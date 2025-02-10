package com.example.starbuzzproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class IdolAdapter(
    private val profiles: List<Int>,
    private val names: List<String>,
    private val favoriteProfiles: Set<Int> = mutableSetOf()
) : RecyclerView.Adapter<IdolAdapter.IdolViewHolder>() {

    var favoriteClickListener: ((Int, String) -> Unit)? = null

    class IdolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.idolProfile)
        val title: TextView = itemView.findViewById(R.id.title)
        val starImage: ImageView = itemView.findViewById(R.id.emptyStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdolViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return IdolViewHolder(view)
    }

    override fun onBindViewHolder(holder: IdolViewHolder, position: Int) {
        holder.profileImage.setImageResource(profiles[position])
        holder.title.text = names[position]

        // 즐겨찾기된 아이템이면 채워진 별로 표시
        if (favoriteProfiles.contains(profiles[position])) {
            holder.starImage.setImageResource(R.drawable.ic_star_filled) // 채워진 별 이미지
        } else {
            holder.starImage.setImageResource(R.drawable.ic_star_empty) // 빈 별 이미지
        }

        // 아이템 클릭 시 SunwooPage로 이동
        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, SunwooPage::class.java)
            context.startActivity(intent)
        }

        holder.starImage.setOnClickListener {
            if (favoriteProfiles.contains(profiles[position])) {
                holder.starImage.setImageResource(R.drawable.ic_star_empty) // 빈 별로 변경
            } else {
                holder.starImage.setImageResource(R.drawable.ic_star_filled) // 채워진 별로 변경
            }
            favoriteClickListener?.invoke(profiles[position], names[position]) // 아이템 선택 시 콜백 호출
        }
    }

    override fun getItemCount(): Int {
        return profiles.size
    }
}