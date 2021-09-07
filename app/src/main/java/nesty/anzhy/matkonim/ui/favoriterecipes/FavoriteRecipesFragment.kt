package nesty.anzhy.matkonim.ui.favoriterecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import nesty.anzhy.matkonim.databinding.FragmentFavoriteRecipesBinding

class FavoriteRecipesFragment : Fragment() {

    private lateinit var favoriteRecipesFragmentViewModel: FavoriteRecipesFragmentViewModel
    private var _binding: FragmentFavoriteRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteRecipesFragmentViewModel =
            ViewModelProvider(this).get(FavoriteRecipesFragmentViewModel::class.java)

        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}