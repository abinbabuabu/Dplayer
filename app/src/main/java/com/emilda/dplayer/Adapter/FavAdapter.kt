package com.emilda.dplayer.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.fav_row.view.*

class FavAdapter(options: FirebaseRecyclerOptions<SongType>, context: Context, listener: songClickListener) :
    FirebaseRecyclerAdapter<SongType, FavAdapter.mViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
      return mViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_row, parent, false))
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int, current: SongType){
        holder.customBind(mListener,current)
    }

    private val context: Context = context
    val mListener: songClickListener = listener


    inner class mViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var songView = v.song_layout
        var favbutton = v.options_layout

        fun customBind(listener: songClickListener, current: SongType) {
           songView.setOnClickListener{
               listener.onSongClick(current,it.id)
           }
            favbutton.setOnClickListener {
                listener.onSongClick(current,it.id)
            }
        }
    }

}
