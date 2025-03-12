package com.icedemon72.komsilook.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
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

	fun <T> handleResourceState(
		resource: Resource<T>?,
		progressBar: View? = null,
		onSuccess: (T) -> Unit,
		onError: ((String) -> Unit)? = null
	) {
		when (resource) {
			is Resource.Loading -> progressBar?.visibility = View.VISIBLE
			is Resource.Success -> {
				progressBar?.visibility = View.GONE
				onSuccess(resource.data!! as T)
			}
			is Resource.Error -> {
				progressBar?.visibility = View.GONE
				onError?.invoke(resource.message ?: "Došlo je do greške")
			}
			null -> progressBar?.visibility = View.GONE
		}
	}

}