package a114w6077dicoding.develops.githubuserapp.detail

import a114w6077dicoding.develops.githubuserapp.DetailUserActivity
import a114w6077dicoding.develops.githubuserapp.R
import a114w6077dicoding.develops.githubuserapp.UserAdapter
import a114w6077dicoding.develops.githubuserapp.databinding.FragmentFollowBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        println("username $username")
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            user ->
            if (user != null) {
                println(user)
                adapter.setList(user)
                showLoading(false)
            }
        })
    }

    private fun showLoading(ShowState: Boolean) {
        if (ShowState) {
            binding.pbFollow.visibility = View.VISIBLE
        } else {
            binding.pbFollow.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}