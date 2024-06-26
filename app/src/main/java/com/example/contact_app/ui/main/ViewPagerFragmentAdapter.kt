package com.example.contact_app.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contact_app.ui.contact.ContactDetailFragment
import com.example.contact_app.ui.contact.MyPageFragment
import com.example.contact_app.ui.contactList.ContactListFragment

class ViewPagerFragmentAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {

    private val contactListFragment = ContactListFragment()

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> contactListFragment
            1 -> MyPageFragment()
            else -> throw RuntimeException("현재 Fragment는 2개 입니다.")
        }
    }

    fun getFragment(switchTabPosition: () -> Unit): ContactListFragment {
        return contactListFragment.apply {
            onClickListener1() {
                switchTabPosition()
            }
        }
    }
}