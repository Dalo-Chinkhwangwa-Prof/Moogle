package com.example.admin.moogle.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.admin.moogle.R
import kotlinx.android.synthetic.main.song_information_framgment.*

class SongInformationFragment: Fragment(){
    companion object {
        const val song_name = "song_name"
        const val song_artist = "song_artist"
        const val song_image = "song_image"
        const val song_listeners = "song_listeners"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.song_information_framgment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel_song_information_button.setOnClickListener { _->
            fragmentManager?.popBackStack()
        }
        song_fragment_name_textview.text = arguments?.getString(song_name) ?: resources.getString(R.string.unkown_song_title)
        song_information_artist_textview.text = arguments?.getString(song_artist) ?: resources.getString(R.string.unkown_artist_name)
        activity?.let { mainActivity ->
            val drawable: Any? = if(arguments?.getString(song_image).isNullOrEmpty())
                                    ContextCompat.getDrawable(mainActivity.applicationContext, R.drawable.music_icon)
                                else
                                    arguments?.getString(song_image)

            Glide.with(mainActivity)
                    .setDefaultRequestOptions(RequestOptions().centerCrop())
                    .load(drawable)
                    .into(song_fragment_imageview)
            song_information_listeners_textview.text = resources.getQuantityString(R.plurals.artist_listeners, Integer.parseInt(arguments?.getString(song_listeners)), Integer.parseInt(arguments?.getString(song_listeners)))
        }


    }
}