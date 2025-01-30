package com.icedemon72.komsilook.ui.pages.profile

import ProfileViewModelFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.data.models.User
import com.icedemon72.komsilook.databinding.FragmentProfileBinding
import com.icedemon72.komsilook.data.repositories.AuthRepository

class ProfileFragment : Fragment() {
	private var _binding: FragmentProfileBinding? = null
	private val binding get() = _binding!!

	private lateinit var profileViewModel: ProfileViewModel
	private var currentUser: User? = null


//	private val viewModel: ProfileViewModel by viewModels()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentProfileBinding.inflate(inflater, container, false)
		val authRepository = AuthRepository()
		val factory = ProfileViewModelFactory(authRepository)
		profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
		currentUser = profileViewModel.getCurrentUser()
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.etEmail.text = currentUser?.email

		binding.btnLogut.setOnClickListener {
			profileViewModel.logout()
			findNavController().navigate(R.id.loginFragment)
			Snackbar.make(view, "Uspešna odjava", Snackbar.LENGTH_SHORT).show()
		}
	}
}