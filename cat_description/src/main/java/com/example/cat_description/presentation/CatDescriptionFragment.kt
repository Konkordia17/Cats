package com.example.cat_description.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cat_description.R
import com.example.cat_description.databinding.FragmentCatDescriptionBinding
import com.example.cat_description.di.CatDescriptionComponent
import com.example.cat_description.di.CatDescriptionComponentDependenciesProvider
import com.example.cat_description.di.DaggerCatDescriptionComponent
import com.example.cat_description.models.CatDescription
import javax.inject.Inject


class CatDescriptionFragment : Fragment(R.layout.fragment_cat_description) {
    private var _binding: FragmentCatDescriptionBinding? = null
    private val binding get() = _binding!!
    private var cat: CatDescription? = null

    private lateinit var catComponent: CatDescriptionComponent

    @Inject
    lateinit var vmFactory: CatDescriptionViewModelFactory
    lateinit var vm: CatDescriptionViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val catsComponentDependencies =
            (context.applicationContext as CatDescriptionComponentDependenciesProvider)
                .getCatDescriptionComponentDependencies()
        catComponent = DaggerCatDescriptionComponent.builder()
            .catDescriptionComponentDependencies(catsComponentDependencies)
            .build()
        catComponent.injectCatDescriptionFragment(this)
        vmFactory = catComponent.getViewModelFactory()
        vm = ViewModelProvider(this, vmFactory)[CatDescriptionViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cat = requireArguments().getParcelable(CAT_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCatImage()
        setOnClickListeners()
    }

    private fun setCatImage() {
        Glide.with(binding.catImage)
            .load(cat?.url)
            .placeholder(com.example.ui.R.drawable.cat_placeholder)
            .into(binding.catImage)
    }

    private fun downloadPicture(url: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", requireContext().packageName))
                startActivityForResult(intent, 2296)
            } else {
                download(url)
            }
        } else {
            download(url)
        }
    }

    private fun download(url: String) {
        vm.download(
            getBitmapCallback = {
                Glide
                    .with(requireContext())
                    .asBitmap()
                    .load(url)
                    .into(100, 100)
                    .get()
            },
            isLoadedCallback = {
                showToast(R.string.is_downloaded_picture)
            },
            isErrorCallback = {
                showToast(R.string.is_error_downloading)
            }
        )
    }

    private fun showToast(stringId: Int) {
        Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT).show()
    }

    private fun setOnClickListeners() {
        binding.downloadBtn.setOnClickListener {
            cat?.url?.let { url -> downloadPicture(url) }
        }
    }

    companion object {
        private const val CAT_KEY = "cat_key"

        fun newInstance(cat: CatDescription): CatDescriptionFragment {
            return CatDescriptionFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(CAT_KEY, cat)
                this.arguments = bundle
            }
        }
    }
}