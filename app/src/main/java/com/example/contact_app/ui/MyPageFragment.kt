package com.example.contact_app.ui

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentAddScheduleDialogBinding
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


        binding.tvPlus.setOnClickListener {
            val dialog = AddScheduleDialogFragment()
            dialog.show(parentFragmentManager,"AddScheduleDialog")

             }



        //전화아이콘 눌렀을때 연결
        binding.imgTelephone.setOnClickListener {
            openTelephone(binding.tvNumberPhone.text.toString())
        }

        //문자아이콘 눌렀을때 연결
        binding.imgSms.setOnClickListener {
            openMessenger(binding.tvNumberPhone.text.toString())
        }

        //copy 버튼 눌렀을때 복사(전화번호)
        binding.tvCopyPhone.setOnClickListener {
            copyText(binding.tvNumberPhone.text.toString())
        }

        //copy 버튼 눌렀을때 복사(이메일)
        binding.tvCopyEmail.setOnClickListener {
            copyText(binding.tvDetailEmail.text.toString())
        }

        //blog아이콘 눌렀을때 연결
        binding.ivBlog.setOnClickListener {
            openLink(binding.tvBlog.text.toString())
        }

        //github아이콘 눌렀을때 연결
        binding.ivGit.setOnClickListener {
            openLink(binding.tvGit.text.toString())
        }
        val firstUser = UserProvider.users.firstOrNull()
        firstUser?.let { user ->
            displayUserInfo(user.name, user.phoneNumber, user.email, user.blogLink, user.githubLink )
        }
    }

    fun displayUserInfo(name: String, phoneNumber: String, email: String, blog:String, github:String) {
        binding.tvName.text = name
        binding.tvNumberPhone.text = phoneNumber
        binding.tvDetailEmail.text = email
        binding.tvBlog.text = blog
        binding.tvGit.text = github
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openTelephone(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
    }

    private fun openMessenger(number: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
        startActivity(intent)
    }

    private fun copyText(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun openLink(text: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
        startActivity(intent)
    }
}
