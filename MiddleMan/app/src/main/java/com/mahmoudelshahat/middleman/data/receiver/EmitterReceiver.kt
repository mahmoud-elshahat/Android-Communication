package com.mahmoudelshahat.middleman.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.mahmoudelshahat.emitter.data.network.response.User


class EmitterReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val receivedUser: User = intent?.getParcelableExtra("user")!!
        Handler(Looper.getMainLooper()).post {
            val sharedPreference = context?.getSharedPreferences("users", Context.MODE_PRIVATE)
            val editor = sharedPreference?.edit()
            val gson = Gson();
            val json = gson.toJson(receivedUser);
            editor?.putString("last_user", json);
            editor?.apply()
        }
    }
}