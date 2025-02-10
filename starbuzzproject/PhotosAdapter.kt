package com.example.starbuzzproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private val photoList = arrayListOf(
        R.drawable.photo1, R.drawable.photo2, R.drawable.photo3,
        R.drawable.photo4, R.drawable.photo5, R.drawable.photo6,
        R.drawable.photo7, R.drawable.photo8, R.drawable.photo9,
        R.drawable.photo10, R.drawable.photo11, R.drawable.photo12
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photos, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivPhoto)

        fun bind(imageRes: Int) {
            imageView.setImageResource(imageRes)
            imageView.setOnClickListener {
                // 클릭 시 해당 이미지를 새로운 DialogFragment로 띄움
                val fragment = PhotosDialogFragment.newInstance(imageRes)
                fragment.show((itemView.context as AppCompatActivity).supportFragmentManager, "photoDialog")
            }
        }
    }
}