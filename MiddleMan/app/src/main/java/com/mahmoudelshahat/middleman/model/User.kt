package  com.mahmoudelshahat.emitter.data.network.response

import android.os.Parcelable

import kotlinx.parcelize.Parcelize


@Parcelize
data class User(

    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
) : Parcelable