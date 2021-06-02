package com.mahmoudelshahat.emitter.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudelshahat.emitter.data.network.api.UsersApiService
import com.mahmoudelshahat.emitter.data.network.response.UsersResponse
import com.mahmoudelshahat.forecastapp.data.network.api.UsersNetworkDataSource


class UsersRepository(
    private val usersApiService: UsersApiService,
)  {


    private val _downloadedUsers= MutableLiveData<UsersResponse>()



    suspend fun getAllUsers(): MutableLiveData<UsersResponse> {
        try {
            val fetchedCurrentWeather = usersApiService
                .getAllUsers()
                .await()

            _downloadedUsers.postValue(fetchedCurrentWeather)
            return _downloadedUsers

        } catch (e: Exception) {
            Log.e("error", e.message.toString())
              return _downloadedUsers
        }
    }}
