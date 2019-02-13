package com.emilda.dplayer


import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.emilda.dplayer.ViewModels.sharedViewModel
import kotlinx.android.synthetic.main.player_view.*


class PlayerFragment : Fragment() {
    var pause_or_play =true


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_view, container, false)
}


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //View Model

        player_control.showTimeoutMs=0
        player_control.visibility = View.VISIBLE



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model = activity?.run {
            ViewModelProviders.of(this).get(sharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        player_control.player = model.player!!

    }
}


