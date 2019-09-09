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
import kotlinx.android.synthetic.main.artist_information_fragment.*

class ArtistInformationFragment: Fragment(){
    companion object {
        const val artist_name = "artist_name"
        const val artist_image = "artist_image"
        const val artist_listeners = "artist_listeners"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.artist_information_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel_artist_information_button.setOnClickListener { _->
            fragmentManager?.popBackStack()
        }
        artist_fragment_name_textview.text = arguments?.getString(artist_name) ?: resources.getString(R.string.unkown_artist_name)
        activity?.let { mainActivity ->
            val drawable: Any? = if(arguments?.getString(artist_image).isNullOrEmpty())
                ContextCompat.getDrawable(mainActivity.applicationContext, R.drawable.music_icon)
            else
                arguments?.getString(artist_image)

            Glide.with(mainActivity)
                    .setDefaultRequestOptions(RequestOptions().centerCrop())
                    .load(drawable)
                    .into(artist_fragment_imageview)
            artist_information_artist_textview.text = resources.getQuantityString(R.plurals.artist_listeners, Integer.parseInt(arguments?.getString(artist_listeners)), Integer.parseInt(arguments?.getString(artist_listeners)))
        }


    }
}