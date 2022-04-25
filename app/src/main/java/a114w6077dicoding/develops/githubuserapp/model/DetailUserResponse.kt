package a114w6077dicoding.develops.githubuserapp.model

import androidx.annotation.NonNull
import androidx.room.PrimaryKey

data class DetailUserResponse(
    @PrimaryKey
    @NonNull
    val id : Int,
    @NonNull
    val login : String,
    @NonNull
    val avatar_url : String,
    @NonNull
    val followers_url : String,
    @NonNull
    val following_url : String,
    @NonNull
    val name : String,
    @NonNull
    val following : Int,
    @NonNull
    val followers : Int
)
