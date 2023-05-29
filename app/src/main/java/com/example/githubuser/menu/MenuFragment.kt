package com.example.githubuser.menu

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.api.GithubResponse
import com.example.githubuser.databinding.FragmentMenuBinding


class MenuFragment: Fragment(){
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private var listUser = ArrayList<GithubResponse>()
    private val menuViewModel by activityViewModels<MenuViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.supportBarTitle)
        }
        menuViewModel.listUser.observe(viewLifecycleOwner){ listUser ->
            setUserData(listUser)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.setHasFixedSize(true)

        menuViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        val item = menu.findItem(R.id.theme)
        val title = SpannableString("Change Theme")

        val nightModeFlags = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> title.setSpan(ForegroundColorSpan(Color.WHITE),0,title.length,0)
            Configuration.UI_MODE_NIGHT_NO -> title.setSpan(ForegroundColorSpan(Color.BLACK),0,title.length,0)
        }
        item.title = title

        val search = menu.findItem(R.id.search).actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    listUser.clear()
                    menuViewModel.searchUser(query)
                    setUserData(listUser)
                    search.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.theme -> {
                val findNavigate = findNavController()
                findNavigate.navigate(R.id.action_menuFragment_to_switchFragment)
            }
            R.id.favorite -> {
                val findNavigate = findNavController()
                findNavigate.navigate(R.id.action_menuFragment_to_favoriteFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUserData(listUser: List<GithubResponse>?) {
        val adapter = listUser?.let { MenuAdapter(it) }
        binding.rvUsers.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}