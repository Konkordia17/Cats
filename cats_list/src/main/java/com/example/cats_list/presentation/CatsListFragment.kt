package com.example.cats_list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cats_list.Cat
import com.example.cats_list.R
import com.example.cats_list.databinding.FragmentCatsListBinding
import com.example.cats_list.di.CatsListComponent
import com.example.cats_list.di.CatsListComponentDependenciesProvider
import com.example.cats_list.di.DaggerCatsListComponent
import com.example.database.di.DataModule
import com.example.ui.extentions.getCurrentPosition
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CatsListFragment : Fragment(R.layout.fragment_cats_list) {
    private var _binding: FragmentCatsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var catsAdapter: CatsAdapter
    private lateinit var catsComponent: CatsListComponent

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screen: Screens

    @Inject
    lateinit var vmFactory: CatsListViewModelFactory
    lateinit var vm: CatsListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val catsComponentDependencies =
            (context.applicationContext as CatsListComponentDependenciesProvider)
                .getCatsListComponentDependencies()
        catsComponent = DaggerCatsListComponent.builder()
            .catsListComponentDependencies(catsComponentDependencies)
            .dataModule(DataModule(requireActivity()))
            .build()
        catsComponent.injectCatsListFragment(this)
        vmFactory = catsComponent.getViewModelFactory()
        vm = ViewModelProvider(this, vmFactory)[CatsListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCatsList()
        vm.getCats(CATS_LIMIT)
        observeLiveData()
        setOnClickListeners()
    }

    private fun observeLiveData() {
        vm.catsList.observe(viewLifecycleOwner) { cats ->
            setDataToAdapter(cats)
        }
        vm.getUpdateFavoritesAction().observe(viewLifecycleOwner) { isUpdated ->
            if (null != isUpdated)
                showIsUpdatedFavoritesMessage(isUpdated)
        }
    }

    private fun setDataToAdapter(cats: List<Cat>) {
        catsAdapter.submitList(cats)
        with(binding) {
            catsList.visibility = View.VISIBLE
            favoritesBtn.visibility = View.VISIBLE
            catLoading.pauseAnimation()
            catLoading.visibility = View.GONE
        }
    }

    private fun showIsUpdatedFavoritesMessage(isAdded: Boolean) {
        if (isAdded) {
            showToast(com.example.ui.R.string.cat_is_added_to_favorites)
        } else {
            showToast(com.example.ui.R.string.cat_is_deleted_from_favorites)
        }
    }

    private fun setOnClickListeners() {
        binding.favoritesBtn.setOnClickListener {
            router.navigateTo(screen.favoriteCatsFragment())
        }
    }

    private fun showToast(messageId: Int) {
        Toast.makeText(requireContext(), messageId, Toast.LENGTH_SHORT).show()
    }

    private fun initCatsList() {
        catsAdapter = CatsAdapter(
            onItemClicked = { cat ->
                router.navigateTo(screen.catDescriptionFragment(cat))
            },
            isAddToFavoritesClicked = { cat ->
                vm.setOrDeleteCatFromFavorites(cat)
            }
        )
        with(binding.catsList) {
            adapter = catsAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val position = getCurrentPosition()
                    vm.onPositionChanged(position)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val SPAN_COUNT = 2
        const val CATS_LIMIT = 25
        fun newInstance(): CatsListFragment {
            return CatsListFragment()
        }
    }
}