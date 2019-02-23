package com.emilda.dplayer


import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.emilda.dplayer.Adapter.mPagerAdapter
import com.emilda.dplayer.Factory.SharedViewModelFactory
import com.emilda.dplayer.ViewModels.sharedViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN = 1
    lateinit var viewModel: sharedViewModel
    lateinit var searchView: MaterialSearchView
    lateinit var mtoolbar: androidx.appcompat.widget.Toolbar
    private var userId: String = "sample"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent.getStringExtra("USER_ID")


        viewModel =
            ViewModelProviders.of(this, SharedViewModelFactory(userId)).get(sharedViewModel::class.java)
        Log.d("FUCK", viewModel.userid + "VM")

        mtoolbar = toolbar
        setSupportActionBar(mtoolbar)
        searchView = search_view as MaterialSearchView


        val fragmentAdapter = mPagerAdapter(supportFragmentManager)
        view_pager.adapter = fragmentAdapter
        main_tab_layout.setupWithViewPager(view_pager)

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

    }


}
