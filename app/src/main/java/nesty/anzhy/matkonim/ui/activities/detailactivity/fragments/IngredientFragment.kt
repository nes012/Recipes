package nesty.anzhy.matkonim.ui.activities.detailactivity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import nesty.anzhy.matkonim.adapters.IngredientsAdapter
import nesty.anzhy.matkonim.databinding.FragmentIngredientBinding
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.util.Constants.Companion.RECIPE_RESULT_KEY


class IngredientFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView()

        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }

        return binding.root
    }

    private fun setupRecyclerView(){
        binding.recyclerViewIngredients.adapter = mAdapter
        binding.recyclerViewIngredients.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewIngredients.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}