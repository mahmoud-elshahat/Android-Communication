    package com.mahmoudelshahat.emitter.ui.adapter

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
import com.mahmoudelshahat.emitter.R
import com.mahmoudelshahat.emitter.data.network.response.User


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
            Toast.makeText(context, "List is empty", Toast.LENGTH_LONG).show()
        }
        return usersList.size
    }


    fun clear() {
        usersList.clear()
        notifyDataSetChanged()
    }

    inner class PostsViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        val name = binding.findViewById(R.id.name) as TextView
        val username = binding.findViewById(R.id.user_name) as TextView
        val email = binding.findViewById(R.id.email) as TextView

        fun bind(user: User) {
            name.text = user.name
            username.text = user.username
            email.text = user.email

            binding.setOnClickListener(View.OnClickListener {
                openDialog(user)
            })
        }


        private fun openDialog(user: User) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Alert")
            builder.setMessage("Are you sure to send this user to middle man app ?")
            builder.setPositiveButton("Yes") { _, _ ->
                val intent = Intent()
                intent.action = "com.mahmoudelshahat.perform"
                intent.putExtra("user", user)
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                intent.component = ComponentName(
                    "com.mahmoudelshahat.middleman",
                    "com.mahmoudelshahat.middleman.data.receiver.EmitterReceiver"
                )
                context.sendBroadcast(intent)
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.show()
        }


    }

}