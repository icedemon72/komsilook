package com.icedemon72.komsilook.ui.pages.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.data.models.User
import com.icedemon72.komsilook.databinding.FragmentProfileBinding
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

	@Inject
	lateinit var profileViewModel: ProfileViewModel
	private var currentUser: User? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.etEmail.text = currentUser?.email

		binding.btnLogut.setOnClickListener {
			profileViewModel.logout()
			findNavController().navigate(R.id.loginFragment)
			Snackbar.make(view, "Uspešna odjava", Snackbar.LENGTH_SHORT).show()
		}
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(activity?.application as Komsilook).appComponent.inject(this)
		currentUser = profileViewModel.getCurrentUser()
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
		return FragmentProfileBinding.inflate(inflater, container, false)
	}
}