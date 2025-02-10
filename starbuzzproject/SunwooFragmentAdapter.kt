package com.example.starbuzzproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SunwooFragmentAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4 // 4개의 탭

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragment() // Info Fragment
            1 -> PhotosFragment() // Photos Fragment
            2 -> PlaylistFragment() // Playlist Fragment
            3 -> CommunityFragment() // Community Fragment
            else -> InfoFragment()
        }
    }
}