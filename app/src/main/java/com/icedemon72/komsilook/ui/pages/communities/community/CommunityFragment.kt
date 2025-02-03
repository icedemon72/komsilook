package com.icedemon72.komsilook.ui.pages.communities.community

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentCommunityBinding
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class CommunityFragment : BaseFragment<FragmentCommunityBinding>() {
	@Inject
	lateinit var communityViewModel: CommunityViewModel


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		(activity?.application as Komsilook).appComponent.inject(this)
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCommunityBinding {
		return FragmentCommunityBinding.inflate(inflater, container, false)
	}
}