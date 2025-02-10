package com.example.starbuzzproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SunwooPage : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunwoo_page)

        // Toolbar에서 뒤로가기 버튼을 활성화
        setSupportActionBar(findViewById(R.id.toolbar))  // Toolbar를 setSupportActionBar로 설정
        supportActionBar?.setDisplayHomeAsUpEnabled(true)  // 뒤로가기 버튼 활성화

        // TabLayout과 ViewPager2 연결
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tab_layout)

        // TabLayout과 ViewPager2 연결
        val adapter = SunwooFragmentAdapter(this)
        viewPager.adapter = adapter

        // TabLayout과 ViewPager2 동기화
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Info"
                1 -> tab.text = "Photos"
                2 -> tab.text = "Playlist"
                3 -> tab.text = "Community"
            }
        }.attach()
    }

    // 뒤로가기 버튼 클릭 시 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            // 뒤로가기 버튼을 눌렀을 때 액티비티 종료
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}