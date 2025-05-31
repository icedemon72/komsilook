package com.icedemon72.komsilook.ui.pages

import android.os.Bundle
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.databinding.FragmentHomeBinding
import com.icedemon72.komsilook.ui.pages.posts.PostsAdapter
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
	@Inject
	lateinit var homeViewModel: HomeViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		homeViewModel.postsState.observe(viewLifecycleOwner) { state ->
			handleResourceState(
				resource = state,
				progressBar = binding.progressBar,
				onSuccess = { posts ->
					val adapter = PostsAdapter(posts) { selectedPost ->
						val action = HomeFragmentDirections
							.actionHomeFragmentToPostFragment(selectedPost.communityId!!, selectedPost.id!!)
						findNavController().navigate(action)
					}

					binding.postsRecyclerView.adapter = adapter
					binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
				}
			)
		}
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
		return FragmentHomeBinding.inflate(inflater, container, false)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(activity?.application as Komsilook).appComponent.inject(this)

		homeViewModel.getPostsInJoinedCommunities()
	}
}