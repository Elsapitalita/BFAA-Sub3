package a114w6077dicoding.develops.githubuserapp.api

import a114w6077dicoding.develops.githubuserapp.BuildConfig
import a114w6077dicoding.develops.githubuserapp.model.DetailUserResponse
import a114w6077dicoding.develops.githubuserapp.model.User
import a114w6077dicoding.develops.githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}