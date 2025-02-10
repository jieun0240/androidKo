package com.example.starbuzzproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class PhotosDialogFragment : DialogFragment() {

    private var imageRes: Int = R.drawable.photo1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.photo_dialog, container, false)

        imageRes = arguments?.getInt("imageRes") ?: R.drawable.photo1

        val imageView: ImageView = view.findViewById(R.id.image)
        val downloadButton: Button = view.findViewById(R.id.download_button)

        imageView.setImageResource(imageRes)

        downloadButton.setOnClickListener {
            // 이미지 저장 로직 (임시로 Toast 메시지 출력)
            saveImage(imageRes)
        }

        // 다이얼로그 크기 조정
        val dialog = dialog
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,  // 너비는 화면 꽉 채우기
            ViewGroup.LayoutParams.MATCH_PARENT  // 높이는 내용에 맞게 조정
        )

        return view
    }

    private fun saveImage(imageRes: Int) {
        // 이미지 저장 로직 추가 (예: 내부 저장소에 저장)
        Toast.makeText(context, "저장되었습니다!", Toast.LENGTH_SHORT).show()
        // 실제 저장 구현은 여기 추가
    }

    companion object {
        fun newInstance(imageRes: Int): PhotosDialogFragment {
            val fragment = PhotosDialogFragment()
            val args = Bundle()
            args.putInt("imageRes", imageRes)
            fragment.arguments = args
            return fragment
        }
    }
}