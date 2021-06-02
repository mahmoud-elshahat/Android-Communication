    package com.mahmoudelshahat.receiver.ui.adapter

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudelshahat.emitter.data.network.response.User
import com.mahmoudelshahat.receiver.R


    class UsersAdapter(val usersList: ArrayList<User>, val context: Context) :
    RecyclerView.Adapter<UsersAdapter.PostsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UsersAdapter.PostsViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return PostsViewHolder(root)
    }

    override fun onBindViewHolder(holder: UsersAdapter.PostsViewHolder, position: Int) {
        holder.bind(usersList.get(position))
    }

    override fun getItemCount(): Int {
        if (usersList.size == 0) {
            Toast.makeText(context, "List is empty", Toast.LENGTH_SHORT).show()
        }
        return usersList.size
    }


    inner class PostsViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        val name = binding.findViewById(R.id.name) as TextView
        val username = binding.findViewById(R.id.user_name) as TextView
        val email = binding.findViewById(R.id.email) as TextView

        fun bind(user: User) {
            name.text = user.name
            username.text = user.username
            email.text = user.email
        }





    }

}