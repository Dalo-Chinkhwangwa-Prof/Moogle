package com.example.admin.moogle.view

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.admin.moogle.R
import com.example.admin.moogle.adapter.ResultFragmentsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    companion object {
        const val fragmentCount: Int = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewPager()
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
}

