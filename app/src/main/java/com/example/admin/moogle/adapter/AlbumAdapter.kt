package com.example.admin.moogle.adapter

import android.content.Context
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
import com.example.admin.moogle.model.albums.Album
import com.example.admin.moogle.util.rx.ErrorLog

class AlbumAdapter(private val albumList: List<Album>, private val albumAdapterDelegate: AlbumAdapterDelegate) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    interface AlbumAdapterDelegate{
        fun albumSelected(album: Album)
    }
    private lateinit var applicationContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        applicationContext = parent.context.applicationContext
        return AlbumViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_item_view, parent, false))
    }

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(albumViewHolder: AlbumViewHolder, position: Int) {
        albumList[position].let { album ->

            albumViewHolder.apply {
                val drawable: Any? = if (album.image[album.image.size-1].text.isNullOrEmpty())
                    applicationContext.getDrawable(R.drawable.album_icon)
                else
                    album.image[album.image.size-1].text
                Glide.with(applicationContext)
                        .setDefaultRequestOptions(RequestOptions().centerCrop())
                        .load(drawable)
                        .into(albumArt)
                albumArtistText.text = album.artist ?: applicationContext.getString(R.string.unkown_artist_name)
                albumTitleText.text = album.name ?: applicationContext.getString(R.string.unkown_album_title)
                albumContainer.setOnClickListener {
                    albumAdapterDelegate.albumSelected(album)
                }
            }
        }
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumTitleText: TextView = itemView.findViewById(R.id.album_title_textview)
        val albumArtistText: TextView = itemView.findViewById(R.id.album_artist_textview)
        val albumArt: ImageView = itemView.findViewById(R.id.album_imageview)
        val albumContainer: CardView = itemView.findViewById(R.id.album_view)
    }
}