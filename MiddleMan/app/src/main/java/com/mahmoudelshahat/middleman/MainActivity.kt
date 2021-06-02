package com.mahmoudelshahat.middleman

import android.app.AlertDialog
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.mahmoudelshahat.emitter.data.network.response.User
import com.mahmoudelshahat.middleman.data.receiver.EmitterReceiver
import com.mahmoudelshahat.middleman.data.socket.ServerThread


lateinit var emitterReceiver: EmitterReceiver


class MainActivity : AppCompatActivity() {
    lateinit var serverThread:ServerThread

    private lateinit var builder:AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //register receiver
        emitterReceiver = EmitterReceiver()
        val intentFilter = IntentFilter("com.mahmoudelshahat.perform");
        registerReceiver(emitterReceiver, intentFilter)

        //starting server
        serverThread= ServerThread(this)
        serverThread.startServer()
        Toast.makeText(this, "Server started.", Toast.LENGTH_SHORT).show()
    }



    override fun onDestroy() {
        super.onDestroy();
        unregisterReceiver(emitterReceiver);
    }

    override fun onResume() {
        super.onResume()
        builder=AlertDialog.Builder(this)
        Handler(Looper.getMainLooper()).postDelayed({
            val user = checkIfThereIsReceivedUser()
            if (user != null)
                openDialog(user)


        }, 200)
    }


    //check if there is user from emitter app
    private fun checkIfThereIsReceivedUser(): User? {
        val sharedPreference = getSharedPreferences("users", Context.MODE_PRIVATE)
        val gson = Gson()

        val json: String = sharedPreference.getString("last_user", "none")!!
        return if (json == "none")
            null
        else{
            gson.fromJson(json, User::class.java)
        }

    }


    // if there is user then show a dialog
    private fun openDialog(user: User) {
        builder.setTitle("Alert")
        builder.setMessage(
            "Just received user with:\n" +
                    "\nname: ${user.name}" +
                    "\nusername: ${user.username}" +
                    "\nemail: ${user.email}" +
                    "Send it to receiver app ?"
        )
        builder.setPositiveButton("Confirm") { _, _ ->
            val gson = Gson();
            val json = gson.toJson(user);
            serverThread.sendMessageToClient(json)
            Toast.makeText(this,"User have been sent to receiver app",
                Toast.LENGTH_SHORT).show()

        }
        builder.show()
        val sharedPreference = getSharedPreferences("users", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.remove("last_user").apply()
    }

}


