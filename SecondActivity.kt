package com.example.and_1126

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.and_1126.databinding.ActivityMainBinding
import com.example.and_1126.databinding.SecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : SecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        다시 전 화면으로 돌아가는(종료되는 코드)
        binding.btnReturn.setOnClickListener {finish()}
    }
}