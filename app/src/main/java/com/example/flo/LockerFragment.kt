package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerBinding
import java.util.ArrayList

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private var musicDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        musicDatas.apply {
            add(Album("Butter", "방탄소년단", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유", R.drawable.img_album_exp2))
            add(Album("대관람차", "QWER", R.drawable.album_cover_qwer))
            add(Album("그건 사랑이었다고", "YENA (최예나)", R.drawable.album_cover_yena))
            add(Album("HAPPY", "DAY6", R.drawable.album_cover_day6))
            add(Album("청춘만화", "이무진", R.drawable.album_cover_leemugin))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.album_cover_aespa))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.album_cover_bts))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.album_cover_momoland))
            add(Album("Weekend", "태연 (Tae Yeoan)", R.drawable.album_cover_taeyeon))
        }

        val musicRVAdapter = MusicRVAdapter(musicDatas)
        binding.musicListRv.adapter = musicRVAdapter
        binding.musicListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        musicRVAdapter.setMyItemClickListener(object:MusicRVAdapter.MyItemClickListener{
            override fun onRemoveAlbum(position: Int) {
                musicRVAdapter.removeItem(position)
            }
        })

        return binding.root
    }
}