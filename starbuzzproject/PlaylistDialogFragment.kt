package com.example.starbuzzproject

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment

class PlaylistDialogFragment : DialogFragment() {

    private lateinit var albumCover: ImageView
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var prevButton: ImageButton
    private lateinit var playPauseButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var songProgress: SeekBar
    private lateinit var currentTime: TextView
    private lateinit var totalTime: TextView

    private var songList: List<PlaylistAdapter.Song> = emptyList()
    private var currentIndex: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.music_dialog, container, false)

        albumCover = view.findViewById(R.id.album_cover)
        songTitle = view.findViewById(R.id.song_title)
        artistName = view.findViewById(R.id.artist_name)
        prevButton = view.findViewById(R.id.prev_button)
        playPauseButton = view.findViewById(R.id.play_pause_button)
        nextButton = view.findViewById(R.id.next_button)
        songProgress = view.findViewById(R.id.song_progress)
        currentTime = view.findViewById(R.id.current_time)
        totalTime = view.findViewById(R.id.total_time)

        updateSongDetails()
        setupListeners()

        return view
    }

    private fun updateSongDetails() {
        val song = songList[currentIndex]
        albumCover.setImageResource(song.albumCover)
        songTitle.text = song.title
        artistName.text = song.artist

        // MediaPlayer 초기화
        mediaPlayer?.release() // 기존 MediaPlayer 해제
        mediaPlayer = MediaPlayer.create(requireContext(), song.songFile)
        mediaPlayer?.setOnPreparedListener {
            songProgress.max = it.duration
            totalTime.text = formatTime(it.duration)
        }

        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            playPauseButton.setImageResource(R.drawable.play)
        }
    }

    private fun setupListeners() {
        playPauseButton.setOnClickListener {
            if (isPlaying) {
                pauseSong()
            } else {
                playSong()
            }
        }

        prevButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateSongDetails()
                playSong()
            } else {
                Toast.makeText(requireContext(), "No previous song", Toast.LENGTH_SHORT).show()
            }
        }

        nextButton.setOnClickListener {
            if (currentIndex < songList.size - 1) {
                currentIndex++
                updateSongDetails()
                playSong()
            } else {
                Toast.makeText(requireContext(), "No next song", Toast.LENGTH_SHORT).show()
            }
        }

        songProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun playSong() {
        mediaPlayer?.start()
        isPlaying = true
        playPauseButton.setImageResource(R.drawable.pause)
        updateSeekBar()
    }

    private fun pauseSong() {
        mediaPlayer?.pause()
        isPlaying = false
        playPauseButton.setImageResource(R.drawable.play)
    }

    private fun updateSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    val currentPosition = it.currentPosition
                    val remainingTime = it.duration - currentPosition

                    // SeekBar와 TextView 업데이트
                    songProgress.progress = currentPosition
                    currentTime.text = formatTime(currentPosition)
                    totalTime.text = formatTime(remainingTime) // 남은 시간 표시

                    handler.postDelayed(this, 1000)
                }
            }
        }, 0)
    }

    private fun formatTime(ms: Int): String {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60)) % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacksAndMessages(null)
    }

    fun setSongList(songList: List<PlaylistAdapter.Song>, index: Int) {
        this.songList = songList
        this.currentIndex = index
    }
}