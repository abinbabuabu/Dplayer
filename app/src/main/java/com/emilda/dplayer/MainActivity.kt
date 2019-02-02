package com.emilda.dplayer


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //View Model Created
        val viewModel = ViewModelProviders.of(this,CustomViewModelFactory("somerandomvalue")).get(sharedViewModel::class.java)
//        sharedViewModel.number.observe(this, Observer {
//            it?.let {
//                Log.d("FUCK","View model Created")
//            }
//        })
        Log.d("FUCk",viewModel.getSongList().toString())


    }
}
