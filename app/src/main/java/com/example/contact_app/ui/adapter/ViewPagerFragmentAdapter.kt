package com.example.contact_app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contact_app.ui.ContactDetailFragment
import com.example.contact_app.ui.ContactListFragment
import com.example.contact_app.ui.MyPageFragment

class ViewPagerFragmentAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ContactListFragment()
            1 -> MyPageFragment()
            else -> throw RuntimeException("현재 Fragment는 2개 입니다.")
        }
    }
}