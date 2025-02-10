package com.example.starbuzzproject

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class InfoPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4 // Number of tabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragmentNews()
            1 -> InfoFragmentSchedule()
            2 -> InfoFragmentWork()
            3 -> InfoFragmentMusic()
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }
}