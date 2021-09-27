package nesty.anzhy.matkonim.ui.favoriterecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nesty.anzhy.matkonim.MainViewModel
import nesty.anzhy.matkonim.adapters.FavoriteRecipesAdapter
import nesty.anzhy.matkonim.databinding.FragmentFavoriteRecipesBinding

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentFavoriteRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel =  ViewModelProvider(requireActivity())
            .get(MainViewModel::class.java)
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mAdapter = mAdapter
        binding.mainViewModel = mainViewModel

        //we don't need this code because we added FavoriteRecipesBinding to layout.
        //mainViewModel and adapter inside layout.
        /*
        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, {  database->
            if (database.isNotEmpty()) {
                mAdapter.setData(database)
            }
            else{
                binding.ivNoData.visibility = View.VISIBLE
                binding.tvNoData.visibility = View.VISIBLE
            }
        })
         */
        setupRecyclerView(binding.recyclerViewFavoriteRecipes)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}