package com.example.cat_description.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cat_description.R
import com.example.cat_description.databinding.FragmentCatDescriptionBinding
import com.example.cat_description.models.CatDescription


class CatDescriptionFragment : Fragment(R.layout.fragment_cat_description) {
    private var _binding: FragmentCatDescriptionBinding? = null
    private val binding get() = _binding!!
    private var cat: CatDescription? = null

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
            .placeholder(R.drawable.cat_placeholder)
            .into(binding.catImage)
    }

    private fun setOnClickListeners() {
        binding.downloadBtn.setOnClickListener {
            cat?.url?.let { url -> }
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