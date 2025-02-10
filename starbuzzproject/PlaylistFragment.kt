package com.example.starbuzzproject

import android.app.Dialog
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistFragment : Fragment(R.layout.fragment_playlist) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaylistAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 1) // 세로로 1열 설정

        // 어댑터 설정
        adapter = PlaylistAdapter()
        recyclerView.adapter = adapter

        // playBtn 클릭 리스너
        val playBtn: ImageView = view.findViewById(R.id.playBtn)
        playBtn.setOnClickListener {
            val firstSong = adapter.getSongAt(0) // 첫 번째 노래 가져오기
            if (firstSong != null) {
                showMusicDialog(firstSong) // 다이얼로그 띄우기
            }
        }
    }

    private fun showMusicDialog(song: PlaylistAdapter.Song) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.music_dialog)

        val albumCover: ImageView = dialog.findViewById(R.id.album_cover)
        val songTitle: TextView = dialog.findViewById(R.id.song_title)
        val artistName: TextView = dialog.findViewById(R.id.artist_name)
        val playPauseButton: ImageButton = dialog.findViewById(R.id.play_pause_button)
        val songProgress: SeekBar = dialog.findViewById(R.id.song_progress)
        val currentTime: TextView = dialog.findViewById(R.id.current_time)
        val totalTime: TextView = dialog.findViewById(R.id.total_time)

        // 현재 곡의 인덱스를 가져옵니다.
        val currentIndex = adapter.getSongIndex(song)

        // 노래 정보 설정
        albumCover.setImageResource(song.albumCover)
        songTitle.text = song.title
        artistName.text = song.artist

        // MediaPlayer 초기화
        var mediaPlayer = MediaPlayer.create(requireContext(), song.songFile)
        songProgress.max = mediaPlayer.duration
        totalTime.text = formatTime(mediaPlayer.duration)

        // 재생 및 일시 정지 버튼 동작 설정
        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playPauseButton.setImageResource(R.drawable.play) // Play 아이콘으로 변경
            } else {
                mediaPlayer.start()
                playPauseButton.setImageResource(R.drawable.pause) // Pause 아이콘으로 변경
            }
        }

        // SeekBar 업데이트
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                if (mediaPlayer.isPlaying) {
                    songProgress.progress = mediaPlayer.currentPosition
                    currentTime.text = formatTime(mediaPlayer.currentPosition)
                    totalTime.text = formatTime(mediaPlayer.duration - mediaPlayer.currentPosition)
                }
                handler.postDelayed(this, 1000)
            }
        })

        // SeekBar 변경 시 재생 위치 업데이트
        songProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // 다이얼로그 닫힐 때 MediaPlayer 해제
        dialog.setOnDismissListener {
            mediaPlayer.release()
        }

        // 자동 재생 시작
        mediaPlayer.start()
        playPauseButton.setImageResource(R.drawable.pause) // Pause 아이콘으로 설정

        // **노래가 끝났을 때 다음 곡으로 넘어가기**
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release() // 현재 MediaPlayer 해제

            val nextIndex = currentIndex + 1
            if (nextIndex < adapter.itemCount) {
                // 다음 곡 정보 가져오기
                val nextSong = adapter.getSongAt(nextIndex)
                if (nextSong != null) {
                    dialog.dismiss() // 현재 다이얼로그 닫기
                    showMusicDialog(nextSong) // 다음 곡 다이얼로그 띄우기
                }
            } else {
                // 마지막 곡일 경우: 재생 중단 및 다이얼로그 유지
                playPauseButton.setImageResource(R.drawable.play) // Play 아이콘으로 설정
            }
        }

        dialog.show()
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = (milliseconds / 1000) % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}
