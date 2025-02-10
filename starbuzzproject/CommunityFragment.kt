package com.example.starbuzzproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class CommunityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: MyDbHelper
    private lateinit var editText: EditText
    private lateinit var fabAdd: ExtendedFloatingActionButton
    private lateinit var communityAdapter: CommunityAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_community, container, false)

        dbHelper = MyDbHelper(requireContext())
        recyclerView = binding.findViewById(R.id.main_recyclerView)
        fabAdd = binding.findViewById(R.id.fabAdd)

        // EditText가 존재하는 경우 처리
        editText = binding.findViewById(R.id.editTitle)

        // RecyclerView 설정
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        communityAdapter = CommunityAdapter(requireContext(), dbHelper.getAllData())
        recyclerView.adapter = communityAdapter

        // 데이터 추가 버튼 클릭 리스너 설정
        fabAdd.setOnClickListener {
            val title = editText.text.toString()

            if (title.isNotEmpty()) {
                dbHelper.insertData("default_image", title) // 예시로 "default_image" 사용
                communityAdapter.updateData(dbHelper.getAllData()) // 데이터 업데이트
            } else {
                // 제목이 비어 있으면 사용자에게 알림을 추가할 수 있습니다.
            }
        }

        return binding
    }
}