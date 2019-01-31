package com.emilda.dplayer


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //View Model Created
        val sharedViewModel = ViewModelProviders.of(this).get(sharedViewModel::class.java)
//        sharedViewModel.number.observe(this, Observer {
//            it?.let {
//                Log.d("FUCK","View model Created")
//            }
//        })
        sharedViewModel.lookForDbChange()

    }
}
