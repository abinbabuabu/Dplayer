package com.emilda.dplayer.Adapter

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.emilda.dplayer.MainActivity
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MediaAdapter(context: Context?) : PlayerNotificationManager.MediaDescriptionAdapter {
    var context: Context? = null

    init {
        this.context = context
    }

    override fun createCurrentContentIntent(player: Player?): PendingIntent? {
        var intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun getCurrentContentText(player: Player?): String? {
       return "sample"
    }

    override fun getCurrentContentTitle(player: Player?): String {
       return "sample"
    }

    override fun getCurrentLargeIcon(player: Player?, callback: PlayerNotificationManager.BitmapCallback?): Bitmap? {
        return null
    }
}