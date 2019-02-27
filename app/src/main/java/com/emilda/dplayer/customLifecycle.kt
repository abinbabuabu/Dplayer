package com.emilda.dplayer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class customLifecycle :LifecycleOwner {
    private var mlifecycleReg:LifecycleRegistry = LifecycleRegistry(this)

    init {
        mlifecycleReg.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }
    
     fun stopListening() {
         mlifecycleReg.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }
    fun startListening(){
        mlifecycleReg.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }


    override fun getLifecycle(): Lifecycle {
        Log.i("CustomLifeCycleOwner","Returning registry!!")
        return mlifecycleReg
    }
}