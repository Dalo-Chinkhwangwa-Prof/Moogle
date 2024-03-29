package com.example.admin.moogle.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.admin.moogle.R
import com.example.admin.moogle.adapter.ResultFragmentsPagerAdapter
import com.example.admin.moogle.model.albums.Album
import com.example.admin.moogle.model.artists.Artist
import com.example.admin.moogle.model.tracks.Track
import com.example.admin.moogle.viewmodel.MoogleViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener,
        MediaFragment.MediaFragmentDelegate {


    companion object {
        const val fragmentCount: Int = 3
    }

    private lateinit var viewModel: MoogleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MoogleViewModel::class.java)

        setUpViewPager()
        setUpKeystrokeListener()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun setUpKeystrokeListener() {
        with(search_edittext as EditText){
            addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    //Do nothing
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Do nothing
                }
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(!text.isNullOrEmpty())
                        viewModel.searchMediaText(text.toString(), 1)
                }
            })
        }
    }

    private fun setUpViewPager() {
        results_view_pager.adapter = ResultFragmentsPagerAdapter(supportFragmentManager)
        results_view_pager.addOnPageChangeListener(this@MainActivity)

        bottom_navigatonview.setOnNavigationItemSelectedListener({ viewItem ->
            when(viewItem.itemId){
                (R.id.artist_item) -> results_view_pager.currentItem = 0
                (R.id.music_item) -> results_view_pager.currentItem = 1
                (R.id.album_item) -> results_view_pager.currentItem = 2
            }
            true
        })
    }

    override fun onPageScrollStateChanged(state: Int) {
        //Do nothing
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //Do nothing
    }

    override fun onPageSelected(position: Int) {
        bottom_navigatonview.menu.getItem(position).isChecked = true
    }

    override fun loadMoreSongs() {

    }

    override fun loadMoreAlbums() {
    }

    override fun loadMoreArtists() {
//        viewModel.loadMoreArtists()
    }

    fun openSongInformationFragmet(song: Track){
        val songInformationFragment = SongInformationFragment()
        val bundle = Bundle()
        bundle.putString(SongInformationFragment.song_name, song.name)
        bundle.putString(SongInformationFragment.song_image, song.image[song.image.size-1].text ?: "")
        bundle.putString(SongInformationFragment.song_artist, song.artist)
        bundle.putString(SongInformationFragment.song_listeners, song.listeners)
        songInformationFragment.arguments = bundle
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, songInformationFragment)
                .addToBackStack(songInformationFragment.tag)
                .commit()
    }

    fun openAlbumInformationFragment(album: Album){
        val albumInformationFragment = AlbumInformationFragment()
        val bundle = Bundle()
        bundle.putString(AlbumInformationFragment.album_name, album.name)
        bundle.putString(AlbumInformationFragment.cover_image, album.image[album.image.size-1].text ?: "")
        bundle.putString(AlbumInformationFragment.album_artist, album.artist)
        albumInformationFragment.arguments = bundle
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, albumInformationFragment)
                .addToBackStack(albumInformationFragment.tag)
                .commit()
    }
    fun openArtistInformation(artist: Artist){
        val artistInformationFragment = ArtistInformationFragment()
        val bundle = Bundle()
        bundle.putString(ArtistInformationFragment.artist_name, artist.name)
        bundle.putString(ArtistInformationFragment.artist_image, artist.image[artist.image.size-1].text ?: "")
        bundle.putString(ArtistInformationFragment.artist_listeners, artist.listeners)
        artistInformationFragment.arguments = bundle
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, artistInformationFragment)
                .addToBackStack(artistInformationFragment.tag)
                .commit()

    }
}

