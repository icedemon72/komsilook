package com.icedemon72.komsilook.ui.pages.communities.createcommunity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.databinding.FragmentCreateCommunityBinding
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class CreateCommunityFragment : BaseFragment<FragmentCreateCommunityBinding>() {

	@Inject
	lateinit var createCommunityViewModel: CreateCommunityViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnCreate.setOnClickListener {
			val name = binding.etName.text.toString()
			val description = binding.etDescription.text.toString()
			val location = binding.etLocation.text.toString()
			val isPrivate = binding.etPrivate.isChecked

			if (name.isEmpty() || description.isEmpty() || location.isEmpty()) {
				Snackbar.make(view, "Popunite sva polja.", Snackbar.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			 createCommunityViewModel.create(name, description, location, isPrivate)
		}

		createCommunityViewModel.communityState.observe(viewLifecycleOwner) { state ->
			handleResourceState(
				resource = state,
				progressBar = binding.progressBar,
				onSuccess = { community ->
					binding.progressBar.visibility = View.GONE
					binding.btnCreate.isEnabled = true
					Snackbar.make(view, "Uspešno kreiranje Komšilooka!", Snackbar.LENGTH_SHORT).show()

					val action = CreateCommunityFragmentDirections
						.actionCreateCommunityFragmentToCommunityFragment(community.id!!)
					 findNavController().navigate(action)
				},
				errorMessage = "Došlo je do greške prilikom kreiranja Komšilooka."
			)
		}

		requireActivity().onBackPressedDispatcher.addCallback(
			viewLifecycleOwner,
			object : OnBackPressedCallback(true) {
				override fun handleOnBackPressed() {
					findNavController().navigateUp()
				}
			}
		)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(activity?.application as Komsilook).appComponent.inject(this)
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCreateCommunityBinding {
		return FragmentCreateCommunityBinding.inflate(inflater, container, false)
	}
}