package com.emilda.dplayer.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.artist_row.view.*

class RvAdapter(options: FirebaseRecyclerOptions<SongType>, context: Context,listener:songClickListener) :
    FirebaseRecyclerAdapter<SongType, RvAdapter.mViewHolder>(options) {
    private val context: Context = context
    val mlistener:songClickListener = listener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        Log.d("FUCk", "View Holder")
        return mViewHolder(LayoutInflater.from(context).inflate(R.layout.artist_row, parent, false))
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int, current: SongType) {
        holder.customBind(mlistener,current)

    }

    inner class mViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view: View = v
        var artistImage: ImageView = view.artist_image

        fun customBind(listener: songClickListener,current: SongType){

         view.setOnClickListener {
             listener.onSongClick(current)

         }
        }

    }

}



