package com.icedemon72.komsilook.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentLoginBinding
import com.icedemon72.komsilook.utils.Resource
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
	@Inject
	lateinit var authViewModel: AuthViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)


		binding.btnLogin.setOnClickListener {
			val email = binding.etEmail.text.toString().trim()
			val password = binding.etPassword.text.toString().trim()
			if (email.isNotEmpty() && password.isNotEmpty()) {
				authViewModel.login(email, password)
			} else {
				Snackbar.make(view, "Popunite sva polja", Snackbar.LENGTH_SHORT).show()
			}
		}

		binding.tvRegister.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
		}


		authViewModel.authState.observe(viewLifecycleOwner) { state ->
			when (state) {
				is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
				is Resource.Success -> {
					binding.progressBar.visibility = View.GONE
					Snackbar.make(view, "Uspešna prijava", Snackbar.LENGTH_SHORT).show()
					findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
				}

				is Resource.Error -> {
					binding.progressBar.visibility = View.GONE
					Snackbar.make(
						view,
						"Greška prilikom prijave: uneta je netačna e-adresa i/ili lozinka",
						Snackbar.LENGTH_LONG
					).show()
				}

				null -> {
					binding.progressBar.visibility = View.GONE
				}
			}
		}
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(activity?.application as Komsilook).appComponent.inject(this)
	}

	override fun getViewBinding (inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
		return FragmentLoginBinding.inflate(inflater, container, false)
	}

}