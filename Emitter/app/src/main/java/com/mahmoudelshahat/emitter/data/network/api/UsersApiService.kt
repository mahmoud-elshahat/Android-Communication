
package com.mahmoudelshahat.emitter.data.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mahmoudelshahat.emitter.data.network.response.UsersResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


//https://jsonplaceholder.typicode.com/users

interface UsersApiService {

    @GET("users")
    fun getAllUsers(
    ):Deferred<UsersResponse>
    
    companion object{
        operator fun invoke():UsersApiService{
            val requestInterceptor=Interceptor{chain ->

                val url=chain.request()
                    .url()
                    .newBuilder()
                    .build()

                val request=chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsersApiService::class.java)
        }
    }

}