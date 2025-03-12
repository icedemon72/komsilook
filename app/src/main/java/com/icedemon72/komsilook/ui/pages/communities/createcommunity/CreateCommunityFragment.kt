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
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentCreateCommunityBinding
import com.icedemon72.komsilook.utils.BaseFragment
import com.icedemon72.komsilook.utils.Resource
import javax.inject.Inject

class CreateCommunityFragment : BaseFragment<FragmentCreateCommunityBinding>() {

	@Inject
	lateinit var createCommunityViewModel: CreateCommunityViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnCreate.setOnClickListener {
			binding.etName.text.toString()
			binding.etDescription.text.toString()
			binding.etLocation.text.toString()
			binding.etPrivate.isChecked

			// createCommunityViewModel.create(name, description, location, isPrivate)

		}

//		createCommunityViewModel.communityState.observe(viewLifecycleOwner) { state ->
//			when (state) {
//				is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
//				is Resource.Success -> {
//					binding.progressBar.visibility = View.GONE
//					Snackbar.make(view, "Uspešno kreiranje Komšilooka!", Snackbar.LENGTH_SHORT).show()
////					 findNavController().navigate(R.id.action_createCommunityFragment_to_communityFragment)
//				}
//				is Resource.Error -> {
//					binding.progressBar.visibility = View.GONE
//					Snackbar.make(view, "Greška prilikom krerianja Komšilooka!", Snackbar.LENGTH_LONG).show()
//				}
//				null -> {
//					binding.progressBar.visibility = View.GONE
//				}
//			}
//		}



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