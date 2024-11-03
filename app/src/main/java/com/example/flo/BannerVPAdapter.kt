package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentlist : ArrayList<Fragment> = ArrayList()

    // ViewPager2에 표시할 총 페이지 수를 반환
    override fun getItemCount(): Int = fragmentlist.size

    // 지정된 위치(position)에 해당하는 Fragment를 반환
    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    // 새로운 Fragment를 리스트에 추가
    fun addFragment(fragment: Fragment) {
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1)
    }
}