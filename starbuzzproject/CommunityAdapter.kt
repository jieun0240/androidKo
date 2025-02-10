package com.example.starbuzzproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommunityAdapter(private val context: Context, private var communityList: List<CommunityItem>) :
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_community, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val communityItem = communityList[position]
        holder.titleText.text = communityItem.title
        // 이미지 경로에 맞게 이미지 설정
        // 예시: holder.imageView.setImageURI(communityItem.imageUri)
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    fun updateData(newData: List<CommunityItem>) {
        communityList = newData
        notifyDataSetChanged()
    }

    class CommunityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.uploadImg)
        val titleText: TextView = view.findViewById(R.id.title)
    }
}