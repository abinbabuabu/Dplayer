package com.emilda.dplayer


import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.emilda.dplayer.Factory.SharedViewModelFactory
import com.emilda.dplayer.ViewModels.sharedViewModel

class MainActivity : AppCompatActivity() {
   lateinit var viewModel: sharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //View Model Created
        viewModel = ViewModelProviders.of(this, SharedViewModelFactory("somerandomvalue")).get(sharedViewModel::class.java)
//        viewModel?.getSongList()?.observe(this, Observer {
//            Log.d("FUCK","THIS LIVE DATA")
//        })?: Log.d("FUCK","THIS LIVE DATA")
//        Log.d("FUCk",viewModel.getSongList().toString())


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        viewModel.releasePlayer(this)
    }
}
