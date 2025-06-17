package com.icedemon72.komsilook.ui.components

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.icedemon72.komsilook.databinding.ModalCommunityBinding
import com.icedemon72.komsilook.data.models.Community

class CommunityBottomSheet(
    private val community: Community,
    private val onJoinClick: (Community) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: ModalCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalCommunityBinding.inflate(inflater, container, false)

        binding.communityName.text = community.name
        binding.communityDescription.text = community.description
        binding.communityLocation.text = community.location

        binding.joinButton.setOnClickListener {
            onJoinClick(community)
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
