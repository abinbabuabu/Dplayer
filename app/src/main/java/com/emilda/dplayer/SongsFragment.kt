package com.emilda.dplayer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.Adapter.SongsAdapter
import com.emilda.dplayer.DataClass.SongType

class SongsFragment : Fragment() {
    lateinit var  myAdapter : SongsAdapter
    var songlist: ArrayList<SongType?> = ArrayList()
    private var sharedViewModel:sharedViewModel?=null
    private lateinit var rv: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_songs, container, false)
        rv = view.findViewById(R.id.rv_song_list) as RecyclerView

        //shared View MOdel Variable

        sharedViewModel= activity?.run {
            ViewModelProviders.of(this).get(com.emilda.dplayer.sharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        rv.layoutManager = LinearLayoutManager(context)
        myAdapter = SongsAdapter(sampleSongs("First"), context)
        rv.adapter = myAdapter


        songlist = sampleSongs("second")
        myAdapter.notifyDataSetChanged()

        return view
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel?.getSongList()
        sharedViewModel?.songsList?.observe(this, Observer { list ->
            songlist = list
        })
        myAdapter.notifyDataSetChanged()

    }
    fun sampleSongs(artistName:String): ArrayList<SongType?> {
        var sample = SongType()
        sample.artistName = artistName
        sample.url = "sample"
        sample.songName = "sample"
        songlist.add(sample)
        return songlist
    }

}
