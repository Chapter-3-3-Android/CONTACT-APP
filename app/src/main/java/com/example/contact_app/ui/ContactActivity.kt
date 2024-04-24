package com.example.contact_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contact_app.R
import com.example.contact_app.databinding.ActivityContactBinding
import com.example.contact_app.ui.adapter.ViewPagerFragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator


class ContactActivity : AppCompatActivity() {
    private val binding: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: ViewPagerFragmentAdapter
    private val tabTexts = listOf("Contacts", "My Page")
    private val tabIcons = listOf(R.drawable.ic_contacts, R.drawable.ic_user)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = ViewPagerFragmentAdapter(this)

        adapter.getFragment() {
            switchTabPosition()
        }

        binding.vpItems.adapter = adapter

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

    private fun switchTabPosition() {
        val prePosition = binding.tlItems.selectedTabPosition
        val postPosition = if (prePosition == 0) 1 else 0

        adapter.notifyItemMoved(prePosition, postPosition)
    }
}