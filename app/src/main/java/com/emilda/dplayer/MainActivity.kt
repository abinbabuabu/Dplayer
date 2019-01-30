package com.emilda.dplayer


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.emilda.dplayer.DataClass.Song
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var firedb: FirebaseDatabase
    private lateinit var songRef: DatabaseReference
    private lateinit var childEventListener: ChildEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firedb = FirebaseDatabase.getInstance()
        songRef = firedb.reference.child("/")

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
                val song = p0.getValue(Song::class.java)
                Log.d("FUCK", song?.url)

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        }
        songRef.addChildEventListener(childEventListener)
    }
}
