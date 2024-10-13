package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 현재 화면의 미니 플레이어에 표시된 노래 제목과 가수 정보를 기반으로 Song 객체 생성
        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())

        // 미니 플레이어 클릭 시 SongActivity로 전환하고, 노래 제목과 가수 정보를 Intent로 전달
        binding.mainPlayerCl.setOnClickListener {
//            startActivity(Intent(this, SongActivity::class.java))
            val intent = Intent(this,SongActivity::class.java) // SongActivity로 전환을 위한 Intent 생성
            intent.putExtra("title",song.title) // Intent에 노래 제목 추가
            intent.putExtra("singer",song.singer) // Intent에 가수 정보 추가
            startActivity(intent)
        }

        initBottomNavigation()

        // Song 객체에 저장된 노래 제목과 가수 정보를 로그에 출력
        Log.d("Song",song.title + song.singer)

    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}