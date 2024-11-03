package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    // ViewPager2에 표시할 총 페이지 수를 반환
    override fun getItemCount(): Int = 3

    // 지정된 위치(position)에 해당하는 Fragment를 순서대로 반환
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SongFragment() // 첫 번째 페이지 : SongFragment
            1 -> DetailFragment() // 두 번째 페이지 : DetailFragment
            else -> VideoFragment() // 세 번째 페이지 : VideoFragment
        }
    }
}