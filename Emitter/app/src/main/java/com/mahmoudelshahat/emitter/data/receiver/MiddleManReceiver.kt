package com.mahmoudelshahat.emitter.data.receiver

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.EditText
import android.widget.FrameLayout
import com.google.gson.Gson
import com.mahmoudelshahat.emitter.data.network.response.User

class MiddleManReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val response: String = intent?.getStringExtra("status")!!
        Log.e("RESPONSE_TAG", response)
        Handler(Looper.getMainLooper()).post {
            val sharedPreference = context?.getSharedPreferences("emitter", Context.MODE_PRIVATE)
            val editor = sharedPreference?.edit()
            editor?.putString("status", response);
            editor?.apply()
        }
    }



}