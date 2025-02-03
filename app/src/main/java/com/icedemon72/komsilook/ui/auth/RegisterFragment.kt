package com.icedemon72.komsilook.ui.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.databinding.FragmentRegisterBinding
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentLoginBinding
import com.icedemon72.komsilook.utils.BaseFragment
import com.icedemon72.komsilook.utils.Resource
import javax.inject.Inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
	@Inject
	lateinit var authViewModel: AuthViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnRegister.setOnClickListener {
			val name = binding.etName.text.toString().trim()
			val email = binding.etEmail.text.toString().trim()
			val password = binding.etPassword.text.toString()

			if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
				authViewModel.register(email, password, name)

			} else {
				Snackbar.make(view, "Popunite sva polja", Snackbar.LENGTH_SHORT).show()
			}

		}

		binding.tvLogin.setOnClickListener {
			findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
		}

		authViewModel.authState.observe(viewLifecycleOwner) { state ->
			when (state) {
				is Resource.Loading -> {
					binding.progressBar.visibility = View.VISIBLE
				}
				is Resource.Success -> {
					binding.progressBar.visibility = View.GONE
					Snackbar.make(view, "Registracija uspešna", Snackbar.LENGTH_SHORT).show()
					 findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
				}
				is Resource.Error -> {
					binding.progressBar.visibility = View.GONE
					Snackbar.make(view, state.message ?: "Došlo je do greške", Snackbar.LENGTH_LONG).show()
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

	override fun getViewBinding (inflater: LayoutInflater, container: ViewGroup?): FragmentRegisterBinding {
		return FragmentRegisterBinding.inflate(inflater, container, false)
	}

}