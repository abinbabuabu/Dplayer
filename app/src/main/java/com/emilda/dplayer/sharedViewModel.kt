package com.emilda.dplayer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.DataClass.SongType
import com.google.firebase.database.*


class sharedViewModel: ViewModel() {
    var number = MutableLiveData<Int>()
    var currentSong: SongType? = null
    lateinit var childEventListener: ChildEventListener
    var dbRef: DatabaseReference
    lateinit var songListRef: DatabaseReference
     var songsList:MutableLiveData<List<SongType>> = MutableLiveData()

    init {

        dbRef = FirebaseDatabase.getInstance().reference

    }


    fun lookForDbChange() {
        songListRef = dbRef.child("/")

        childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("FUCK", "Listener Error")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var song = p0.getValue(SongType::class.java)
                currentSong = song
                var list:ArrayList<SongType> = ArrayList()
                list.add(song!!)
                songsList.postValue(list)
                Log.d("FUCK", song.url)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        songListRef.addChildEventListener(childEventListener)
    }
}


