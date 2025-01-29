package com.icedemon72.komsilook.ui.auth

import AuthViewModelFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentLoginBinding
import com.icedemon72.komsilook.utils.Resource
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.ViewModelProvider
import com.icedemon72.komsilook.data.repositories.AuthRepository

// import com.google.android.gms.auth.api.signin.GoogleSignInClient

class LoginFragment : Fragment() {
	private var _binding: FragmentLoginBinding? = null
	private val binding get() = _binding!!
	private lateinit var authViewModel: AuthViewModel
	// private lateinit var googleSignInClient: GoogleSignInClient

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentLoginBinding.inflate(inflater, container, false)
		val authRepository = AuthRepository()  // Or get it via DI framework like Dagger/Hilt
		val factory = AuthViewModelFactory(authRepository)
		authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)


		binding.btnLogin.setOnClickListener {
			val email = binding.etEmail.text.toString().trim()
			val password = binding.etPassword.text.toString().trim()
			if (email.isNotEmpty() && password.isNotEmpty()) {
				authViewModel.login(email, password)
			} else {
				Snackbar.make(view, "Please enter all fields", Snackbar.LENGTH_SHORT).show()
			}
		}

		// TODO: Implement Google Sign-In functionality
		//	binding.btnGoogleSignIn.setOnClickListener {
		//		googleSignIn();
		//	}

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
					Snackbar.make(view, state.message ?: "Login failed", Snackbar.LENGTH_LONG)
						.show()
				}

				null -> {
					binding.progressBar.visibility = View.GONE

				}
			}
		}

	}

	private fun googleSignIn() {
		// val signInIntent = googleSignInClient.signInIntent
		// startActivityForResult(signInIntent, 100)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}