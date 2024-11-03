package com.example.flo

import android.content.Context.MODE_PRIVATE
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    // 전역 변수 선언
    lateinit var binding : ActivitySongBinding // activity_song.xml
    lateinit var song : Song
    lateinit var timer : Timer
    private var mediaPlayer : MediaPlayer? = null
    private var gson: Gson = Gson()

    // Activity가 처음 생성될 때 필요한 초기화 작업을 수행하는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) // XML 레이아웃을 인플레이션하여 ViewBinding 객체 생성
        setContentView(binding.root) // 인플레이트된 XML 레이아웃을 화면에 표시

        initSong()
        setPlayer(song)

        // song_down_ib 버튼 클릭 시 Activity 종료
        binding.songDownIb.setOnClickListener {
            finish()
        }

        // song_miniplayer_iv 버튼 클릭 시 정지 버튼으로 전환
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        // song_pause_iv 버튼 클릭 시 재생 버튼으로 전환
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song) {
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second/60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
    }

    // 재생 상태에 따라 버튼을 전환 (true: 재생 버튼 표시, false: 정지 버튼 표시)
    private fun setPlayerStatus(isPlaying : Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
        private var second: Int = 0
        private var mills: Long = 0L
        private var totalPlayedMills: Long = 0L  // 총 재생 시간을 누적할 변수 추가

        override fun run() {
            super.run()
            try {
                val totalMills = playTime * 1000L  // 전체 재생 시간을 밀리초로 변환

                while (true) {
                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(50)
                        mills += 50L
                        totalPlayedMills += 50L  // 총 재생 시간 누적

                        // 진행률 계산 (누적된 총 재생 시간 사용)
                        val progress = (totalPlayedMills * 100000 / totalMills).toInt()

                        runOnUiThread {
                            Log.d("Timer", "Progress: $progress, TotalPlayedMills: $totalPlayedMills, Total: $totalMills")
                            binding.songProgressSb.progress = progress
                        }

                        // 매 1초마다 second 증가
                        if (mills >= 1000) {
                            mills = 0  // mills만 초기화
                            second++

                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "스레드 종료 ${e.message}")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime) / 100 ) / 1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}