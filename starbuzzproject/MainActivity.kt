package com.example.starbuzzproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starbuzzproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val profile = arrayListOf(
        R.drawable.sangyeon, R.drawable.jacob, R.drawable.younghoon, R.drawable.hyunjae,
        R.drawable.juyeon, R.drawable.kevin, R.drawable.chanhee, R.drawable.q,
        R.drawable.juhakyeon, R.drawable.sunwoo, R.drawable.eric
    )
    private val artiName = arrayListOf(
        "상연", "제이콥", "영훈", "현재", "주연", "케빈", "뉴", "큐",
        "주학년", "선우", "에릭"
    )
    private val favoriteProfiles = mutableSetOf<Int>() // 즐겨찾기된 프로필 리스트

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val profiles = result.data?.getIntegerArrayListExtra("profiles") ?: arrayListOf()
                favoriteProfiles.clear()
                favoriteProfiles.addAll(profiles)
                updateFavorites() // 즐겨찾기된 아이템만 업데이트
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 처음에 즐겨찾기된 아이템 수 카운트
        val favoriteCount = favoriteProfiles.size

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.personPlus -> {
                    // FavoriteActivity로 이동
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startForResult.launch(intent) // 선택된 즐겨찾기 아이템들을 받아옴
                    true
                }
                else -> false
            }
        }

        updateFavorites() // 즐겨찾기된 아이템만 표시하기
    }

    // 즐겨찾기된 아이템만 표시하는 기능
    fun updateFavorites() {
        val profilesToShow = profile.filterIndexed { index, _ -> favoriteProfiles.contains(profile[index]) }
        val namesToShow = artiName.filterIndexed { index, _ -> favoriteProfiles.contains(profile[index]) }

        val adapter = IdolAdapter(profilesToShow, namesToShow, favoriteProfiles.toSet()) // 즐겨찾기된 아이템만 전달

        adapter.favoriteClickListener = { _, _ ->
            // 아이템 클릭 시 강제로 SunwooPage로 이동
            val intent = Intent(this, SunwooPage::class.java)
            startActivity(intent)
        }

        // 즐겨찾기된 아이템 수 다시 계산
        val favoriteCount = favoriteProfiles.size
        binding.artiNum.text = "최애 아티스트 $favoriteCount"

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}