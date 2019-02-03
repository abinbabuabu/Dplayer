package com.emilda.dplayer.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.Intefaces.songClickListener
import com.emilda.dplayer.R
import kotlinx.android.synthetic.main.song_row.view.*

class SongsAdapter(listener :songClickListener) : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {
    var ItemsList :ArrayList<SongType?>? = null
    val mlistener:songClickListener



    init {
        this.mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_row, parent, false))
    }

    override fun getItemCount(): Int {
        return if(ItemsList!=null) ItemsList!!.size
        else -1

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customBind(ItemsList?.get(position),position,mlistener)
    }



    internal fun updateList(newList:ArrayList<SongType?>?){
        ItemsList = newList
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var view:View = view
        val songName: TextView = view.songname_tv
        val artistName: TextView = view.artistname_tv
        val songLayout:LinearLayout = view.song_layout

        fun customBind(Song:SongType?,position: Int , listener: songClickListener){
            var rowSelected:Int = -1
            songName.text = Song?.songName
            artistName.text = Song?.artistName
            view.setOnClickListener {
                    listener.onSongClick(Song)
                rowSelected =position
                notifyDataSetChanged()
            }
            if(rowSelected == position){
                songLayout.setBackgroundColor(Color.RED)
            }

        }
    }

}