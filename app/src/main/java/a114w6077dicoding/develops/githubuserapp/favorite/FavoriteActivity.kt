package a114w6077dicoding.develops.githubuserapp.favorite

import a114w6077dicoding.develops.githubuserapp.DetailUserActivity
import a114w6077dicoding.develops.githubuserapp.UserAdapter
import a114w6077dicoding.develops.githubuserapp.databinding.ActivityFavoriteBinding
import a114w6077dicoding.develops.githubuserapp.local.model.FavoriteUser
import a114w6077dicoding.develops.githubuserapp.model.User
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class FavoriteActivity : AppCompatActivity() {

    private lateinit var bindingActivity: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(bindingActivity.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    detail ->
                    detail.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    detail.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    detail.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(detail)
                }
            }
        })

        bindingActivity.apply {
            rvUserFav.setHasFixedSize(true)
            rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFav.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            user ->
            if (user != null) {
                val list = mapList(user)
                adapter.setList(list)
            }
        })

    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val UserList = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            UserList.add(userMapped)
        }
        return UserList
    }
}