package com.example.starbuzzproject

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoFragmentNewsAdapter : RecyclerView.Adapter<InfoFragmentNewsAdapter.NewsViewHolder>() {

    private val thumbnails = listOf(
        R.drawable.yt_tr,
        R.drawable.yt_ho,
        R.drawable.yt_ro,
        R.drawable.yt_aw,
        R.drawable.yt_wa,
        R.drawable.yt_in_wh
    )
    private val titles = listOf(
        "[얼빡직캠 4K] 더보이즈 선우 'TRIGGER (導火線)' (THE BOYZ SUNWOO Facecam) @뮤직뱅크(Music Bank) 241101",
        "[얼빡직캠 4K] 더보이즈 선우 'Honey'(THE BOYZ Special Unit SUNWOO Facecam) @뮤직뱅크(Music Bank) 240112",
        "[페이스캠4K] 더보이즈 선우 'ROAR' (THE BOYZ SUNWOO FaceCam) @SBS Inkigayo 230226",
        "[입덕직캠] 더보이즈 선우 직캠 4K 'Awake' (THE BOYZ SUNWOO FanCam) | @MCOUNTDOWN_2023.2.23",
        "[얼빡직캠 4K] 더보이즈 선우 'WATCH IT'(THE BOYZ SUNWOO Facecam) @뮤직뱅크(Music Bank) 231124",
        "[2022 가요대전 페이스캠 4K] 더보이즈 선우 'INTRO + WHISPER' (THE BOYZ SUNWOO FaceCam)│@SBS Gayo Daejeon 221224"
    )
    private val dates = listOf(
        "2024.11.01",
        "2024.01.12",
        "2023.02.26",
        "2023.02.23",
        "2023.11.24",
        "2022.12.24"
    )
    // 유튜브 링크 추가 (각 제목에 맞는 유튜브 링크 추가)
    private val youtubeLinks = listOf(
        "https://youtu.be/mcXgRZtQP88?si=EavsfUcnCjazx4-B",
        "https://youtu.be/Pz8OpumANUc?si=47B1iBNJ7QzZLRoM",
        "https://youtu.be/hUC5_mFUXUo?si=045IF9mr8b1aDxnl",
        "https://youtu.be/QgKBd00tBE4?si=gPl6CHOlk7pLzRNv",
        "https://youtu.be/jhZ_OGn6NUY?si=UVIO0Y0GwAa1bNV8",
        "https://youtu.be/3o8dm8yLrzo?si=uUi9Zho169m4CKSz"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(thumbnails[position], titles[position], dates[position], youtubeLinks[position])
    }

    override fun getItemCount(): Int = titles.size

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnail)
        private val titleTextView: TextView = itemView.findViewById(R.id.txtTitle)
        private val dateTextView: TextView = itemView.findViewById(R.id.txtDate)

        fun bind(thumbnail: Int, title: String, date: String, youtubeLink: String) {
            thumbnailImageView.setImageResource(thumbnail)
            titleTextView.text = title
            dateTextView.text = date

            // 아이템 클릭 시 유튜브 링크 열기
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                itemView.context.startActivity(intent)
            }
        }
    }
}