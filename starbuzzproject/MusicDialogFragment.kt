package com.example.starbuzzproject

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide

class MusicDialogFragment : DialogFragment() {

    private var isPlaying = false
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var musicItem: MusicItem
    private lateinit var musicList: ArrayList<MusicItem>
    private var currentIndex: Int = 0
    private lateinit var seekBar: SeekBar
    private var currentPosition: Int = 0  // 추가된 변수: 현재 위치 저장

    companion object {
        private const val ARG_MUSIC = "arg_music"
        private const val ARG_MUSIC_LIST = "arg_music_list"

        fun newInstance(musicItem: MusicItem, musicList: ArrayList<MusicItem>): MusicDialogFragment {
            val fragment = MusicDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_MUSIC, musicItem)
            args.putParcelableArrayList(ARG_MUSIC_LIST, musicList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.music_dialog, container, false)

        musicItem = arguments?.getParcelable(ARG_MUSIC)!!
        musicList = arguments?.getParcelableArrayList(ARG_MUSIC_LIST)!!
        currentIndex = musicList.indexOf(musicItem)

        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        // Glide로 앨범 커버 로드
        Glide.with(view.context)
            .load(musicItem.albumCover)
            .into(view.findViewById<ImageView>(R.id.album_cover))

        // 제목과 아티스트 이름을 텍스트뷰에 설정
        view.findViewById<TextView>(R.id.song_title).text = musicItem.title
        view.findViewById<TextView>(R.id.artist_name).text = musicItem.singer

        // 재생/일시 정지 버튼 클릭 리스너
        val playPauseButton = view.findViewById<ImageButton>(R.id.play_pause_button)
        playPauseButton.setImageResource(R.drawable.play)  // 초기 버튼 상태는 pause로 설정
        playPauseButton.setOnClickListener {
            if (isPlaying) {
                mediaPlayer?.pause()  // 일시 정지
                currentPosition = mediaPlayer?.currentPosition ?: 0  // 현재 위치 저장
                playPauseButton.setImageResource(R.drawable.play)  // play 버튼으로 변경
            } else {
                if (mediaPlayer == null) {
                    playMusic(musicItem.songFile) // 노래가 아직 재생되지 않았으면 시작
                } else {
                    mediaPlayer?.seekTo(currentPosition)  // 저장된 위치로 이동
                    mediaPlayer?.start()  // 재생
                }
                playPauseButton.setImageResource(R.drawable.pause)  // pause 버튼으로 변경
            }
            isPlaying = !isPlaying
        }

        // SeekBar 초기화
        seekBar = view.findViewById(R.id.song_progress)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // SeekBar 조작 시, MediaPlayer 위치 업데이트
                    mediaPlayer?.seekTo(progress)
                    currentPosition = progress  // 위치 업데이트
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // 이전 버튼 클릭 리스너
        view.findViewById<ImageButton>(R.id.prev_button).setOnClickListener {
            moveToPreviousTrack()
        }

        // 다음 버튼 클릭 리스너
        view.findViewById<ImageButton>(R.id.next_button).setOnClickListener {
            moveToNextTrack()
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun playMusic(songFile: Int) {
        // 이전에 플레이된 음악이 있다면 해제
        mediaPlayer?.release()

        // MediaPlayer 초기화
        mediaPlayer = MediaPlayer.create(context, songFile)
        mediaPlayer?.start() // 재생 시작

        // 전체 시간 표시 (초 단위 -> 분:초로 변환)
        view?.findViewById<TextView>(R.id.total_time)?.text = formatTime(mediaPlayer?.duration ?: 0)

        // SeekBar의 최대값을 음원 길이로 설정
        seekBar.max = mediaPlayer?.duration ?: 0

        // MediaPlayer의 진행 상태를 SeekBar에 반영
        val updateSeekBar = object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    seekBar.progress = it.currentPosition
                    // 현재 시간 표시 (초 단위 -> 분:초로 변환)
                    view?.findViewById<TextView>(R.id.current_time)?.text = formatTime(it.currentPosition)

                    // 남은 시간 계산 (전체 시간 - 현재 시간)
                    val remainingTime = it.duration - it.currentPosition
                    updateRemainingTime(remainingTime) // 남은 시간 업데이트

                    seekBar.postDelayed(this, 1000) // 1초마다 업데이트
                }
            }
        }
        seekBar.postDelayed(updateSeekBar, 1000)

        // 음악이 끝났을 때 다음 곡으로 넘어가도록 설정
        mediaPlayer?.setOnCompletionListener {
            moveToNextTrack() // 다음 트랙으로 이동
        }
    }

    // 남은 시간을 계산해서 업데이트하는 함수
    private fun updateRemainingTime(remainingTime: Int) {
        view?.findViewById<TextView>(R.id.total_time)?.text = formatTime(remainingTime)
    }

    // 시간을 분:초 형식으로 변환하는 함수
    private fun formatTime(timeInMillis: Int): String {
        val minutes = timeInMillis / 1000 / 60
        val seconds = timeInMillis / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

//    private fun updateRemainingTime(remainingTime: Int) {
//        val minutes = remainingTime / 1000 / 60
//        val seconds = remainingTime / 1000 % 60
//        val timeString = String.format("%02d:%02d", minutes, seconds)
//
//        // 남은 시간 업데이트
//        view?.findViewById<TextView>(R.id.total_time)?.text = timeString
//    }

    private fun moveToNextTrack() {
        currentIndex = (currentIndex + 1) % musicList.size
        musicItem = musicList[currentIndex]
        updateUI()
        playMusic(musicItem.songFile)
    }

    private fun moveToPreviousTrack() {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else musicList.size - 1
        musicItem = musicList[currentIndex]
        updateUI()
        playMusic(musicItem.songFile)
    }

    private fun updateUI() {
        view?.findViewById<TextView>(R.id.song_title)?.text = musicItem.title
        view?.findViewById<TextView>(R.id.artist_name)?.text = musicItem.singer
        view?.findViewById<ImageView>(R.id.album_cover)?.let {
            Glide.with(it.context).load(musicItem.albumCover).into(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}