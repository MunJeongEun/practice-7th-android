package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()

//        // 현재 화면의 미니 플레이어에 표시된 노래 제목과 가수 정보를 기반으로 Song 객체 생성
//        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 60, false, "music_lilac")

        // 미니 플레이어 클릭 시 SongActivity로 전환하고, 노래 제목과 가수 정보를 Intent로 전달
        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        // Song 객체에 저장된 노래 제목과 가수 정보를 로그에 출력
        Log.d("Song",song.title + song.singer)

    }

    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null) {
//            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
//        } else {
//            gson.fromJson(songJson, Song::class.java)
//        }
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0) {
            songDB.songDao().getSong(1)
        } else {
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
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

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단",
                0,
                180,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "청춘만화",
                "이무진",
                0,
                270,
                false,
                "music_coming_of_age_story",
                R.drawable.album_cover_leemugin,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "대관람차",
                "QWER",
                0,
                220,
                false,
                "music_ferris_wheel",
                R.drawable.album_cover_qwer,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "그건 사랑이었다고",
                "YENA (최예나)",
                0,
                180,
                false,
                "music_it_was_love",
                R.drawable.album_cover_yena,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "라일락",
                "아이유",
                0,
                210,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        val ssongs = songDB.songDao().getSongs()
        Log.d("DB data", ssongs.toString())
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)
        val albums = songDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "Butter",
                "방탄소년단",
                R.drawable.img_album_exp,
            )
        )

        songDB.albumDao().insert(
            Album(
                1,
                "만화 (滿花)",
                "이무진",
                R.drawable.album_cover_leemugin,
            )
        )

        songDB.albumDao().insert(
            Album(
                2,
                "1st Mini Album 'MANITO'",
                "QWER",
                R.drawable.album_cover_qwer,
            )
        )

        songDB.albumDao().insert(
            Album(
                3,
                "네모네모",
                "YENA (최예나)",
                R.drawable.album_cover_yena,
            )
        )

        songDB.albumDao().insert(
            Album(
                4,
                "IU 5th Album 'LILAC'",
                "아이유",
                R.drawable.img_album_exp2,
            )
        )
    }

    fun updateMiniPlayer(newSong: Song) {
        song = newSong
        setMiniPlayer(song)

        // 현재 재생 중인 곡 정보 저장
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply()
    }

}