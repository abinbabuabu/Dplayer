package com.emilda.dplayer


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.Adapter.SongsAdapter
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.UtilsApplication.Companion.songRefAPP

class SongsFragment : Fragment() {
    var songlist: ArrayList<SongType?> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_songs, container, false)
        var rv = view.findViewById(R.id.rv_song_list) as RecyclerView
        rv.layoutManager = LinearLayoutManager(context)
        songlist = sampleSongs()
        rv.adapter = SongsAdapter(songlist, context)
        return view
    }

    fun sampleSongs(): ArrayList<SongType?> {
        songlist.add(songRefAPP)
        songlist.add(songRefAPP)
        songlist.add(songRefAPP)
        songlist.add(songRefAPP)
        songlist.add(songRefAPP)
        var song :SongType = SongType()
        song.songName = "sample"
        song.artistName="sample"
        song.url="sample"
        songlist.add(song)

        Log.d("Songs", "Is Song List Null" + songlist.isEmpty())
        return songlist
    }
}
