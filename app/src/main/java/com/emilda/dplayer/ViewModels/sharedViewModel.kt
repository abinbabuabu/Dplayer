package com.emilda.dplayer.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.Adapter.MediaAdapter
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


class sharedViewModel(UserId:String): ViewModel() {
    var userid :String
    var currentSong: SongType? = null
    var isPlaying :Boolean = false

  //exoplayer

    var CHANNEL_ID = "DPlayer"
    var NOTIFICATION_ID = 12
    var player: ExoPlayer? = null
    lateinit var playerNotManager: PlayerNotificationManager


    init {
         this.userid = UserId

    }


//Firebase


//Exoplayer

    fun initializePlayer(context:Context) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
            playerNotManager = PlayerNotificationManager.createWithNotificationChannel(
                context,
                CHANNEL_ID,
                R.string.CHANNEL_NAME,
                NOTIFICATION_ID,
                MediaAdapter(context,currentSong)
            )
         playerNotManager.setFastForwardIncrementMs(0)
            playerNotManager.setRewindIncrementMs(0)
            playerNotManager.setStopAction(null)

            playerNotManager.setPlayer(player)
            Log.d("user",userid)

        }
    }
    fun loadMedia(songType: SongType?){
        val mediaSource =
            buildMediaSource(Uri.parse(songType?.url))
        player?.prepare(mediaSource, true, false)
        Log.d("Working", "Media Loaded")
        player?.playWhenReady = true
        Log.d("Working", "When Ready is set")
    }
    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = "Dplayer"
        return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
            .createMediaSource(uri)
    }

     fun releasePlayer(context:Context) {
        //val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
       // notificationManager.deleteNotificationChannel(CHANNEL_ID)
        playerNotManager.setPlayer(null)

         playerNotManager.setPlayer(null)
            player?.release()
            player = null
    }
}


