package com.example.admin.moogle.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.admin.moogle.view.MainActivity
import com.example.admin.moogle.view.MediaFragment

class ResultFragmentsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return MediaFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return MainActivity.fragmentCount
    }
}