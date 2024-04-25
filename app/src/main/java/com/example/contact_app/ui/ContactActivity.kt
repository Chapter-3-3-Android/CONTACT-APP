package com.example.contact_app.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import com.example.contact_app.R
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.ActivityContactBinding
import com.example.contact_app.extension.ButtonClickListener
import com.example.contact_app.ui.adapter.ViewPagerFragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator


class ContactActivity : AppCompatActivity() {
    private val binding: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: ViewPagerFragmentAdapter
    private val tabTexts = listOf("Contacts", "My Page")
    private val tabIcons = listOf(
        R.drawable.selector_contact_ic,
        R.drawable.selector_mypage_ic
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initFloatingActionButton()
    }

    override fun onRestart() {
        super.onRestart()

        Log.d("lifecycle", "onRestart")
        switchVisibility()
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

    private fun initView() {
        adapter = ViewPagerFragmentAdapter(this)

        adapter.getFragment() {
            switchTabPosition()
        }

        binding.vpItems.adapter = adapter

        setTabLayout()
    }

    private fun switchTabPosition() {
        val prePosition = binding.tlItems.selectedTabPosition
        val postPosition = if (prePosition == 0) 1 else 0

        adapter.notifyItemMoved(prePosition, postPosition)
    }

    private fun initFloatingActionButton() {
        binding.fabAddContact.setOnClickListener {
            val buttonClickListener = object : ButtonClickListener {
                override fun onSaveButtonClick() {
                    switchVisibility()

                    val fragment = ContactDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt("position", UserProvider.users.size)
                        }
                    }
                    // dialog에서 save 버튼을 눌렀을 때, detailFragment로 이동한다.
                    supportFragmentManager.commit {
                        replace(R.id.fl_items, fragment)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    // call

                }
            }

            val fragment = AddContactDialogFragment(buttonClickListener)
            fragment.show(supportFragmentManager, null)
        }
    }

    private fun switchVisibility() {
        val isViewPagerVisibility = binding.vpItems.isVisible

        if (isViewPagerVisibility) {
            with(binding) {
                flItems.visibility = View.VISIBLE
                vArea.visibility = View.GONE
            }
        } else {
            with(binding) {
                flItems.visibility = View.GONE
                vArea.visibility = View.VISIBLE
            }
        }
    }
}