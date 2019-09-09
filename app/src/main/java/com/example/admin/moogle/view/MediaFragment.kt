package com.example.admin.moogle.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.moogle.R
import com.example.admin.moogle.adapter.AlbumAdapter
import com.example.admin.moogle.adapter.ArtistsAdapter
import com.example.admin.moogle.adapter.MusicAdapter
import com.example.admin.moogle.model.albums.Album
import com.example.admin.moogle.model.albums.Albums
import com.example.admin.moogle.model.artists.Artist
import com.example.admin.moogle.model.artists.Artists
import com.example.admin.moogle.model.tracks.Track
import com.example.admin.moogle.model.tracks.Tracks
import com.example.admin.moogle.util.rx.ErrorLog
import com.example.admin.moogle.viewmodel.MoogleViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.album_results_fragment_layout.view.*
import kotlinx.android.synthetic.main.artist_results_fragment_layout.view.*
import kotlinx.android.synthetic.main.music_results_fragment_layout.view.*

class MediaFragment : Fragment(),
        MusicAdapter.MusicAdapterDelegate,
        AlbumAdapter.AlbumAdapterDelegate,
        ArtistsAdapter.ArtistAdapterDelegate{

    companion object {
        const val CURRENT_PAGE = "current_page"
        fun newInstance(fragmentNumber: Int): Fragment {
            val fragmentInstance: Fragment = MediaFragment()
            val bundle = Bundle()
            bundle.putInt(CURRENT_PAGE, fragmentNumber)
            fragmentInstance.arguments = bundle
            return fragmentInstance
        }
    }

    interface MediaFragmentDelegate{
        fun loadMoreSongs()
        fun loadMoreAlbums()
        fun loadMoreArtists()
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var viewModel: MoogleViewModel? = null
    private lateinit var rootView: View
    private lateinit var fragmentDelegate: MediaFragmentDelegate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.let { mainActivity ->
            viewModel = ViewModelProviders.of(mainActivity).get(MoogleViewModel::class.java)
            fragmentDelegate = (mainActivity as MainActivity)
        }
        compositeDisposable.addAll(
                viewModel?.getArtists()
                        ?.subscribe({ artistResults ->
                            updateArtistList(artistResults)
                        }, { throwable ->
                            ErrorLog.logError(throwable)
                        }),
                viewModel?.getSongs()
                        ?.subscribe({ songResults ->
                            updateSongList(songResults)
                        }, { throwable ->
                            ErrorLog.logError(throwable)
                        }),
                viewModel?.getAlbums()
                        ?.subscribe({ albumResults ->
                            updateAlbumList(albumResults)
                        }, { throwable ->
                            ErrorLog.logError(throwable)
                        })

        )

        when (arguments?.getInt(CURRENT_PAGE)) {
            0 -> {
                rootView = layoutInflater.inflate(R.layout.artist_results_fragment_layout, container, false)
            }
            1 -> rootView = layoutInflater.inflate(R.layout.music_results_fragment_layout, container, false)
            2 -> rootView = layoutInflater.inflate(R.layout.album_results_fragment_layout, container, false)
        }
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }

    private fun updateArtistList(results: Artists) {
        rootView.artist_results_recyclerview?.let { recyclerView ->
            val list = results.results.artistmatches.artist
            if(Integer.parseInt(results.results.opensearchQuery.startPage) == 1){
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = ArtistsAdapter(list, this@MediaFragment)
                recyclerView.adapter.notifyDataSetChanged()

//                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                        super.onScrolled(recyclerView, dx, dy)
//                        if(list.size <= ((recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 20))
//                            //fragmentDelegate.loadMoreArtists()
//                    }
//                })
            }
            else{
                (recyclerView.adapter as ArtistsAdapter).artists.addAll(list)
                recyclerView.adapter.notifyDataSetChanged()
            }

        }
    }

    private fun updateSongList(results: Tracks) {
        rootView.music_results_recyclerview?.let { recyclerView ->
            val list = results.results.trackmatches.track
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = MusicAdapter(list, this@MediaFragment)
            recyclerView.adapter.notifyDataSetChanged()

//            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if(list.size <= ((recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 20))
//                        fragmentDelegate.loadMoreSongs()
//                }
//            })
        }
    }

    private fun updateAlbumList(results: Albums) {
        rootView.album_results_recyclerview?.let { recyclerView ->
            val list = results.results.albummatches.album
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = AlbumAdapter(list, this@MediaFragment)
            recyclerView.adapter.notifyDataSetChanged()

//            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if(list.size <= ((recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() + 20))
//                        fragmentDelegate.loadMoreAlbums()
//                }
//            })
        }
    }
    override fun songSelected(song: Track) {
        activity?.let { mainActivity ->
            (mainActivity as MainActivity).openSongInformationFragmet(song)
        }
    }

    override fun albumSelected(album: Album) {
        activity?.let { mainActivity ->
            (mainActivity as MainActivity).openAlbumInformationFragment(album)
        }
    }

    override fun artistSelected(artist: Artist) {
        activity?.let { mainActivity ->
            (mainActivity as MainActivity).openArtistInformation(artist)
        }
    }
}
