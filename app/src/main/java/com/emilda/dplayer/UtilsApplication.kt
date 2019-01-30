package com.emilda.dplayer

import android.app.Application
import com.emilda.dplayer.DataClass.SongType
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UtilsApplication: Application() {

    companion object {
        @get:Synchronized
        lateinit var fireDbAPP:FirebaseDatabase
        private set

        @get:Synchronized
        var songRefAPP:SongType?=null

        @get:Synchronized
        lateinit var dbRefAPP:DatabaseReference
    }

    override fun onCreate() {
        super.onCreate()
        fireDbAPP = FirebaseDatabase.getInstance()
        dbRefAPP = fireDbAPP.reference

    }


}