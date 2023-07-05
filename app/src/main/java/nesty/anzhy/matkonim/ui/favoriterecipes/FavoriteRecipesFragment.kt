package nesty.anzhy.matkonim.ui.favoriterecipes

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nesty.anzhy.matkonim.viewmodel.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.adapters.FavoriteRecipesAdapter
import nesty.anzhy.matkonim.databinding.FragmentFavoriteRecipesBinding
import nesty.anzhy.matkonim.util.bindMenu

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentFavoriteRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(
            requireActivity(),
            mainViewModel
        )
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mAdapter = mAdapter
        binding.mainViewModel = mainViewModel

        //show favorite_recipes_menu layout as our menu in this fragment
        setMenu()

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

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        val provider = requireActivity().bindMenu(
            menuHost = menuHost,
            menuRes = R.menu.favorite_recipes_menu,
            lifecycleOwner = viewLifecycleOwner) { menuItem ->
            if (menuItem.itemId == R.id.delete_all_favorite_recipes_menu) {
                mainViewModel.deleteAllFavoriteRecipes()
                showToast("All recipes removed")
            }
        }
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}