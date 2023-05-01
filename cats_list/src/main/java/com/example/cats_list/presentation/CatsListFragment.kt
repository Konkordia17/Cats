package com.example.cats_list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cats_list.R
import com.example.cats_list.databinding.FragmentCatsListBinding
import com.example.cats_list.di.CatsListComponent
import com.example.cats_list.di.CatsListComponentProvider
import com.example.storage.data.CatsApi
import javax.inject.Inject

class CatsListFragment : Fragment(R.layout.fragment_cats_list) {
    private var _binding: FragmentCatsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var catsAdapter: CatsAdapter
    private lateinit var catsComponent: CatsListComponent

    @Inject
    lateinit var catsApi: CatsApi

    @Inject
    lateinit var vmFactory: ViewModelFactory
    lateinit var vm: CatsListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        catsComponent = (context.applicationContext as CatsListComponentProvider)
            .getCatsListComponent()
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
        vm.getCats(1)
        observeLiveData()
    }

   private fun observeLiveData() {
        vm.catsList.observe(viewLifecycleOwner) { cats ->
            catsAdapter.submitList(cats)
        }
    }

    private fun initCatsList() {
        catsAdapter = CatsAdapter { cat ->
        }
        with(binding.catsList) {
            adapter = catsAdapter
            layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        fun newInstance(): CatsListFragment {
            return CatsListFragment()
        }
    }
}