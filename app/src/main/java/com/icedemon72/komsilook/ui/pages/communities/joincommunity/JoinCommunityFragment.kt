package com.icedemon72.komsilook.ui.pages.communities.joincommunity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.databinding.FragmentJoinCommunityBinding
import com.icedemon72.komsilook.utils.BaseFragment
import javax.inject.Inject

class JoinCommunityFragment : BaseFragment<FragmentJoinCommunityBinding>()  {
    @Inject
    lateinit var joinCommunityViewModel: JoinCommunityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.joinButton.setOnClickListener {
            val code = binding.codeEditText.text.toString().trim()
            if (code.isNotEmpty()) {
                joinCommunityViewModel.joinCommunityByCode(code)
            } else {
                Toast.makeText(requireContext(), "Unesite kod komšilooka", Toast.LENGTH_SHORT).show()
            }

            joinCommunityViewModel.communitiesState.observe(viewLifecycleOwner) { state ->
                handleResourceState(
                    resource = state,
                    progressBar = binding.progressBar,
                    onSuccess = { community ->

                        binding.progressBar.visibility = View.GONE
                        binding.joinButton.isEnabled = true
                        Snackbar.make(view, "Uspešno pridruživanje komšilooku!", Snackbar.LENGTH_SHORT).show()

                        Log.d("JoinCommunityFragment", "Navigating to community with id: ${community.id}")
                        val action = JoinCommunityFragmentDirections
                            .actionJoinCommunityFragmentToCommunityFragment(community.id!!)

                        findNavController().navigate(action)
                    },
                    onError = { errorMessage ->
                        Toast.makeText(requireContext(), errorMessage ?: "Greška", Toast.LENGTH_SHORT).show()
                    },
                    errorMessage = "Uneli ste netačan kod. Pokušajte ponovo."
                )
            }

        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentJoinCommunityBinding {
        return FragmentJoinCommunityBinding.inflate(inflater, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as Komsilook).appComponent.inject(this)
    }
}