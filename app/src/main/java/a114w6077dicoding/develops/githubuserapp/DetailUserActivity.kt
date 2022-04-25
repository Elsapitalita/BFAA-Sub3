package a114w6077dicoding.develops.githubuserapp

import a114w6077dicoding.develops.githubuserapp.databinding.ActivityDetailUserBinding
import a114w6077dicoding.develops.githubuserapp.detail.DetailUserViewModel
import a114w6077dicoding.develops.githubuserapp.detail.SectionPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var bindingActivity: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(bindingActivity.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        val user = intent.extras

        viewModel = ViewModelProvider(
            this,).get(DetailUserViewModel::class.java)

        showLoading(true)

        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                bindingActivity.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
                showLoading(false)
            }
        })

        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if(count>0){
                        bindingActivity.toggleFavorite.isChecked = true
                        checked = true
                    }else{
                        bindingActivity.toggleFavorite.isChecked = false
                        checked = false
                    }
                }
            }
        }

        bindingActivity.toggleFavorite.setOnClickListener{
            checked = !checked
            if (checked){
                viewModel.addToFavorite(username.toString(), id, avatarUrl.toString())
            }else{
                viewModel.removeFromFavorite(id)
            }
            bindingActivity.toggleFavorite.isChecked = checked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this,
            user?.getString(EXTRA_USERNAME).toString())
        bindingActivity.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(bindingActivity.tabs, bindingActivity.viewPager){
                tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }


    }
    private fun showLoading(stateshow: Boolean) {
        if (stateshow) {
            bindingActivity.progressBar.visibility = View.VISIBLE
        } else {
            bindingActivity.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )

        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

}