package com.mahmoudelshahat.emitter.ui


import android.app.AlertDialog
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mahmoudelshahat.emitter.R
import com.mahmoudelshahat.emitter.data.network.api.UsersApiService
import com.mahmoudelshahat.emitter.data.network.response.User
import com.mahmoudelshahat.emitter.data.receiver.MiddleManReceiver
import com.mahmoudelshahat.emitter.data.repository.UsersRepository
import com.mahmoudelshahat.emitter.ui.adapter.UsersAdapter
import kotlinx.coroutines.*


class UsersActivity : AppCompatActivity(), LifecycleOwner {


    lateinit var viewModel: UsersViewModel
    private val usersApiService = UsersApiService()
    private lateinit var builder:AlertDialog.Builder
    lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mainrecycler: RecyclerView
    private var viewManager = LinearLayoutManager(this)
    lateinit var middleReceiver: MiddleManReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup recycler view with swipe view
        mainrecycler = findViewById(R.id.recycler)
        swipeRefresh = findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            mainrecycler.adapter=null
            bindUi()
        }

        //register broadcast receiver for receiving from middle man
        middleReceiver = MiddleManReceiver()
        val intentFilter = IntentFilter("com.mahmoudelshahat.perform");
        registerReceiver(middleReceiver, intentFilter)


        //get reference to view model
        viewModel = ViewModelProvider(this, UsersModelFactory(UsersRepository(usersApiService)))
            .get(UsersViewModel::class.java)
        //set listener to data that came from api
        bindUi()
    }

    private fun bindUi() {
        MainScope().launch {
            withContext(Dispatchers.Main) {
                val allUsers = viewModel.usersResponse.await()
                    allUsers.observe(this@UsersActivity, Observer {
                    if (it == null) return@Observer
                    viewModel.response.value = it
                    val users: ArrayList<User> = viewModel.response.value!!
                    mainrecycler.layoutManager = viewManager

                    mainrecycler.adapter = UsersAdapter(users, this@UsersActivity)
                    swipeRefresh.isRefreshing = false

                })
            }

        }
    }
    override fun onResume() {
        super.onResume()
        builder=AlertDialog.Builder(this)
        Handler(Looper.getMainLooper()).postDelayed({
            val response:String = checkIfThereIsResponse()
            if (response != "none")
                openDialog(response)


        }, 200)
    }
    // checking if there is a response from broadcast receiver
    private fun checkIfThereIsResponse(): String {
        val sharedPreference = getSharedPreferences("emitter", Context.MODE_PRIVATE)
        return sharedPreference.getString("status", "none")!!

    }

    //if there is  a response will show a dialog
    private fun openDialog(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sending Status")
        builder.setMessage(
            message
        )
        builder.setPositiveButton("Confirm") { _, _ ->
        }
        builder.show()

        val sharedPreference = getSharedPreferences("emitter", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.remove("status").apply()
    }



}




