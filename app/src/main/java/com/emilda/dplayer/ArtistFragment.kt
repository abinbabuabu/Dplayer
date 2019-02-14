package com.emilda.dplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.Adapter.RvAdapter
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.ViewModels.ArtistViewModel


class ArtistFragment : Fragment() {
    private lateinit var mrecycler:RecyclerView
    lateinit var  adapter:RvAdapter
    companion object {
        fun newInstance() = ArtistFragment()
    }

    private lateinit var viewModel: ArtistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.artist_fragment, container, false)
        mrecycler = view?.findViewById(R.id.artists_rv) as RecyclerView
        mrecycler.layoutManager = GridLayoutManager(context,2)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArtistViewModel::class.java)
        Log.d("FUCk","Adapter Created")
        adapter = RvAdapter(viewModel.options,context!!,object:songClickListener{
            override fun onSongClick(song: SongType?) {
                Toast.makeText(context,"Artist Clicked",Toast.LENGTH_LONG).show()
            }
        })
        mrecycler.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
