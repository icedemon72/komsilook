package com.icedemon72.komsilook.ui.pages.communities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentCommunitiesBinding
import com.icedemon72.komsilook.utils.BaseFragment
import com.icedemon72.komsilook.utils.Resource
import javax.inject.Inject

class CommunitiesFragment : BaseFragment<FragmentCommunitiesBinding>() {

	@Inject
	lateinit var communitiesViewModel: CommunitiesViewModel

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.addBtn.setOnClickListener {
			findNavController().navigate(R.id.action_communitiesFragment_to_createCommunityFragment)
		}

		communitiesViewModel.communitiesState.observe(viewLifecycleOwner) { state ->
			handleResourceState(
				resource = state,
				progressBar = binding.progressBar,
				onSuccess = { data ->
					Log.d("Communities: ", data.toString())
					// Handle success logic here
				},
				onError = { errorMessage ->
					Log.e("CommunitiesError", errorMessage)
					// Handle error logic here
				}
			)
		}

	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(activity?.application as Komsilook).appComponent.inject(this)
		communitiesViewModel.getNotJoined()
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCommunitiesBinding {
		return FragmentCommunitiesBinding.inflate(inflater, container, false)
	}
}