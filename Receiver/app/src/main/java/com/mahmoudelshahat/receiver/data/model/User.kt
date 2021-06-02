package  com.mahmoudelshahat.emitter.data.network.response


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")

@Parcelize
data class User(
    val email: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
) : Parcelable