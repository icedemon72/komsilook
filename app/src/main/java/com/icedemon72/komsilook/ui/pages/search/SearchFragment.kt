package com.icedemon72.komsilook.ui.pages.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.databinding.FragmentSearchBinding
import com.icedemon72.komsilook.ui.pages.communities.CommunitiesAdapter
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
	@Inject
	lateinit var searchViewModel: SearchViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		searchViewModel.communitiesState.observe(viewLifecycleOwner) { state ->
			handleResourceState(
				resource = state,
				progressBar = binding.progressBar,
				onSuccess = { communities ->
					val adapter = CommunitiesAdapter(communities) {}

					binding.communityRecyclerView.adapter = adapter
					binding.communityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
				}
			)
		}
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
		return FragmentSearchBinding.inflate(inflater, container, false)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(activity?.application as Komsilook).appComponent.inject(this)
	}
}