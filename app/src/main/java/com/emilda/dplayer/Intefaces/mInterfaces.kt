package com.emilda.dplayer.Intefaces

import com.emilda.dplayer.DataClass.SongType

interface songClickListener {
    fun onSongClick(song: SongType, id: Int)
}

interface checkFav{
    fun checkFav(song: SongType)
}