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
import com.facebook.shimmer.ShimmerFrameLayout
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.fav_row.view.*

class FavAdapter(
    options: FirebaseRecyclerOptions<SongType>,
    context: Context,
    listener: songClickListener,
    shimmer: ShimmerFrameLayout
) :
    FirebaseRecyclerAdapter<SongType, FavAdapter.mViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        return mViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_row, parent, false))
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int, current: SongType) {
        shimmer.stopShimmerAnimation()
        shimmer.visibility = View.GONE
        holder.artistName.text = current.artistName
        holder.songName.text = current.songName
        holder.customBind(mListener, current)

    }

    private val context: Context = context
    private val mListener: songClickListener = listener
    private val shimmer = shimmer


    inner class mViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var songView = v.song_layout
        private var favbutton = v.add_to_fav
        var artistName = v.artistname_tv
        var songName = v.songname_tv

        fun customBind(listener: songClickListener, current: SongType) {
            Log.d("FUCk", "isfav${current.isFav}")

            songView.setOnClickListener {
                listener.onSongClick(current, it.id)
            }
            favbutton.setOnClickListener {
                listener.onSongClick(current, it.id)
            }
        }
    }

}
