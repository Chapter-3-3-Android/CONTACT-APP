package com.example.contact_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.contact_app.R
import com.example.contact_app.data.model.UserPrivider

import com.example.contact_app.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class ContactActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserPrivider.createDummyUsers()
        // Default로 ContactList Fragment가 뜨도록 추가함
        setFragment(ContactListFragment())
        //val userList:List<User> = UserPrivider.createDummyUsers()


        // 프레임 레이아웃에 Fragment를 띄운다.
        with(binding) {
            tlItems.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.connectFragment()
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {}
                override fun onTabReselected(p0: TabLayout.Tab?) {}
            })

        }
    }

    private fun TabLayout.Tab.connectFragment() = with(binding) {
        when (this@connectFragment.position) {
            0 -> setFragment(ContactListFragment())
            1 -> setFragment(MyPageFragment())
        }
    }

    private fun setFragment(frag: Fragment) {
        // SFM : 사용자의 상호작용에 응답해 Fragment를 add 또는 remove 할 수 있는 class
        supportFragmentManager.commit {
            replace(R.id.fl_frag, frag)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }





}