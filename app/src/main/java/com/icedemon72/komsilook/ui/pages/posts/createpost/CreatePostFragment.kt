package com.icedemon72.komsilook.ui.pages.posts.createpost

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.data.models.enums.PostType
import com.icedemon72.komsilook.databinding.FragmentCreatePostBinding
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class CreatePostFragment : BaseFragment<FragmentCreatePostBinding>() {
	@Inject
	lateinit var createPostViewModel: CreatePostViewModel
	private var communityId: String? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val options = PostType.entries.toList()

		val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

		binding.etType.adapter = adapter

		binding.btnCreate.setOnClickListener {
			val title = binding.etTitle.text.toString()
			val description = binding.etDescription.text.toString()
			val type = binding.etType.selectedItem.toString()

			if (title.isEmpty()) {
				Snackbar.make(view, "Naslov ne sme biti prazan", Snackbar.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			if (type.isEmpty()) {
				Snackbar.make(view, "Objava mora imati tip", Snackbar.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			createPostViewModel.createPost(title, description, type, communityId!!)
		}

		createPostViewModel.postState.observe(viewLifecycleOwner) { state ->

			handleResourceState(
				resource = state,
				progressBar = binding.progressBar,
				onSuccess = { post ->
					binding.progressBar.visibility = View.GONE
					binding.btnCreate.isEnabled = true
					Snackbar.make(view, "Uspešno kreiranje objave!", Snackbar.LENGTH_SHORT).show()
					val action = CreatePostFragmentDirections
						.actionCreatePostFragmentToPostFragment(communityId!!, post.id!!)
					findNavController().navigate(action)
				}
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

		this.communityId = arguments?.getString("communityId")
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCreatePostBinding {
		return FragmentCreatePostBinding.inflate(inflater, container, false)
	}
}