package com.mahmoudelshahat.emitter.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*

import com.mahmoudelshahat.emitter.data.network.response.UsersResponse
import com.mahmoudelshahat.emitter.data.repository.UsersRepository


class UsersViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {


    init {

    }
    var usersResponse =
        GlobalScope.async(start = CoroutineStart.LAZY) {
            usersRepository.getAllUsers()
        }

    val response= MutableLiveData<UsersResponse>()


}