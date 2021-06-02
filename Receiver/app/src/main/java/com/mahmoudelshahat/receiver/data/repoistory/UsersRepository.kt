package com.mahmoudelshahat.receiver.data.repoistory

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.mahmoudelshahat.emitter.data.network.response.User
import com.mahmoudelshahat.receiver.data.dp.ReceiverDatabase
import com.mahmoudelshahat.receiver.data.dp.UsersDao
import kotlinx.coroutines.flow.Flow

class UsersRepository (private val usersDao: UsersDao) {

    val allUsers: LiveData<List<User>> = usersDao.getAllUsers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        usersDao.insert(user)
    }
}