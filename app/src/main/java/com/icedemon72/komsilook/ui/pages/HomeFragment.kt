package com.icedemon72.komsilook.ui.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.databinding.FragmentHomeBinding
import com.icedemon72.komsilook.utils.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

	}

	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
		return FragmentHomeBinding.inflate(inflater, container, false)
	}
}