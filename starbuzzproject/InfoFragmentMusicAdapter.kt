package com.example.starbuzzproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class InfoFragmentMusicAdapter(
    private val musicList: List<MusicItem>,
    private val itemClickListener: (MusicItem) -> Unit
) : RecyclerView.Adapter<InfoFragmentMusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_info_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val musicItem = musicList[position]
        holder.bind(musicItem)
    }

    override fun getItemCount(): Int = musicList.size

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(musicItem: MusicItem) {
            val title: TextView = itemView.findViewById(R.id.title)
            val singer: TextView = itemView.findViewById(R.id.singer)
            val date: TextView = itemView.findViewById(R.id.date)
            val album: ImageView = itemView.findViewById(R.id.album)

            // Bind data to UI elements
            title.text = musicItem.title
            singer.text = musicItem.singer
            date.text = musicItem.date

            // Use Glide to load album cover
            Glide.with(itemView.context)
                .load(musicItem.albumCover)
                .into(album)

            // Set click listener
            itemView.setOnClickListener {
                itemClickListener(musicItem)
            }
        }
    }
}