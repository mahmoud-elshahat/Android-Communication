package com.mahmoudelshahat.receiver.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudelshahat.emitter.data.network.response.User
import com.mahmoudelshahat.receiver.R
import com.mahmoudelshahat.receiver.data.dp.shared_pref.ReceiverShared
import com.mahmoudelshahat.receiver.data.socket.ClientThread
import com.mahmoudelshahat.receiver.logVerbose
import com.mahmoudelshahat.receiver.ui.adapter.UsersAdapter
import java.io.*
import java.util.*


class ReceivedUsersActivity : AppCompatActivity() {

    lateinit var clientThread: ClientThread
    lateinit var thread: Thread

    var allUsers: List<User>? = null
    lateinit var viewModel: ReceivedUsersViewModel
    private lateinit var mainrecycler: RecyclerView
    private var viewManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_received_users)
        mainrecycler = findViewById(R.id.users_recycler)


        //check if is ip already entered or no
        //if not entered so open dialog for get it
        // if its entered  so start client and connect to the server
        val ip: String = ReceiverShared.getCurrentIp(this)
        if (ip == "none")
            openDialogForServerIp()
        else
            startClient(ip)

        //reference to view model
        viewModel = ViewModelProvider(this, ReceivedUsersViewModelFactory(application))
            .get(ReceivedUsersViewModel::class.java)

        //observing data
        viewModel.readAllData.observe(this, Observer { users ->
            mainrecycler.layoutManager = viewManager
            allUsers = users
        })


    }

    private fun startClient(ip: String) {
        clientThread = ClientThread(ip,this)
        thread = Thread(clientThread)
        thread.start()
        Toast.makeText(this, "Connected to the server.", Toast.LENGTH_SHORT).show()
    }

    fun showData(view: View) {
        mainrecycler.adapter = UsersAdapter(allUsers as ArrayList<User>, this)
        mainrecycler.isVisible = true
    }

    private fun openDialogForServerIp() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val editText = EditText(this);
        builder.setView(editText);
        builder.setTitle("Alert")
        builder.setMessage(
            "In order to continue you must enter ip of current device for " +
                    "local server client methodology workflow\n example: 192.168.1.6 "
        )
        builder.setPositiveButton("Confirm") { _, _ ->
            val ip: String = editText.text.toString()
            logVerbose(ip)
            ReceiverShared.setIp(ip, this)
            startClient(ip)
        }
        builder.show()
        val parameter = editText.layoutParams as FrameLayout.LayoutParams
        parameter.setMargins(
            50,
            10,
            50,
            10
        )
        editText.layoutParams = parameter
    }

}