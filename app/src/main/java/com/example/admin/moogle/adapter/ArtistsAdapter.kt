package com.example.admin.moogle.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.admin.moogle.R
import com.example.admin.moogle.model.artists.Artist

class ArtistsAdapter(val artists: List<Artist>) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {
    lateinit var applicationContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        applicationContext = parent.context.applicationContext
        return ArtistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.artist_item_view, parent, false))
    }

    override fun getItemCount(): Int = artists.size

    override fun onBindViewHolder(artistViewHolder: ArtistViewHolder, position: Int) {
        artistViewHolder.apply {

            artists[position].let { artist ->
                val drawable: Any? =
                        if (artist.image[0].text.isNullOrEmpty())
                            ContextCompat.getDrawable(applicationContext, R.drawable.artist_icon)
                        else
                            artist.image[0].text
                Glide.with(applicationContext)
                        .applyDefaultRequestOptions(RequestOptions().centerCrop())
                        .load(drawable)
                        .into(artistImageView)
                artistName.text = artist.name ?: applicationContext.getText(R.string.artist_name)
                artistListeners.text = applicationContext.resources.getQuantityString(R.plurals.artist_listeners, Integer.parseInt(artist.listeners
                        ?: 0.toString()), Integer.parseInt(artist.listeners ?: 0.toString()))
                containerView.setOnClickListener { _ ->
                    //TODO: navigate to artist page
                }
            }
        }
    }


    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistImageView: ImageView = itemView.findViewById(R.id.artist_imageview)
        val artistName: TextView = itemView.findViewById(R.id.artist_name_textview)
        val artistListeners: TextView = itemView.findViewById(R.id.artist_listeners_textview)
        val containerView: CardView = itemView.findViewById(R.id.artist_view)
    }
}