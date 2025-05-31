package com.icedemon72.komsilook.ui.pages.posts.post

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.utils.BaseFragment
import com.icedemon72.komsilook.databinding.FragmentPostBinding
import javax.inject.Inject


class PostFragment : BaseFragment<FragmentPostBinding>() {
	@Inject
	lateinit var postViewModel: PostViewModel
	private var communityId: String? = null
	private var postId: String? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		postViewModel.postState.observe(viewLifecycleOwner) { state ->
			handleResourceState(
				resource =  state,
				progressBar = binding.progressBar,
				onSuccess = { post ->
					binding.postTitle.text = post.title
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
		this.postId = arguments?.getString("postId")

		this.postId?.let {
			if (it.isNotEmpty()) {
				postViewModel.getPost(this.communityId!!, it)
			} else {
				Log.d("CommunityFragment", "Community ID is empty")
			}
		} ?: run {
			Log.d("CommunityFragment", "No valid communityId found")
		}
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPostBinding {
		return FragmentPostBinding.inflate(inflater, container, false)
	}
}