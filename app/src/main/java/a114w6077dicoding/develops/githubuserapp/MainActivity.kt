package a114w6077dicoding.develops.githubuserapp

import a114w6077dicoding.develops.githubuserapp.databinding.ActivityMainBinding
import a114w6077dicoding.develops.githubuserapp.favorite.FavoriteActivity
import a114w6077dicoding.develops.githubuserapp.model.User
import a114w6077dicoding.develops.githubuserapp.util.SettingPreferences
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var bindingActivity: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var cekTheme = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingActivity.root)

        val pref = SettingPreferences.getInstance(dataStore)
        viewModel = MainViewModel(pref)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        bindingActivity.rvUser.setHasFixedSize(true)
        bindingActivity.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setSearchUsers(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        bindingActivity.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            search.setOnClickListener {
                searchUser()
            }
            search.setOnKeyListener { _, KeyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && KeyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })


        viewModel.getThemeSettings().observe(this,{
            isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            cekTheme = isDarkModeActive
        })
    }

    private fun searchUser() {
        bindingActivity.apply {
            val query = search.query.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            bindingActivity.progressBar.visibility = View.VISIBLE
        } else {
            bindingActivity.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.action_setting ->{
                if (cekTheme){
                    viewModel.saveThemeSetting(false)
                } else{
                    viewModel.saveThemeSetting(true)
                }
                true
            }

            R.id.favorite_menu->{
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}