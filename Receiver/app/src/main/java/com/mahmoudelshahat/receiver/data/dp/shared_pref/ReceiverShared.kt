package com.mahmoudelshahat.receiver.data.dp.shared_pref

import android.content.Context

class ReceiverShared {
    companion object {
        const val sharedName: String = "receiver"
        const val ipName: String = "ip"

        fun getCurrentIp(context: Context): String {
            val sharedPreference = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE)
            return sharedPreference.getString(ipName, "none")!!
        }

        fun setIp(ip:String,context: Context)
        {
            val sharedPreference = context.getSharedPreferences("receiver", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("ip",ip)
            editor?.apply()
        }

    }


}