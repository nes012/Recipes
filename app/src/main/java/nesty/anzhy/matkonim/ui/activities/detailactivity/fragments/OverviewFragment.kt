package nesty.anzhy.matkonim.ui.activities.detailactivity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import coil.load
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.bindingadapters.RecipesItemBinding
import nesty.anzhy.matkonim.databinding.FragmentOverviewBinding
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.util.Constants.Companion.RECIPE_RESULT_KEY

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result

        binding.imageViewOverview.load(myBundle.image)
        binding.tvTitleOverview.text = myBundle.title
        binding.tvLikesOverview.text = myBundle.aggregateLikes.toString()
        binding.tvTimeOverView.text = myBundle.readyInMinutes.toString()
        RecipesItemBinding.parseHtml(binding.tvSummaryOverview, myBundle.summary)

        updateColors(myBundle.vegetarian, binding.tvVegetarianOverview, binding.ivVegetarianOverview)
        updateColors(myBundle.vegan, binding.tvVeganOverview, binding.ivVeganOverview)
        updateColors(myBundle.cheap, binding.tvCheapOverview, binding.ivCheapOverview)
        updateColors(myBundle.dairyFree, binding.tvDairyFreeOverview, binding.ivDairyFreeOverview)
        updateColors(myBundle.glutenFree, binding.tvGlutenFreeOverView, binding.ivGlutenFreeOverview)
        updateColors(myBundle.veryHealthy, binding.tvHealthyOverview, binding.ivHealthyOverview)

        return binding.root
    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}