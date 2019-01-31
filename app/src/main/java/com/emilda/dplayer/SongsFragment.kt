package com.emilda.dplayer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.Adapter.SongsAdapter
import com.emilda.dplayer.DataClass.SongType

class SongsFragment : Fragment() {
    var songlist: ArrayList<SongType?> = ArrayList()
    private var sharedViewModel:sharedViewModel?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_songs, container, false)
        var rv = view.findViewById(R.id.rv_song_list) as RecyclerView

        //shared View MOdel Variable

        val model = activity?.run {
            ViewModelProviders.of(this).get(com.emilda.dplayer.sharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        model.number.postValue(101)
        rv.layoutManager = LinearLayoutManager(context)
        songlist = sampleSongs()
        rv.adapter = SongsAdapter(songlist, context)
        return view
    }

    fun sampleSongs(): ArrayList<SongType?> {
        songlist.add(sharedViewModel?.currentSong)
        return songlist
    }

}
