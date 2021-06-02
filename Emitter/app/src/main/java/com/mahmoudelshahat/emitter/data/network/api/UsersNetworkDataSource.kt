package com.mahmoudelshahat.forecastapp.data.network.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudelshahat.emitter.data.network.response.UsersResponse

interface UsersNetworkDataSource {
    val downloadedUsers:LiveData<UsersResponse>

    suspend fun fetchCurrentWeather(): MutableLiveData<UsersResponse>
    suspend fun getAllUsers(): MutableLiveData<UsersResponse>
}