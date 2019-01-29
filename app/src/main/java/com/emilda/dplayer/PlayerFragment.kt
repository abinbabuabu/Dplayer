package com.emilda.dplayer


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.emilda.dplayer.Adapter.MediaAdapter
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.fragment_player.*


class PlayerFragment : Fragment() {
    var CHANNEL_ID ="DPlayer"
    var NOTIFICATION_ID = 12

    var player: ExoPlayer? = null
    var playerNotManager:PlayerNotificationManager?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerNotManager = PlayerNotificationManager(context,CHANNEL_ID,NOTIFICATION_ID,MediaAdapter(context))
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }


    override fun onDestroy() {
        releasePlayer()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            releaseNotificationChannel()
        }
        super.onDestroy()
    }

    private fun initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())

            playerNotManager?.setPlayer(player)
            playerView?.player = player


        }
        val mediaSource =
            buildMediaSource(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"))
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


    private fun releasePlayer() {
        if (player != null ) {
            playerNotManager?.setPlayer(null)
            player?.release()
            player = null
        }
    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun releaseNotificationChannel(){
        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
    }

}
