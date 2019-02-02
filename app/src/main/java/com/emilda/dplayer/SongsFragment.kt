package com.emilda.dplayer


import android.os.Bundle
import android.util.Log
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
        myAdapter = SongsAdapter(context)
        rv.adapter = myAdapter



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val liveData = sharedViewModel?.getDataSnapshotLiveData()
        liveData?.observe(this, Observer { dataSnapshot ->
            if (dataSnapshot != null) {
                //val ticker = dataSnapshot.child()
                val price = dataSnapshot.child("songs").getValue(SongType::class.java)
                Log.d("FUCK",price?.url.toString()+price?.artistName.toString())
                songlist.add(price)
                myAdapter.updateList(songlist)
            }
        })

    }
}
