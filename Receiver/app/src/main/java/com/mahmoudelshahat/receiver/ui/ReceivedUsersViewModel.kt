package com.mahmoudelshahat.receiver.ui

import android.app.Application
import androidx.lifecycle.*
import com.mahmoudelshahat.emitter.data.network.response.User
import com.mahmoudelshahat.receiver.data.dp.ReceiverDatabase
import com.mahmoudelshahat.receiver.data.repoistory.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReceivedUsersViewModel(application: Application) : ViewModel() {

    val readAllData: LiveData<List<User>>
    lateinit var repository: UsersRepository

    init {
        val userDao = ReceiverDatabase.getDatabase(application).usersDao()
        repository = UsersRepository(userDao)
        readAllData = repository.allUsers
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.insert(user)
        }
    }
}


class ReceivedUsersViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceivedUsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReceivedUsersViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}