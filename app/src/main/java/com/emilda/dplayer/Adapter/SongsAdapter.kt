package com.emilda.dplayer.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.song_row.view.*

class SongsAdapter(options: FirebaseRecyclerOptions<SongType>,
                   context: Context,
                   listener: songClickListener,
                   shimmer: ShimmerFrameLayout) :
    FirebaseRecyclerAdapter<SongType, SongsAdapter.mViewHolder>(options) {


    private val context: Context = context
    private val mlistener: songClickListener = listener
    private val shimmer = shimmer


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        return mViewHolder(LayoutInflater.from(context).inflate(R.layout.song_row, parent, false))
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int, current: SongType) {
        shimmer.stopShimmerAnimation()
        shimmer.visibility = View.GONE
        holder.artistName.text = current.artistName
        holder.songName.text = current.songName
        holder.customBind(mlistener, current)

    }

    inner class mViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view = v.song_layout
        var optionsButton = v.options_menu_song
        var optionslayout = v.options_menu_song

        var artistName = v.artistname_tv
        var songName = v.songname_tv

        fun customBind(listener: songClickListener, current: SongType) {
            view.setOnClickListener {
                listener.onSongClick(current, it.id)
            }

            optionslayout.setOnClickListener {
                Toast.makeText(context, "options Clicked", Toast.LENGTH_LONG).show()
                val popupMenu = PopupMenu(context, optionsButton)
                popupMenu.inflate(R.menu.details_fav_song)
                popupMenu.setOnMenuItemClickListener { item ->
                    listener.onSongClick(current, item.itemId)
                    true
                }
                popupMenu.show()
            }
        }

    }

}



