package com.icedemon72.komsilook.ui.auth

import AuthViewModelFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.data.repositories.AuthRepository
import com.icedemon72.komsilook.databinding.FragmentRegisterBinding
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.utils.Resource

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
	private var _binding: FragmentRegisterBinding? = null
	private val binding get() = _binding!!
	private lateinit var authViewModel: AuthViewModel


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		_binding = FragmentRegisterBinding.inflate(inflater, container, false)
		val authRepository = AuthRepository()  // Or get it via DI framework like Dagger/Hilt
		val factory = AuthViewModelFactory(authRepository)
		authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
		return binding.root
	}

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

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}