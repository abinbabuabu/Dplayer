package com.emilda.dplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.DataClass.SongType
import com.google.firebase.database.*


class sharedViewModel(UserId:String): ViewModel() {
    var userid :String
    var currentSong: SongType? = null



    lateinit var valueEventListener: ChildEventListener

    var dbRef: DatabaseReference
    lateinit var songListRef: DatabaseReference

     var songsList: MutableLiveData<ArrayList<SongType?>> = MutableLiveData()

    init {
        dbRef = FirebaseDatabase.getInstance().reference
         this.userid = UserId

    }


    fun getSongList(): MutableLiveData<ArrayList<SongType?>> {
        songListRef = dbRef.child("/")

        valueEventListener = object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0:DataSnapshot,p1:String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        songListRef.addChildEventListener(valueEventListener)

        return songsList
    }
}


