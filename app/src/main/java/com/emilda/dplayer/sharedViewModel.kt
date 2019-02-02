package com.emilda.dplayer

import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.Adapter.MediaAdapter
import com.emilda.dplayer.DataClass.SongType
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class sharedViewModel(UserId:String): ViewModel() {
    var userid :String
    var currentSong: SongType? = null
    val songListRef: DatabaseReference
    var isPlaying :Boolean = false
    val sampleUrl ="https://firebasestorage.googleapis.com/v0/b/dplayer-7b002.appspot.com/o/Song.mp3?alt=media&token=697845c0-e5f4-45be-9ab7-27f1df471bd1"

  //exoplayer

    var CHANNEL_ID = "DPlayer"
    var NOTIFICATION_ID = 12
    var player: ExoPlayer? = null
    var playerNotManager: PlayerNotificationManager? = null


    init {
         songListRef= FirebaseDatabase.getInstance().reference.child("/")
         this.userid = UserId

    }
   private  val liveData = FirebaseQueryLiveData(songListRef)

//Firebase

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot> {
        return liveData
    }

//Exoplayer

    fun initializePlayer(context:Context) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
            playerNotManager = PlayerNotificationManager.createWithNotificationChannel(
                context,
                CHANNEL_ID,
                R.string.CHANNEL_NAME,
                NOTIFICATION_ID,
                MediaAdapter(context)
            )
            playerNotManager?.setPlayer(player)
            //playerView?.player = player
        }
        val mediaSource =
            buildMediaSource(Uri.parse(sampleUrl))
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

    @RequiresApi(Build.VERSION_CODES.O)
     fun releasePlayer(context:Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
        if (player != null) {
            playerNotManager?.setPlayer(null)
            player?.release()
            player = null
        }
    }
}


