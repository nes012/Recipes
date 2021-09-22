package nesty.anzhy.matkonim.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nesty.anzhy.matkonim.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.adapters.RecipesAdapter
import nesty.anzhy.matkonim.databinding.FragmentRecipesBinding
import nesty.anzhy.matkonim.util.NetworkResult

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null

    private val mAdapter by lazy {
        RecipesAdapter()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())
            .get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity())
            .get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)



        setupRecyclerView()
        readDatabase()
        //requestApiData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabRecipesFragment.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_recipes_to_recipesBottomSheet)
        }
    }


    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                Log.d("RecipesFragment", "readDatabase called!")
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showShimmerEffect() {
        binding.recyclerViewRecipes.showShimmer()

    }

    private fun hideShimmerEffect() {
        binding.recyclerViewRecipes.hideShimmer()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewRecipes.adapter = mAdapter
        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun requestApiData() {
       // Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, {database->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

}