package com.emilda.dplayer


import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.emilda.dplayer.Adapter.mPagerAdapter
import com.emilda.dplayer.Factory.SharedViewModelFactory
import com.emilda.dplayer.ViewModels.sharedViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN = 1
    lateinit var viewModel: sharedViewModel
    lateinit var searchView: MaterialSearchView
    lateinit var mtoolbar: androidx.appcompat.widget.Toolbar
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mtoolbar = toolbar
        setSupportActionBar(mtoolbar)
        searchView = search_view as MaterialSearchView

        viewModel =
            ViewModelProviders.of(this, SharedViewModelFactory("somerandomvalue")).get(sharedViewModel::class.java)
        val fragmentAdapter = mPagerAdapter(supportFragmentManager)
        view_pager.adapter = fragmentAdapter
        main_tab_layout.setupWithViewPager(view_pager)


        //Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            var user = it.currentUser
            if (user != null) {
                //user signed in
            } else {
                //user signed out
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            Arrays.asList(
                                AuthUI.IdpConfig.GoogleBuilder().build(),
                                AuthUI.IdpConfig.EmailBuilder().build()
                            )
                        ).build()
                ,RC_SIGN_IN)
            }

        }

    }


    override fun onDestroy() {
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

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener (mAuthStateListener)
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener (mAuthStateListener)
    }


}
