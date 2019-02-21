package com.emilda.dplayer.Fragments


import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.emilda.dplayer.R
import com.emilda.dplayer.ViewModels.sharedViewModel
import com.google.android.exoplayer2.ui.PlayerControlView


class PlayerFragment : Fragment() {
    var pause_or_play = true
    lateinit var model: sharedViewModel

    lateinit var player: PlayerControlView


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.player_view, container, false)
        player = view?.findViewById(R.id.player_exo) as PlayerControlView
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(sharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        player.player = model.player



    }

}


