package com.icedemon72.komsilook.ui.pages.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icedemon72.komsilook.R
import com.icedemon72.komsilook.data.models.Post

class PostsAdapter(
	private val posts: List<Post>,
	private val onItemClick: (Post) -> Unit
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

	inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val title: TextView = itemView.findViewById(R.id.postTitle)
		val leftBorder: View = itemView.findViewById(R.id.leftBorder)
		val floatingIcon: ImageView = itemView.findViewById(R.id.floatingIcon)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
		return PostViewHolder(view)
	}

	override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
		val post = posts[position]
		val context = holder.itemView.context
		holder.title.text = post.title
		holder.itemView.setOnClickListener {
			onItemClick(post)
		}

		// Access the views through ViewBinding
		val leftBorder = holder.leftBorder
		val floatingIcon = holder.floatingIcon

		when (post.type) {
			"Problem" -> {
				leftBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.problem_border))
				floatingIcon.setImageResource(R.drawable.ic_problem_icon)
				floatingIcon.visibility = View.VISIBLE
			}
			"Obaveštenje" -> {
				leftBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.announcement_border))
				floatingIcon.setImageResource(R.drawable.ic_announcement_icon)
				floatingIcon.visibility = View.VISIBLE
			}
			"Predlog" -> {
				leftBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.suggestion_border))
				floatingIcon.setImageResource(R.drawable.ic_suggestion_icon)
				floatingIcon.visibility = View.VISIBLE
			}
			else -> {
				floatingIcon.visibility = View.VISIBLE
				leftBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.default_border))
			}
		}
	}

	override fun getItemCount(): Int = posts.size
}
