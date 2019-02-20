package com.emilda.dplayer.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Repository.FirebaseQueryLiveData
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class AllSongsViewModel :ViewModel(){
    private val AllArtistRef: Query = FirebaseDatabase.getInstance().reference.child("/songs")
    private val liveData: LiveData<DataSnapshot> = FirebaseQueryLiveData(AllArtistRef)

    val options = FirebaseRecyclerOptions.Builder<SongType>().setQuery(AllArtistRef,object: SnapshotParser<SongType> {
        override fun parseSnapshot(snapshot: DataSnapshot): SongType {
            Log.d("FUCk","parse Snap")
            return snapshot.getValue(SongType::class.java)!!
        }
    }).build()
}
