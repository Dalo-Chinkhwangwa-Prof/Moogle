package com.example.admin.moogle.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.moogle.R
import com.example.admin.moogle.adapter.ArtistsAdapter
import com.example.admin.moogle.model.artists.Artist
import com.example.admin.moogle.viewmodel.MoogleViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.artist_results_fragment_layout.view.*

class MediaFragment : Fragment() {
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

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var viewModel: MoogleViewModel? = null
    private lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.let { mainActivity ->
            viewModel = ViewModelProviders.of(mainActivity).get(MoogleViewModel::class.java)
        }
        compositeDisposable.addAll(
                viewModel?.getArtists()
                        ?.subscribe { artistResults ->
                           updateArtistList(artistResults.results.artistmatches.artist)
                        },
                viewModel?.getSongs()
                        ?.subscribe{
                            Log.d("TAG_X", "Get songs in FRAG")
                        },
                viewModel?.getAlbums()
                        ?.subscribe {
                            Log.d("TAG_X", "Get albums in FRAG")
                        }

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

    private fun updateArtistList(list: List<Artist>){
        Log.d("TAG_X", "Starting recyclerView")
        rootView.artist_results_recyclerview?.let{ recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = ArtistsAdapter(list)
            recyclerView.adapter.notifyDataSetChanged()
        }
    }
}
