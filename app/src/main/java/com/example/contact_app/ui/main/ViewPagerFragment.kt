package com.example.contact_app.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.contact_app.R
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentContactBinding
import com.example.contact_app.databinding.FragmentViewPagerBinding
import com.example.contact_app.extension.ButtonClickListener
import com.example.contact_app.ui.contact.ContactDetailFragment
import com.example.contact_app.ui.dialog.AddContactDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ViewPagerFragmentAdapter

    private val tabTexts = listOf("Contacts", "My Page")
    private val tabIcons = listOf(
        R.drawable.selector_contact_ic,
        R.drawable.selector_mypage_ic
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        adapter = ViewPagerFragmentAdapter(requireActivity())

        adapter.getFragment() {
            switchTabPosition()
        }

        binding.vpItems.adapter = adapter

        setTabLayout()
        initFloatingActionButton()
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

    private fun initFloatingActionButton() {
        binding.fabAddContact.setOnClickListener {
            val buttonClickListener = object : ButtonClickListener {
                override fun onSaveButtonClick() {
                    val fragment = ContactDetailFragment.newInstance(
                        UserProvider.users.size - 1
                    )

                    // dialog에서 save 버튼을 눌렀을 때, detailFragment로 이동한다.
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_items, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

            val fragment = AddContactDialogFragment(buttonClickListener)
            fragment.show(childFragmentManager, "addContactDialog")
        }
    }

    companion object {
        private var INSTANCE: ViewPagerFragment? = null

        fun newInstance(): ViewPagerFragment {
            val newInstance = INSTANCE ?: ViewPagerFragment()

            if (INSTANCE == null) {
                INSTANCE = newInstance
            }

            return newInstance
        }
    }
}