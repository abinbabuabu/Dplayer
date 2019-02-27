package com.emilda.dplayer.DataClass

import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.database.PropertyName

//data class SongType(var artistName: String,var songName: String ,var url:String){


class SongType(
    @set:PropertyName("artistName")
    @get:PropertyName("artistName")
    var artistName: String = "",
    @set:PropertyName("songName")
    @get:PropertyName("songName")
    var songName: String = "",
    @set:PropertyName("url")
    @get:PropertyName("url")
    var url: String = "",
    @set:PropertyName("isFav")
    @get:PropertyName("isFav")
    var isFav: Boolean = false
    )
{
    constructor() : this("", "", "")

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<SongType>() {
            override fun areItemsTheSame(
                oldItem: SongType,
                newItem: SongType
            ): Boolean =
                oldItem.songName == newItem.songName

            override fun areContentsTheSame(oldItem: SongType, newItem: SongType) =
                oldItem.url == newItem.url && oldItem.url == newItem.url

//            override fun getChangePayload(oldItem: MediaItemData, newItem: MediaItemData) =
        }
    }

}
