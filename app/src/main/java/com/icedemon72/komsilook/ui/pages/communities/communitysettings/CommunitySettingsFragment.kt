package com.icedemon72.komsilook.ui.pages.communities.communitysettings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.icedemon72.komsilook.Komsilook
import com.icedemon72.komsilook.databinding.FragmentCommunitySettingsBinding
import com.icedemon72.komsilook.utils.BaseFragment
import com.icedemon72.komsilook.utils.Resource
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class CommunitySettingsFragment : BaseFragment<FragmentCommunitySettingsBinding>() {
    @Inject
    lateinit var communitySettingsViewModel: CommunitySettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toHomeAction = CommunitySettingsFragmentDirections.actionCommunitySettingsFragmentToHomeFragment()
        val communityId = arguments?.getString("communityId") ?: return
        communitySettingsViewModel.loadCommunity(communityId)

        communitySettingsViewModel.communityLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Success -> {
                    val community = state.data!!
                    binding.communityCodeText.text = community.code

                    if (communitySettingsViewModel.isCreator) {
                        binding.regenerateCodeButton.visibility = View.VISIBLE
                        binding.deleteCommunityButton.visibility = View.VISIBLE
                        binding.communityCodeLabel.visibility = View.VISIBLE
                        binding.communityCodeText.visibility = View.VISIBLE
                        binding.leaveCommunityButton.visibility = View.GONE
                    } else {
                        binding.regenerateCodeButton.visibility = View.GONE
                        binding.deleteCommunityButton.visibility = View.GONE
                        binding.communityCodeLabel.visibility = View.GONE
                        binding.communityCodeText.visibility = View.GONE
                        binding.leaveCommunityButton.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), state.message ?: "Greška", Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }

        binding.regenerateCodeButton.setOnClickListener {
            communitySettingsViewModel.regenerateCode(communityId)
        }

        binding.deleteCommunityButton.setOnClickListener {
            communitySettingsViewModel.deleteCommunity(communityId)
            Toast.makeText(requireContext(), "Uspešno brisanje Komšilooka", Toast.LENGTH_SHORT).show()

            findNavController().navigate(toHomeAction)
        }

        binding.leaveCommunityButton.setOnClickListener {
            communitySettingsViewModel.leaveCommunity(communityId)
            Toast.makeText(requireContext(), "Uspešno ste izašli iz Komšilooka", Toast.LENGTH_SHORT).show()
            findNavController().navigate(toHomeAction)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as Komsilook).appComponent.inject(this)
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCommunitySettingsBinding {
        return FragmentCommunitySettingsBinding.inflate(inflater, container, false)
    }
}