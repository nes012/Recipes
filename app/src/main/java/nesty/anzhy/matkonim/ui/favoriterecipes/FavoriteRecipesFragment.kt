package nesty.anzhy.matkonim.ui.favoriterecipes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nesty.anzhy.matkonim.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.adapters.FavoriteRecipesAdapter
import nesty.anzhy.matkonim.databinding.FragmentFavoriteRecipesBinding

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentFavoriteRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }

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

        //show favorite_recipes_menu layout as our menu in this fragment
        setHasOptionsMenu(true)

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

        //if contextual extra mode visible - close it
        mAdapter.clearContextualActionMode()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.delete_all_favorite_recipes_menu){
            mainViewModel.deleteAllFavoriteRecipes()
            showToast("All ercipes removed")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}