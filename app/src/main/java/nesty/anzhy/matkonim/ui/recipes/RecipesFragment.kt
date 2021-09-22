package nesty.anzhy.matkonim.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nesty.anzhy.matkonim.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.adapters.RecipesAdapter
import nesty.anzhy.matkonim.databinding.FragmentRecipesBinding
import nesty.anzhy.matkonim.util.NetworkResult
import nesty.anzhy.matkonim.util.observeOnce


@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

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
    //! we should use viewLifecycleOwner instead this. binding.lifecycleOwner = viewLifecycleOwner Because view of fragment have smaller lifecycle than fragment
     //binding.lifecycleOwner used for observing LiveData with data binding. Kind of android:text=@{viewModel.text} where val text:LiveData<String>. View will observe text changes at runtime.

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel
        setupRecyclerView()
        //requestApiData()
        readDatabase()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabRecipesFragment.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_recipes_to_recipesBottomSheet)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewRecipes.adapter = mAdapter
        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        /*
//we're going to observe VM inside kotlin coroutine
     */
        lifecycleScope.launch{
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called!")
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

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    //if we receive some error from our network then we want to show to our user the previous state
                    //if our user receive some error we're going to load the previous data from the cache
                    //that way user will not suffer without recycler view
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
        lifecycleScope.launch{
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database->
                if(database.isNotEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //we are going to avoid the memory leaks. Whenever our recipes is destroyed, this binding will be set to null
        _binding = null
    }
}