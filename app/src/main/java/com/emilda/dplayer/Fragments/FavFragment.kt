package com.emilda.dplayer.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.emilda.dplayer.Adapter.FavAdapter
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import com.emilda.dplayer.ViewModels.FavoritesViewModel
import com.emilda.dplayer.ViewModels.sharedViewModel
import kotlinx.android.synthetic.main.fragment_fav.*

class FavFragment : Fragment() {
    private lateinit var fragmentVM: FavoritesViewModel
    private lateinit var adapter: FavAdapter
    private lateinit var sharedVM: sharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fav_recycler.layoutManager = LinearLayoutManager(context)
        //viewModels
        fragmentVM = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        sharedVM = activity?.run {
            ViewModelProviders.of(this).get(sharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        sharedVM.initializePlayer(context!!)




        adapter= FavAdapter(fragmentVM.options, context!!, object : songClickListener {
            override fun onSongClick(song: SongType, int: Int) {
               if (int == R.id.song_layout)
                   sharedVM.loadMedia(song)
                else
                  sharedVM.addToFavorites(song)

            }
        })

        fav_recycler.adapter = adapter
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
