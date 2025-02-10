package com.example.starbuzzproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfoFragmentNews : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.info_fragment_news, container, false)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_videos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = InfoFragmentNewsAdapter()

        return view
    }
}