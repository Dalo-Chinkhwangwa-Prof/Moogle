package com.example.admin.moogle.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.moogle.R
import com.example.admin.moogle.viewmodel.MoogleViewModel

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        arguments?.get(CURRENT_PAGE)?.let { pageNumber ->
            when (pageNumber) {
                0 -> {
                    ViewModelProviders.of(this).get(MoogleViewModel::class.java)
                    view = layoutInflater.inflate(R.layout.artist_results_fragment_layout, container, false)
                }
                1 -> {
                    view = layoutInflater.inflate(R.layout.music_results_fragment_layout, container, false)
                }
                2 -> {
                    view = layoutInflater.inflate(R.layout.album_results_fragment_layout, container, false)
                }
            }
        }
        return view
    }
}
