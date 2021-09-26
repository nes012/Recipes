package nesty.anzhy.matkonim.ui.activities.detailactivity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.databinding.FragmentOverviewBinding
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.imageViewOverview.load(myBundle?.image)
        binding.tvTitleOverview.text = myBundle?.title
        binding.tvLikesOverview.text = myBundle?.aggregateLikes.toString()
        binding.tvTimeOverView.text = myBundle?.readyInMinutes.toString()
       // binding.tvSummaryOverview.text = myBundle?.summary
        //we're going to parse html with jsoup library
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.tvSummaryOverview.text = summary
        }

        if(myBundle?.vegetarian == true){
            binding.ivVegetarianOverview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvVegetarianOverview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if(myBundle?.cheap == true){
            binding.ivCheapOverview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvCheapOverview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if(myBundle?.dairyFree == true){
            binding.ivDairyFreeOverview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvDairyFreeOverview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if(myBundle?.glutenFree == true){
            binding.ivGlutenFreeOverview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvGlutenFreeOverView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if(myBundle?.veryHealthy == true){
            binding.ivHealthyOverview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvHealthyOverview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if(myBundle?.vegan == true){
            binding.ivVeganOverview.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvVeganOverview.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        return binding.root
    }



}