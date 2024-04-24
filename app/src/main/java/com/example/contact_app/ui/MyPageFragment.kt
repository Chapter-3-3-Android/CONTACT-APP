package com.example.contact_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstUser = UserProvider.users.firstOrNull()
        firstUser?.let { user ->
            displayUserInfo(user.name, user.phoneNumber, user.email)
        }
    }

    fun displayUserInfo(name: String, phoneNumber: String, email: String) {
        binding.tvName.text = name
        binding.tvNumberPhone.text = phoneNumber
        binding.tvDetailEmail.text = email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
