package com.example.starbuzzproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    // 초기화된 데이터 목록 (앨범 이미지, 제목, 아티스트, 날짜, songFile 추가)
    private val songList = listOf(
        Song(R.drawable.cover_ho, "Honey", "더보이즈", "2023", R.raw.honey),  // songFile 추가
        Song(R.drawable.cover_st, "STUPID LIAR", "빅뱅", "2013", R.raw.stupid_liar),
        Song(R.drawable.cover_don, "집에 가지마(Don't Go Leave)", "GD&TOP", "2010", R.raw.dont_go_home)
    )

    fun getSongAt(position: Int): Song? {
        return if (position in songList.indices) songList[position] else null
    }

    fun getSongIndex(song: Song): Int {
        return songList.indexOf(song)
    }

    // recyclerView 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info_music, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val song = songList[position]
        holder.bind(song)

        holder.itemView.setOnClickListener {
            val dialog = PlaylistDialogFragment()
            dialog.setSongList(songList, position)
            if (holder.itemView.context is AppCompatActivity) {
                dialog.show(
                    (holder.itemView.context as AppCompatActivity).supportFragmentManager,
                    "PlaylistDialog"
                )
            }
        }
    }

    override fun getItemCount(): Int = songList.size

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val albumCover: ImageView = itemView.findViewById(R.id.album)
        private val songTitle: TextView = itemView.findViewById(R.id.title)
        private val songArtist: TextView = itemView.findViewById(R.id.singer)
        private val songDate: TextView = itemView.findViewById(R.id.date)

        fun bind(song: Song) {
            albumCover.setImageResource(song.albumCover)
            songTitle.text = song.title
            songArtist.text = song.artist
            songDate.text = song.date
        }
    }

    data class Song(val albumCover: Int, val title: String, val artist: String, val date: String, val songFile: Int)
}