package com.emilda.dplayer.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Repository.FirebaseQueryLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class AllSongsViewModel :ViewModel(){

     private val AllSongsRef= FirebaseDatabase.getInstance().reference.child("/songs")
     private val liveData:LiveData<DataSnapshot> = FirebaseQueryLiveData(AllSongsRef)

  fun getAllSongsList():LiveData<List<SongType?>> {
      return Transformations.map(liveData){
               it.children.map { child ->
                   child.getValue(SongType::class.java)
               }
          }
  }

    fun sample(){
        FirebaseRecyclerOptions<>
    }


}
