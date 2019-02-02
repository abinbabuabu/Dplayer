package com.emilda.dplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.DataClass.SongType
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class sharedViewModel(UserId:String): ViewModel() {
    var userid :String
    var currentSong: SongType? = null
    var songList : ArrayList<SongType?>?=null


    lateinit var valueEventListener: ChildEventListener

     val songListRef: DatabaseReference


    init {
         songListRef= FirebaseDatabase.getInstance().reference.child("/")
         this.userid = UserId

    }
   private  val liveData = FirebaseQueryLiveData(songListRef)

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot> {
        return liveData
    }

}


