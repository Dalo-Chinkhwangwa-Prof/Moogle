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
import kotlinx.android.synthetic.main.album_information_fragment.*

class AlbumInformationFragment : Fragment() {
    companion object {
        const val album_name = "album_name"
        const val album_artist = "album_artist"
        const val cover_image = "cover_image"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.album_information_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel_album_information_button.setOnClickListener { _ ->
            fragmentManager?.popBackStack()
        }
        album_fragment_name_textview.text = arguments?.getString(album_name) ?: resources.getString(R.string.unkown_album_title)
        album_information_artist_textview.text = arguments?.getString(album_artist) ?: resources.getString(R.string.unkown_artist_name)
        activity?.let { mainActivity ->
            val drawable: Any? = if (arguments?.getString(cover_image).isNullOrEmpty())
                ContextCompat.getDrawable(mainActivity.applicationContext, R.drawable.album_icon)
            else
                arguments?.getString(cover_image)

            Glide.with(mainActivity)
                    .setDefaultRequestOptions(RequestOptions().centerCrop())
                    .load(drawable)
                    .into(album_fragment_imageview)
        }
    }
}