package com.example.firebaseissuegithub.userInterface.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseissuegithub.R
import com.example.firebaseissuegithub.model.Comments
import kotlinx.android.synthetic.main.comment_row_item.view.*

class CommentAdapter(private val items: List<Comments>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.comment_row_item, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CommentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(comment: Comments) {
            with(view) {
                txt_comment_user.text = comment.user.userName
                txt_comment.text = comment.body
            }
        }
    }
}