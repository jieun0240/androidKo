package com.example.starbuzzproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starbuzzproject.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val profile = arrayListOf(
        R.drawable.sangyeon, R.drawable.jacob, R.drawable.younghoon, R.drawable.hyunjae,
        R.drawable.juyeon, R.drawable.kevin, R.drawable.chanhee, R.drawable.q,
        R.drawable.juhakyeon, R.drawable.sunwoo, R.drawable.eric
    )
    private val artiName = arrayListOf(
        "상연", "제이콥", "영훈", "현재", "주연", "케빈", "뉴", "큐",
        "주학년", "선우", "에릭"
    )
    private val favoriteProfiles = mutableSetOf<Int>() // 즐겨찾기된 프로필 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar에서 뒤로가기 버튼을 활성화
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = IdolAdapter(profile, artiName, favoriteProfiles.toSet()).apply {
            favoriteClickListener = { profile, name ->
                // 별 클릭 시 토스트 메시지 표시
                Toast.makeText(this@FavoriteActivity, "${name} 아티스트가 추가되었습니다!", Toast.LENGTH_SHORT).show()

                if (favoriteProfiles.contains(profile)) {
                    favoriteProfiles.remove(profile) // 이미 즐겨찾기 된 아이템은 제거
                } else {
                    favoriteProfiles.add(profile) // 즐겨찾기에 추가
                }
            }
        }

        binding.recyclerViewFavorites.adapter = adapter
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(this)

        // '뒤로 가기' 버튼 클릭
        binding.toolbar.setNavigationOnClickListener {
            val intent = Intent()
            // 즐겨찾기된 프로필만 넘김
            intent.putExtra("profiles", ArrayList(favoriteProfiles)) // 즐겨찾기된 아이템만 전송
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}