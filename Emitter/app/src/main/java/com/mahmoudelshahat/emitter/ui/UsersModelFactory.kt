package com.mahmoudelshahat.emitter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudelshahat.emitter.data.repository.UsersRepository

class UsersModelFactory(private  val usersRepository: UsersRepository)   : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(usersRepository) as T
    }
}
