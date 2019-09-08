package com.example.admin.moogle.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.example.admin.moogle.R
import com.example.admin.moogle.adapter.ResultFragmentsPagerAdapter
import com.example.admin.moogle.viewmodel.MoogleViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    companion object {
        const val fragmentCount: Int = 3
    }

    private lateinit var viewModel: MoogleViewModel
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MoogleViewModel::class.java)

        setUpViewPager()
        setUpKeystrokeListener()
    }

    private fun setUpDisposable() {
        compositeDisposable.addAll(
                viewModel.getMusic()
                        .subscribe { music ->

                        },
                viewModel.getAlbums()
                        .subscribe { albums ->
                            Toast.makeText(this, "Albums in ${albums.results.albummatches.album.size}", Toast.LENGTH_SHORT).show()
                        },
                viewModel.getArtists()
                        .subscribe { artists ->

                        }
        )
    }

    override fun onResume() {
        super.onResume()
        setUpDisposable()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun setUpKeystrokeListener() {
        with(search_edittext as EditText) {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    //Do nothing
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Do nothing
                }

                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.searchMediaText(text.toString())
                }
            })
        }
    }

    private fun setUpViewPager() {
        results_view_pager.adapter = ResultFragmentsPagerAdapter(supportFragmentManager)
        results_view_pager.addOnPageChangeListener(this@MainActivity)

        bottom_navigatonview.setOnNavigationItemSelectedListener({ viewItem ->
            when (viewItem.itemId) {
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
}

