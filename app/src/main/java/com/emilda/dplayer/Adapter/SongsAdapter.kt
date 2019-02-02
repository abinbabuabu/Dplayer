package com.emilda.dplayer.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.R
import kotlinx.android.synthetic.main.song_row.view.*

class SongsAdapter(val items: ArrayList<SongType?>, var context: Context?) :
    RecyclerView.Adapter<SongsAdapter.ViewHolder>() {
    var ItemsList :ArrayList<SongType?>
    init {
        this.ItemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.song_row, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songName.text = ItemsList[position]?.songName
        holder.artistName.text = ItemsList[position]?.artistName
        holder.itemView.setOnClickListener {
            Log.d("FUCK",""+ItemsList[position])
            it.findNavController().navigate(R.id.action_songsFragment_to_playerFragment)
        }
    }

    fun updateList(newList:ArrayList<SongType?>){
        ItemsList.clear()
        ItemsList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView = view.songname_tv
        val artistName: TextView = view.artistname_tv
    }
}