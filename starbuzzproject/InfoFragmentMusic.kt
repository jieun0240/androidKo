package com.example.starbuzzproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfoFragmentMusic : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicAdapter: InfoFragmentMusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.info_fragment_music, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter and set it to the RecyclerView
        musicAdapter = InfoFragmentMusicAdapter(getMusicList()) { music ->
            // Handle item click
            openMusicDialog(music)
        }
        recyclerView.adapter = musicAdapter

        return view
    }

    private fun getMusicList(): List<MusicItem> {
        return listOf(
            MusicItem("소년 (boy)", "더보이즈 | 작사 김선우", "2017.12.06", R.drawable.cover_boy, R.raw.boy),
            MusicItem("Giddy Up", "더보이즈 | 작사 김선우", "2018.04.03", R.drawable.cover_giddy_up, R.raw.giddy_up),
            MusicItem("Right Here", "더보이즈 | 작사 김선우", "2018.09.05", R.drawable.cover_right_here, R.raw.right_here),
            MusicItem("환상고백", "더보이즈 | 작사 김선우", "2020.02.10", R.drawable.cover_break_your_rules, R.raw.break_your_rules)
        )
    }

    private fun openMusicDialog(music: MusicItem) {
        val dialog = MusicDialogFragment.newInstance(music, ArrayList(getMusicList()))
        dialog.show(parentFragmentManager, "musicDialog")
    }
}