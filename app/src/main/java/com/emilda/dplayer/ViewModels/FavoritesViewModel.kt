package com.emilda.dplayer.ViewModels

import androidx.lifecycle.ViewModel
import com.emilda.dplayer.DataClass.SongType
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class FavoritesViewModel : ViewModel() {
    private val AllArtistRef: Query = FirebaseDatabase.getInstance().reference.child("/songs")

    val options = FirebaseRecyclerOptions.Builder<SongType>().setQuery(AllArtistRef, object : SnapshotParser<SongType> {
        override fun parseSnapshot(snapshot: DataSnapshot): SongType {
            return snapshot.getValue(SongType::class.java)!!
        }
    }).build()
}