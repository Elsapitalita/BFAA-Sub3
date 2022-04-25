package a114w6077dicoding.develops.githubuserapp.detail

import a114w6077dicoding.develops.githubuserapp.api.RetrofitClient
import a114w6077dicoding.develops.githubuserapp.local.model.FavoriteUser
import a114w6077dicoding.develops.githubuserapp.local.model.FavoriteUserDao
import a114w6077dicoding.develops.githubuserapp.local.model.UserDatabase
import a114w6077dicoding.develops.githubuserapp.model.DetailUserResponse
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val detailUser = MutableLiveData<DetailUserResponse>()
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)


    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance.getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    Userresponse: Response<DetailUserResponse>
                ) {
                    if (Userresponse.isSuccessful) {
                        detailUser.postValue(Userresponse.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> = detailUser

    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

}