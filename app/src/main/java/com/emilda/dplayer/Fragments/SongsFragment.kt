package com.emilda.dplayer.Fragments


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
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import com.emilda.dplayer.ViewModels.AllSongsViewModel
import com.emilda.dplayer.ViewModels.sharedViewModel
import kotlinx.android.synthetic.main.fragment_songs.*


class SongsFragment : Fragment() {
    private lateinit var myAdapter: SongsAdapter
    private lateinit var sharedVM: sharedViewModel
    private lateinit var fragmentVM:AllSongsViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_songs, container, false)
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)


        //setting Player Contols

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedVM = activity?.run {
            ViewModelProviders.of(this).get(sharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        sharedVM.initializePlayer(context!!)



        //Create Fragment View Model
        //TODO("Make the View model to pass a User Id")
        fragmentVM = ViewModelProviders.of(this).get(AllSongsViewModel::class.java)

        myAdapter = SongsAdapter(fragmentVM.options,context!!,object :songClickListener{
            override fun onSongClick(song: SongType?) {
                sharedVM.loadMedia(song)
            }
        })
        recyclerView.adapter = myAdapter

        player_controls.player = sharedVM.player

    }

    override fun onStart() {
        super.onStart()
        myAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        myAdapter.stopListening()
    }

}





