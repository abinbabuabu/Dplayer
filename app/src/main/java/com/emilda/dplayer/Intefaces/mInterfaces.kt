package com.emilda.dplayer.Intefaces

import com.emilda.dplayer.DataClass.SongType
import com.google.android.exoplayer2.ExoPlayer

interface songClickListener {
    fun onSongClick(song: SongType, id: Int)
}

interface setPlayer{
    fun setPlayer(player: ExoPlayer)
}