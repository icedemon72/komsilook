package com.icedemon72.komsilook.ui.pages.communities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentCommunitiesBinding
import com.icedemon72.komsilook.utils.BaseFragment

class CommunitiesFragment : BaseFragment<FragmentCommunitiesBinding>() {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.addBtn.setOnClickListener {
			findNavController().navigate(R.id.action_communitiesFragment_to_createCommunityFragment)
		}

	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCommunitiesBinding {
		return FragmentCommunitiesBinding.inflate(inflater, container, false)
	}
}