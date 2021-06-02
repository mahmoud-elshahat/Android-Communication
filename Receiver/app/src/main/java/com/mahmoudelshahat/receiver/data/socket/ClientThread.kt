package com.mahmoudelshahat.receiver.data.socket


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.mahmoudelshahat.emitter.data.network.response.User
import com.mahmoudelshahat.receiver.data.dp.ReceiverDatabase
import com.mahmoudelshahat.receiver.data.repoistory.UsersRepository
import com.mahmoudelshahat.receiver.logVerbose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import kotlin.math.log


class ClientThread(private val serverIp:String,private val context: Context) : Runnable {
    private val SERVER_PORT = 3003

    private var socket: Socket? = null
    private var input: BufferedReader? = null
    override fun run() {
        try {
            val serverAddr: InetAddress = InetAddress.getByName(serverIp)
            logVerbose(serverIp)
            socket = Socket(serverAddr, SERVER_PORT)
            while (!Thread.currentThread().isInterrupted) {
                input = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                val message: String = input!!.readLine()

                if(message!=""){
                    val gson = Gson()
                    val receivedUser:User=gson.fromJson(message, User::class.java)
                    addUser(receivedUser)

                }


                logVerbose(message)
            }
        } catch (e1: UnknownHostException) {
            e1.printStackTrace()
            logVerbose(e1.message.toString())
            logVerbose(e1.toString())


        } catch (e1: IOException) {
            e1.printStackTrace()
            logVerbose(e1.message.toString())
            logVerbose(e1.toString())

        }
    }

     fun addUser(user: User){

        GlobalScope.launch (Dispatchers.IO){
            try{
                val userDao = ReceiverDatabase.getDatabase(context).usersDao()
                val repository = UsersRepository(userDao)
                repository.insert(user)
                sendMessage("OK")
            }catch (e:java.lang.Exception)
            {
                sendMessage("NOK")

            }

        }
    }
    fun sendMessage(message: String?) {
        Thread {
            try {
                if (null != socket) {
                    val out = PrintWriter(
                        BufferedWriter(
                        OutputStreamWriter(socket?.getOutputStream())
                        ),
                        true)
                    out.println(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                logVerbose(e.message.toString())

            }
        }.start()
    }

}

