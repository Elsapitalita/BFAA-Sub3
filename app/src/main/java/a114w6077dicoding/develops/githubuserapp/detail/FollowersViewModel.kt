package a114w6077dicoding.develops.githubuserapp.detail

import a114w6077dicoding.develops.githubuserapp.api.RetrofitClient
import a114w6077dicoding.develops.githubuserapp.model.User
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, error: Throwable) {
                    Log.d("Failure", error.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> = listFollowers
}