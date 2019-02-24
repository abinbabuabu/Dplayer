package com.emilda.dplayer.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.song_row.view.*

class SongsAdapter(options: FirebaseRecyclerOptions<SongType>, context: Context, listener: songClickListener) :
    FirebaseRecyclerAdapter<SongType, SongsAdapter.mViewHolder>(options) {
    private val context: Context = context
    val mlistener: songClickListener = listener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        Log.d("FUCk", "View Holder")
        return mViewHolder(LayoutInflater.from(context).inflate(R.layout.song_row, parent, false))
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int, current: SongType) {
        holder.customBind(mlistener, current)

    }

    inner class mViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view = v.song_layout
        var optionsButton = v.options_menu_song
        var optionslayout = v.options_layout

        fun customBind(listener: songClickListener, current: SongType) {

            view.setOnClickListener {
                listener.onSongClick(current)
            }

            optionslayout.setOnClickListener{
                Toast.makeText(context,"options Clicked",Toast.LENGTH_LONG).show()
                val popupMenu = PopupMenu(context,optionsButton)
                popupMenu.inflate(R.menu.details_fav_song)
                popupMenu.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                        R.id.add_to_now_playing -> Toast.makeText(context,"Added to now Playing",Toast.LENGTH_LONG).show()
                        R.id.remove_song-> Toast.makeText(context,"Song Removed",Toast.LENGTH_LONG).show()
                    }
                    true
                }
                popupMenu.show()
            }
        }

    }

}



