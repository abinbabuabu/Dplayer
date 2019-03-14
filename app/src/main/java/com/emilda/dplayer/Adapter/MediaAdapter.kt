package com.emilda.dplayer.Adapter

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.detailsActivity
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MediaAdapter(context: Context?,songType: SongType?) : PlayerNotificationManager.MediaDescriptionAdapter {
    var context: Context? = null
    var song :SongType?=null

    init {
        this.context = context
        this.song = songType
    }

    override fun createCurrentContentIntent(player: Player?): PendingIntent? {
        var intent = Intent(context, detailsActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun getCurrentContentText(player: Player?): String? {
        Log.d("Player","${player?.currentWindowIndex}")
       return song?.artistName
    }

    override fun getCurrentContentTitle(player: Player?): String {
       return song?.songName ?:"Music"
    }

    override fun getCurrentLargeIcon(player: Player?, callback: PlayerNotificationManager.BitmapCallback?): Bitmap? {
        return null
    }
}