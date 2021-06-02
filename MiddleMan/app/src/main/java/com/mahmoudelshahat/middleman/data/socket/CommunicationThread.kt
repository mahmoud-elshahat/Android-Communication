package com.mahmoudelshahat.receiver.data.socket

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.mahmoudelshahat.middleman.log
import java.io.*
import java.net.Socket

class CommunicationThread(clientSocket: Socket,val context: Context) : Runnable {
    private var input: BufferedReader? = null
    var tempClientSocket: Socket? = null

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            try {
                var read: String = input?.readLine()!!
                if ("Disconnect".contentEquals(read)) {
                    Thread.interrupted()
                    read = "Client Disconnected"
                    log(read)
                    break
                }
                log(read)
                if(read == "OK")
                {
                    val intent = Intent()
                    intent.action = "com.mahmoudelshahat.perform"
                    intent.putExtra("status", read)
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    intent.component = ComponentName(
                        "com.mahmoudelshahat.emitter",
                        "com.mahmoudelshahat.emitter.data.receiver.MiddleManReceiver"
                    )
                    context.sendBroadcast(intent)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    init {
        tempClientSocket = clientSocket
        try {
            input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        } catch (e: IOException) {
            e.printStackTrace()
            log( "Error Connecting to Client!!")

        }
        log("Connected to Client!!")

    }


    fun sendMessage(message: String) {
        try {
            if (null != tempClientSocket) {
                Thread {
                    var out: PrintWriter? = null
                    try {
                        out = PrintWriter(
                            BufferedWriter(
                                OutputStreamWriter(tempClientSocket!!.getOutputStream())
                            ),
                            true
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        log(e.message.toString())
                    }
                    out?.println(message)
                }.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            log(e.message.toString())

        }
    }
}