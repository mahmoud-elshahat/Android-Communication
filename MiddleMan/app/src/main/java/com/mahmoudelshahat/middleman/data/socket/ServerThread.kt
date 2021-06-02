package com.mahmoudelshahat.middleman.data.socket


import android.content.Context
import com.mahmoudelshahat.middleman.SERVER_PORT
import com.mahmoudelshahat.middleman.log
import com.mahmoudelshahat.receiver.data.socket.CommunicationThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.ServerSocket
import java.net.Socket



class ServerThread (val context: Context){
     private var communicationThread: CommunicationThread? =null
    var serverSocket: ServerSocket? = null
    fun startServer() {
        log("starting server")

        GlobalScope.launch(Dispatchers.IO) {
            var socket: Socket
            try {
                serverSocket = ServerSocket(SERVER_PORT)
            } catch (e: IOException) {
                e.printStackTrace()
                log(e.message.toString())
            }
            if (null != serverSocket) {
                while (!Thread.currentThread().isInterrupted) {
                    try {
                        socket = serverSocket!!.accept()
                        communicationThread = CommunicationThread(socket,context)
                        Thread(communicationThread).start()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        log(e.message.toString())
                    }
                }
            }
            log("no there")

        }

    }

    fun sendMessageToClient(message:String)
    {
        communicationThread?.sendMessage(message)
    }
}