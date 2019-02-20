package com.emilda.dplayer.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

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
        var view: View = v

        fun customBind(listener: songClickListener, current: SongType) {

            view.setOnClickListener {
                listener.onSongClick(current)

            }
        }

    }

}



