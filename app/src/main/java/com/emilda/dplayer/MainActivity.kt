package com.emilda.dplayer


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.UtilsApplication.Companion.dbRefAPP
import com.emilda.dplayer.UtilsApplication.Companion.songRefAPP
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    private lateinit var songRef: DatabaseReference
    private lateinit var childEventListener: ChildEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songRef = dbRefAPP.child("/")

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
                songRefAPP = song
                song = songRefAPP
                Log.d("FUCK", song?.url)

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        }
        songRef.addChildEventListener(childEventListener)
    }
}
