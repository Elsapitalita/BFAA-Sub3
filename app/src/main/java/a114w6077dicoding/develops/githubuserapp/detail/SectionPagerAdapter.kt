package a114w6077dicoding.develops.githubuserapp.detail

import a114w6077dicoding.develops.githubuserapp.DetailUserActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putString(DetailUserActivity.EXTRA_USERNAME, username)
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        fragment?.arguments = bundle
        return fragment as Fragment
    }
}