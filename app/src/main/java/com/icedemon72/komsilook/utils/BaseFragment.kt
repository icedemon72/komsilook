package com.icedemon72.komsilook.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {
	private var _binding: Binding? = null
	protected val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = getViewBinding(inflater, container)
		return binding.root
	}

	abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}