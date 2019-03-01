package com.emilda.dplayer

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.emilda.dplayer.Adapter.mPagerAdapter
import com.emilda.dplayer.Factory.SharedViewModelFactory
import com.emilda.dplayer.Reciever.ConnectivityReceiverLive
import com.emilda.dplayer.ViewModels.sharedViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main_contents.*
import kotlinx.android.synthetic.main.app_bar_details.*
import kotlinx.android.synthetic.main.mini_player_layout.*
import kotlinx.android.synthetic.main.player_view.*

class detailsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var searchView: MaterialSearchView
    // private lateinit var adapter: SongsAdapter
    //private lateinit var recyclerView: RecyclerView
    private var userId: String = "sample"
    private var new_user = true
    private lateinit var viewModel: sharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        searchView = search_view as MaterialSearchView
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.round_menu_24)
        }


        userId = intent.getStringExtra("USER_ID")
        new_user = intent.getBooleanExtra("NEW_USER", true)

        viewModel =
            ViewModelProviders.of(this, SharedViewModelFactory(userId, new_user)).get(sharedViewModel::class.java)
        viewModel.initializePlayer(this)


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


//        recyclerView = search_results
//        recyclerView.layoutManager = LinearLayoutManager(this)


        val fragmentAdapter = mPagerAdapter(supportFragmentManager)
        view_pager.adapter = fragmentAdapter
        main_tab_layout.setupWithViewPager(view_pager)
        internet()
        createSearch()
       setBottomSheet()
        player_exo.player = viewModel.player
        mini_player.player = viewModel.player





    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.details, menu)
        var menuItem  = menu.findItem(R.id.search)
        searchView.setMenuItem(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun createSearch() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //recyclerSetup(query!!)
                // adapter.startListening()
                Log.d("FUCK", query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //recyclerSetup(newText!!)
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

//   private fun recyclerSetup(query: String) {
//        var options = viewModel.searchFiredb(query)
//        adapter = SongsAdapter(options, this, object : songClickListener {
//            override fun onSongClick(song: SongType, id: Int) {
//                // Toast.makeText(,"Clicked",Toast.LENGTH_LONG).show()
//            }
//        })
//
//        recyclerView.adapter = adapter
//    }

    private fun internet() {
        val connectionLiveData = ConnectivityReceiverLive(application)
        connectionLiveData.observe(this, Observer {
            if (it)
                Toast.makeText(this, "Connection Available", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Connection Lost", Toast.LENGTH_LONG).show()
        })
    }

    override fun onDestroy() {
        if (viewModel != null)
            viewModel.releasePlayer(this)
        // adapter.stopListening()
        super.onDestroy()
    }


    fun setBottomSheet(){
        val bottomSheetBehaviour = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehaviour.isHideable = false
        bottomSheetBehaviour.peekHeight = 208
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
//      val bottomSheet = PlayerFragment()
//        bottomSheet.show(supportFragmentManager,"playerView")
        mini_player_layout.setOnClickListener{
          bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }



        bottomSheetBehaviour.setBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(p0: View, p1: Float) {
                Log.d("Alpha","${1-2*p1}")
                mini_player_bottom.alpha = 1-2*p1
            }

            override fun onStateChanged(view: View, state: Int) {
                when(state){
                    BottomSheetBehavior.STATE_EXPANDED -> mini_player_bottom.visibility = View.GONE
                    BottomSheetBehavior.STATE_COLLAPSED -> {mini_player_bottom.visibility = View.VISIBLE
                    mini_player_bottom.alpha = 1.0f
                    }
                    BottomSheetBehavior.STATE_DRAGGING ->mini_player_bottom.visibility = View.VISIBLE
                }

            }
        }
        )

    }


}
