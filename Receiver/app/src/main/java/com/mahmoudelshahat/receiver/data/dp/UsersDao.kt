package com.mahmoudelshahat.receiver.data.dp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoudelshahat.emitter.data.network.response.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
//
    @Query("SELECT * FROM users ")
    fun getAllUsers(): LiveData<List<User>>
//
}