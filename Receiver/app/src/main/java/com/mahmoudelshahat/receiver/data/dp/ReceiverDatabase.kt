package com.mahmoudelshahat.receiver.data.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahmoudelshahat.emitter.data.network.response.User


@Database(
    entities = [User::class],
    version = 1
)
abstract class ReceiverDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao


    companion object {

        @Volatile
        private var INSTANCE: ReceiverDatabase? = null
        fun getDatabase(context: Context): ReceiverDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReceiverDatabase::class.java,
                    "receiver_database"
                ).build()
                INSTANCE = instance
                // return instance
                 instance
            }
        }
    }
}