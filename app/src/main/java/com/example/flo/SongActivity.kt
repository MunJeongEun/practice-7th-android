package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    // 변수 선언
    lateinit var binding : ActivitySongBinding // activity_song.xml

    // Activity가 처음 생성될 때 필요한 초기화 작업을 수행하는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) // XML 레이아웃을 인플레이션하여 ViewBinding 객체 생성
        setContentView(binding.root) // 인플레이트된 XML 레이아웃을 화면에 표시

        // song_down_ib 버튼 클릭 시 Activity 종료
        binding.songDownIb.setOnClickListener {
            finish()
        }

        // song_miniplayer_iv 버튼 클릭 시 정지 버튼으로 전환
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }

        // song_pause_iv 버튼 클릭 시 재생 버튼으로 전환
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        // Intent로부터 "title"과 "singer" 데이터를 전달받아 해당 텍스트 뷰에 설정
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songTitleTv.text = intent.getStringExtra("title")
            binding.songSingerTv.text = intent.getStringExtra("singer")
        }
    }

    // 재생 상태에 따라 버튼을 전환 (true: 재생 버튼 표시, false: 정지 버튼 표시)
    fun setPlayerStatus(isPlaying : Boolean) {
        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
}