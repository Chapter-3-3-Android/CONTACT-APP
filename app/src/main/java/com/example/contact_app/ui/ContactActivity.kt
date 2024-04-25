package com.example.contact_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.contact_app.R
import com.example.contact_app.databinding.ActivityContactBinding
import com.example.contact_app.ui.main.ViewPagerFragment

class ContactActivity : AppCompatActivity() {
    private val binding: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ininView()
    }

    private fun ininView() {
        val viewPagerFragment = ViewPagerFragment.newInstance()

        supportFragmentManager.commit {
            replace(R.id.fl_items, viewPagerFragment)
            addToBackStack(null)
        }
    }
}