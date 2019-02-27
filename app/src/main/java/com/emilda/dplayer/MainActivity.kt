package com.emilda.dplayer


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.Adapter.SongsAdapter
import com.emilda.dplayer.Adapter.mPagerAdapter
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Factory.SharedViewModelFactory
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.Reciever.ConnectivityReceiverLive
import com.emilda.dplayer.ViewModels.sharedViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main_contents.*
import kotlinx.android.synthetic.main.mini_player_layout.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: sharedViewModel
    private lateinit var searchView: MaterialSearchView
    private lateinit var mtoolbar: androidx.appcompat.widget.Toolbar
    private var userId: String = "sample"
    private var new_user = true
    private lateinit var adapter: SongsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent.getStringExtra("USER_ID")
        new_user = intent.getBooleanExtra("NEW_USER", true)


        viewModel =
            ViewModelProviders.of(this, SharedViewModelFactory(userId, new_user)).get(sharedViewModel::class.java)

        mtoolbar = toolbar
        setSupportActionBar(mtoolbar)
        searchView = search_view as MaterialSearchView

        recyclerView = search_results
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerSetup("sample")


        val fragmentAdapter = mPagerAdapter(supportFragmentManager)
        view_pager.adapter = fragmentAdapter
        main_tab_layout.setupWithViewPager(view_pager)
        internet()
        createSearch()
        mini_player.player = viewModel.player

    }


    override fun onDestroy() {
        if (viewModel != null)
            viewModel.releasePlayer(this)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        var menuItem = menu?.findItem(R.id.search)
        searchView.setMenuItem(menuItem)
        return super.onCreateOptionsMenu(menu)
    }

    fun createSearch() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                recyclerSetup(query!!)
                adapter.startListening()
                Log.d("FUCK", query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerSetup(newText!!)
                Log.d("FUCK", newText)
                return true
            }

        })

        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                search_results.visibility = View.GONE
                main_tab_layout.visibility = View.VISIBLE
                view_pager.visibility = View.VISIBLE
            }

            override fun onSearchViewShown() {
                main_tab_layout.visibility = View.GONE
                view_pager.visibility = View.GONE
                search_results.visibility = View.VISIBLE
            }

        })

    }

    private fun internet() {
        val connectionLiveData = ConnectivityReceiverLive(application)
        connectionLiveData.observe(this, Observer {
            if (it)
                Toast.makeText(this, "Connection Available", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Connection Lost", Toast.LENGTH_LONG).show()
        })
    }

    private fun recyclerSetup(query: String) {
        var options = viewModel.searchFiredb(query)
        adapter = SongsAdapter(options, this, object : songClickListener {
            override fun onSongClick(song: SongType, int: Int) {
                // Toast.makeText(,"Clicked",Toast.LENGTH_LONG).show()
            }
        })

        recyclerView.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}
