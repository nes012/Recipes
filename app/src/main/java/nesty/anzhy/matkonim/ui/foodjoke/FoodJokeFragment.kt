package nesty.anzhy.matkonim.ui.foodjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import nesty.anzhy.matkonim.databinding.FragmentFoodJokeBinding

class FoodJokeFragment : Fragment() {

    private lateinit var foodJokeViewModel: FoodJokeViewModel
    private var _binding: FragmentFoodJokeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        foodJokeViewModel =
            ViewModelProvider(this).get(FoodJokeViewModel::class.java)

        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}