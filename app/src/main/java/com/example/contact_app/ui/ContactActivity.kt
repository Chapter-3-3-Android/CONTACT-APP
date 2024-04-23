package com.example.contact_app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.contact_app.R
import com.example.contact_app.data.model.UserPrivider
import com.example.contact_app.databinding.ActivityMainBinding
import com.example.contact_app.ui.adapter.ViewPagerFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayoutMediator

class ContactActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val tabTexts = listOf("Contacts", "My Page")
    private val tabIcons = listOf(R.drawable.ic_contacts, R.drawable.ic_user)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewPagerFragmentAdapter = ViewPagerFragmentAdapter(this)
        binding.vpItems.adapter = viewPagerFragmentAdapter

        setTabLayout()
    }

    private fun setTabLayout() {
        with(binding) {
            // TabLayoutMediator를 통해서 TabLayout과 ViewPager2를 연동함
            TabLayoutMediator(tlItems, vpItems) { tab, position ->
                // 각 Tab의 text와 Icon 지정
                tab.text = tabTexts[position]
                tab.setIcon(tabIcons[position])
            }.attach()
        }
    }
}