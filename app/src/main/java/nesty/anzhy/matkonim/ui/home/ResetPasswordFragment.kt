package nesty.anzhy.matkonim.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nesty.anzhy.matkonim.viewmodel.MainViewModel
import nesty.anzhy.matkonim.R
import nesty.anzhy.matkonim.databinding.FragmentResetPasswordBinding

@AndroidEntryPoint
class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {

    private var _binding : FragmentResetPasswordBinding? = null
    private val binding get() = _binding
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater , container , false)
        setUpWidgets()
        listenToChannels()
        return binding?.root
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEventsFlow.collect { event ->
                when(event){
                    is MainViewModel.AllEvents.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
                    }
                    is MainViewModel.AllEvents.Error -> {
                        binding?.apply {
                            resetPassProgressBar.isInvisible = true
                            errorText.text = event.error
                        }
                    }
                    is MainViewModel.AllEvents.ErrorCode -> {
                        if(event.code == 1)
                            binding?.apply {
                                userEmailEtvl.error = "email should not be empty!"
                                resetPassProgressBar.isInvisible = true
                            }
                    }
                }

            }
        }
    }

    private fun setUpWidgets() {
        binding?.apply {
            buttonResendPassword.setOnClickListener {
                resetPassProgressBar.isVisible = true
                val email = userEmailEtv.text.toString()
                viewModel.verifySendPasswordReset(email)
            }
        }
    }
}