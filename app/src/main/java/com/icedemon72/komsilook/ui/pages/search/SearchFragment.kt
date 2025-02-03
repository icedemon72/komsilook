package com.icedemon72.komsilook.ui.pages.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentSearchBinding
import com.icedemon72.komsilook.utils.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
		return FragmentSearchBinding.inflate(inflater, container, false)
	}
}