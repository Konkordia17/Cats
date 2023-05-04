package com.example.cats_list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cats_list.R
import com.example.cats_list.databinding.FragmentCatsListBinding
import com.example.cats_list.di.CatsListComponent
import com.example.cats_list.di.CatsListComponentDependenciesProvider
import com.example.cats_list.di.DaggerCatsListComponent
import com.example.cats_list.getCurrentPosition
import com.example.database.di.DataModule
import com.example.storage.data.CatsApi
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CatsListFragment : Fragment(R.layout.fragment_cats_list) {
    private var _binding: FragmentCatsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var catsAdapter: CatsAdapter
    private lateinit var catsComponent: CatsListComponent

    @Inject
    lateinit var catsApi: CatsApi

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
    }

    private fun observeLiveData() {
        vm.catsList.observe(viewLifecycleOwner) { cats ->
            catsAdapter.submitList(cats)
            binding.catsList.visibility = View.VISIBLE
            binding.catLoading.pauseAnimation()
            binding.catLoading.visibility = View.GONE
        }
    }

    private fun initCatsList() {
        catsAdapter = CatsAdapter(
            onItemClicked = { cat ->
                router.navigateTo(screen.catDescriptionFragment(cat))
            },
            onHeartClicked = { cat, view ->
                vm.setCatToFavorites(cat){
                    view.setImageDrawable(resources.getDrawable(com.example.ui.R.drawable.red_heart_icon, null))
                }
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
        const val CATS_LIMIT = 15
        fun newInstance(): CatsListFragment {
            return CatsListFragment()
        }
    }
}