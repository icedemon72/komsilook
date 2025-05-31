package com.icedemon72.komsilook.ui.pages.communities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.icedemon72.komsilook.data.models.Community
import com.icedemon72.komsilook.R

class CommunitiesAdapter(
	private val communities: List<Community>,
	private val onItemClick: (Community) -> Unit
) :
	RecyclerView.Adapter<CommunitiesAdapter.CommunityViewHolder>() {

	inner class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val name = itemView.findViewById<TextView>(R.id.name)
		val description = itemView.findViewById<TextView>(R.id.description)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_community, parent, false)
		return CommunityViewHolder(view)
	}

	override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
		val community = communities[position]
		holder.name.text = community.name
		holder.description.text = community.description
		holder.itemView.setOnClickListener {
			onItemClick(community)
		}
	}

	override fun getItemCount(): Int = communities.size
}
