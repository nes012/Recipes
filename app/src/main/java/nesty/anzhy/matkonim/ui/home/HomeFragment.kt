package nesty.anzhy.matkonim.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nesty.anzhy.matkonim.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getUser()
        registerObserver()
        listenToChannels()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


// Declaring the animation view
        val animationView: LottieAnimationView = binding.animationView
        animationView
            .playAnimation()
        animationView.repeatCount = 10

        return binding.root
    }

    private fun getUser() {
        mainViewModel.getCurrentUser()
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.allEventsFlow.collect { event ->
                when (event) {
                    is MainViewModel.AllEvents.Message -> {
                        Toast.makeText(
                            requireContext(),
                            event.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun registerObserver() {
        mainViewModel.currentUser.observe(viewLifecycleOwner, { user ->
            user?.let {
                binding?.apply {
                    welcomeTxt.text = "welcome ${it.email}"
                    signinButton.text = "sign out"
                    signinButton.setOnClickListener {
                        mainViewModel.signOut()
                    }
                }
            } ?: binding?.apply {
                welcomeTxt.isVisible = false
                signinButton.text = "sign in"
                signinButton.setOnClickListener {
                    findNavController().navigate(R.id.action_navigation_home_to_signInFragment)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

