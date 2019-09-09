package com.example.admin.moogle.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.admin.moogle.R
import com.example.admin.moogle.model.tracks.Track
import org.w3c.dom.Text

class MusicAdapter(private val musicList: List<Track>, private val adapterDelegate: MusicAdapterDelegate) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {


    interface MusicAdapterDelegate {
        fun songSelected(song: Track)
    }

    private lateinit var applicationContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        applicationContext = parent.context.applicationContext
        return MusicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.music_item_view, parent, false))
    }

    override fun getItemCount(): Int = musicList.size

    override fun onBindViewHolder(musicViewHolder: MusicViewHolder, position: Int) {
        musicViewHolder.apply {
            musicList[position].let { song ->
                val drawable: Any? =
                        if (song.image[song.image.size - 1]?.text.isNullOrEmpty())
                            ContextCompat.getDrawable(applicationContext, R.drawable.music_icon)
                        else
                            song.image[song.image.size - 1]?.text

                songTitleText.text = song.name ?: applicationContext.resources.getString(R.string.unkown_song_title)
                Glide.with(applicationContext)
                        .setDefaultRequestOptions(RequestOptions().centerCrop())
                        .load(drawable)
                        .into(songArtImageView)

                songArtistText.text = song.artist ?: applicationContext.resources.getString(R.string.unkown_artist_name)
                songContainer.setOnClickListener { _ ->
                    adapterDelegate.songSelected(song)
                }
            }
        }
    }


    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitleText: TextView = itemView.findViewById(R.id.song_name_textview)
        val songArtistText: TextView = itemView.findViewById(R.id.song_artist_name)
        val songArtImageView: ImageView = itemView.findViewById(R.id.song_imageview)
        val songContainer: CardView = itemView.findViewById(R.id.song_view)
    }
}