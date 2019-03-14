package com.emilda.dplayer.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emilda.dplayer.Adapter.MediaAdapter
import com.emilda.dplayer.DataClass.SongType
import com.emilda.dplayer.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.firebase.database.*


class sharedViewModel(UserId: String, newUser: Boolean) : ViewModel() {
    var userid: String
    var currentSong: SongType? = null
    var isPlaying: Boolean = false
    var newUser: Boolean
    private val AllArtistRef: Query = FirebaseDatabase.getInstance().reference.child("/songs")
    private val users = FirebaseDatabase.getInstance().reference.child("/users")
lateinit var context: Context
    //exoplayer

    var CHANNEL_ID = "DPlayer"
    var NOTIFICATION_ID = 12
    var player: ExoPlayer? = null
    lateinit var playerNotManager: PlayerNotificationManager


    init {
        this.userid = UserId
        this.newUser = newUser

    }


//Firebase


//Exoplayer

    fun initializePlayer(context: Context) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
            this.context =context
            //TODO("set the notification according to the song")
            //TODO("Shuffling nad play all")
            setNotification(currentSong)

        }
    }

    fun loadMedia(songType: SongType?) {

        setNotification(songType)
        val mediaSource =
            buildMediaSource(Uri.parse(songType?.url))
        player?.prepare(mediaSource, true, false)
        Log.d("Working", "Media Loaded")
        player?.playWhenReady = true
        playerNotManager.setPlayer(player)

        Log.d("Working", "When Ready is set")
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = "Dplayer"
        return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
            .createMediaSource(uri)
    }

    fun releasePlayer(context: Context) {
        playerNotManager.setPlayer(null)

        playerNotManager.setPlayer(null)
        player?.release()
        player = null
    }

    fun searchFiredb(queryString: String): FirebaseRecyclerOptions<SongType> {
        val optBuild = AllArtistRef.orderByValue().startAt(queryString)//.endAt("$queryString\\uf8ff")
        val options = FirebaseRecyclerOptions.Builder<SongType>().setQuery(optBuild, object :
            SnapshotParser<SongType> {
            override fun parseSnapshot(snapshot: DataSnapshot): SongType {
                return snapshot.getValue(SongType::class.java)!!
            }
        }).build()
        return options
    }


    fun addToFavorites(songType: SongType) {
        songType.isFav = true
        users.child(userid).child(songType.artistName).setValue(songType)
    }

    fun removeFavSong(song: SongType) {
        users.child(userid).child(song.artistName).removeValue()
    }

    fun checkFav(song: SongType): LiveData<Boolean> {
        val FavRef = FirebaseDatabase.getInstance().reference.child("/users/$userid")
        val data: MutableLiveData<Boolean> = MutableLiveData()
        data.postValue(false)

        FavRef.child(song.artistName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    data.postValue(true)
                } else {
                    Log.d("FUCK", "Not A fav")
                }
            }
        })
        return data
    }

    private fun setNotification(song: SongType?){
        playerNotManager = PlayerNotificationManager.createWithNotificationChannel(
            context,
            CHANNEL_ID,
            R.string.CHANNEL_NAME,
            NOTIFICATION_ID,
            MediaAdapter(context,song)
        )
        playerNotManager.setFastForwardIncrementMs(0)
        playerNotManager.setRewindIncrementMs(0)
        playerNotManager.setStopAction(null)


    }

    fun playList(songs:List<SongType>){
        var queue = ConcatenatingMediaSource()
        for (i in songs){
            val mediaSource =
                buildMediaSource(Uri.parse(i.url))
            queue.addMediaSource(mediaSource)
        }
        player?.prepare(queue)
        player?.playWhenReady =true
    }

}


