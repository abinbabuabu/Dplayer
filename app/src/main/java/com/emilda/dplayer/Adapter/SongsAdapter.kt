package com.emilda.dplayer.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import kotlinx.android.synthetic.main.song_row.view.*

class SongsAdapter(listener :songClickListener) : ListAdapter<SongType,SongsAdapter.ViewHolder>(SongType.diffCallback) {
    var ItemsList :ArrayList<SongType?>? = null
    val mlistener:songClickListener = listener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customBind(getItem(position),position,mlistener)
    }



    internal fun updateList(newList:ArrayList<SongType?>?){
        ItemsList = newList
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var view:View = view
        val songName: TextView = view.songname_tv
        val artistName: TextView = view.artistname_tv
        val songLayout:ConstraintLayout = view.song_layout

        fun customBind(Song:SongType?,position: Int , listener: songClickListener){
            var rowSelected:Int = -1
            songName.text = Song?.songName
            artistName.text = Song?.artistName
            view.setOnClickListener {
                    listener.onSongClick(Song)
                rowSelected =position

                //notifyDataSetChanged()
            }
            if(rowSelected == position){
                songLayout.setBackgroundColor(Color.RED)
            }

        }
    }


}