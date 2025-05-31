package com.icedemon72.komsilook.ui.pages.communities.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.databinding.FragmentCommunityBinding
import com.icedemon72.komsilook.ui.pages.posts.PostsAdapter
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class CommunityFragment : BaseFragment<FragmentCommunityBinding>() {
	@Inject
	lateinit var communityViewModel: CommunityViewModel
	private var communityId: String? = null


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.addBtn.setOnClickListener {
			val action = CommunityFragmentDirections.actionCommunityFragmentToCreatePostFragment(communityId!!)
			findNavController().navigate(action)
		}

		communityViewModel.communitiesState.observe(viewLifecycleOwner) { state ->
			handleResourceState(
				resource = state,
				progressBar = binding.progressBar,
				onSuccess = { community ->
					binding.communityTitle.text = community.name
				}
			)
		}

		communityViewModel.postsState.observe(viewLifecycleOwner) { state ->
			handleResourceState(
				resource = state,
				progressBar = binding.progressBar,
				onSuccess = { posts ->
					val adapter = PostsAdapter(posts) { selectedPost ->
						val action = CommunityFragmentDirections
							.actionCommunityFragmentToPostFragment(communityId!!, selectedPost.id!!)
						findNavController().navigate(action)
					}
					binding.postsRecyclerView.adapter = adapter
					binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
				},
				onError = { errorMessage ->
					Log.e("PostsError", errorMessage)
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

		this.communityId?.let {
			if (it.isNotEmpty()) {
				communityViewModel.getCommunity(it)
				communityViewModel.getPostsInCommunity(it)
			} else {
				Log.d("CommunityFragment", "Community ID is empty")
			}
		} ?: run {
			Log.d("CommunityFragment", "No valid communityId found")
		}

	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCommunityBinding {
		return FragmentCommunityBinding.inflate(inflater, container, false)
	}
}