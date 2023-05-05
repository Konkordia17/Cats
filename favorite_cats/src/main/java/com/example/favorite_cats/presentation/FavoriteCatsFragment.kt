package com.example.favorite_cats.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.database.di.DataModule
import com.example.favorite_cats.FavoriteScreens
import com.example.favorite_cats.R
import com.example.favorite_cats.databinding.FragmentFavoriteCatsBinding
import com.example.favorite_cats.di.DaggerFavoriteCatsComponent
import com.example.favorite_cats.di.FavoriteCatsComponent
import com.example.favorite_cats.di.FavoriteCatsComponentDependenciesProvider
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class FavoriteCatsFragment : Fragment(R.layout.fragment_favorite_cats) {

    private var _binding: FragmentFavoriteCatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteCatsComponent: FavoriteCatsComponent
    private lateinit var favoriteCatsAdapter: FavoriteCatsAdapter

    @Inject
    lateinit var vmFactory: FavoriteCatsViewModelFactory
    lateinit var vm: FavoriteCatsViewModel

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screen: FavoriteScreens

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val favoriteCatsComponentDependencies =
            (context.applicationContext as FavoriteCatsComponentDependenciesProvider)
                .getFavoriteCatsComponentDependencies()
        favoriteCatsComponent = DaggerFavoriteCatsComponent.builder()
            .favoriteCatsComponentDependencies(favoriteCatsComponentDependencies)
            .dataModule(DataModule(requireActivity()))
            .build()
        favoriteCatsComponent.injectFavoriteCatsFragment(this)
        vmFactory = favoriteCatsComponent.getViewModelFactory()
        vm = ViewModelProvider(this, vmFactory)[FavoriteCatsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteCatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getFavoriteCats()
        initCatsList()
        observeLiveData()
        setOnClickListeners()
    }

    private fun observeLiveData() {
        vm.favoriteCatsList.observe(viewLifecycleOwner) {
            favoriteCatsAdapter.submitList(it)
            binding.catLoading.pauseAnimation()
            binding.favoriteCatsRv.visibility = View.VISIBLE
            binding.catLoading.visibility = View.GONE
            if (it.isEmpty()) {
                binding.emptyListTv.visibility = View.VISIBLE
                binding.deleteAll.visibility = View.GONE
            } else {
                binding.deleteAll.visibility = View.VISIBLE
            }
        }
        vm.getUpdateFavoritesAction().observe(viewLifecycleOwner){
            if (it) {
                showToast(com.example.ui.R.string.cat_is_deleted_from_favorites)
            } else {
                showToast(com.example.ui.R.string.connection_error)
            }
        }
    }

    private fun showToast(messageId: Int) {
        Toast.makeText(requireContext(), messageId, Toast.LENGTH_SHORT).show()
    }

    private fun setOnClickListeners() {
        binding.backBtn.setOnClickListener {
            router.exit()
        }

        binding.deleteAll.setOnClickListener {
            vm.deleteAllCatsFromFavorites()
        }
    }

    private fun initCatsList() {
        favoriteCatsAdapter = FavoriteCatsAdapter(
            onItemClicked = { cat ->
                router.navigateTo(screen.catDescriptionFragment(cat))
            },
            onDeleteFavoritesClicked = { cat ->
                vm.deleteFavoriteCat(cat)
            }
        )
        with(binding.favoriteCatsRv) {
            adapter = favoriteCatsAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            setHasFixedSize(true)
        }
    }


    companion object {
        private const val SPAN_COUNT = 2
        fun newInstance(): FavoriteCatsFragment {
            return FavoriteCatsFragment()
        }
    }
}