package nesty.anzhy.matkonim.ui.activities.detailactivity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.databinding.FragmentIngredientBinding
import nesty.anzhy.matkonim.databinding.FragmentInstructionsBinding
import nesty.anzhy.matkonim.models.Result
import nesty.anzhy.matkonim.util.Constants


class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        //here we're going to use anonymous class
        binding.instructionsWebView.webViewClient = object : WebViewClient(){}
        val webSiteUrl: String = myBundle!!.sourceUrl
        binding.instructionsWebView.loadUrl(webSiteUrl)

        binding.instructionsWebView.settings.domStorageEnabled = true
        //binding.instructionsWebView.getSettings().setAppCacheEnabled(true)
        binding.instructionsWebView.settings.loadsImagesAutomatically = true
        binding.instructionsWebView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW


        return binding.root
    }
}