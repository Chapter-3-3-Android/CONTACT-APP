package com.example.contact_app.ui.contact

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
import com.example.contact_app.databinding.FragmentContactBinding
import com.example.contact_app.extension.ButtonClickListener
import com.example.contact_app.ui.dialog.AddScheduleDialogFragment

class MyPageFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivAddSchedule.setOnClickListener {
                val dialog = AddScheduleDialogFragment(
                    userIndex = 0,
                    buttonClickListener = object: ButtonClickListener {
                        override fun onSaveButtonClick() {

                        }
                    }
                )
                dialog.show(parentFragmentManager, "AddScheduleDialog")
            }

            // 전화아이콘, 문자아이콘, 비디오아이콘 GONE
            imgTelephone.visibility = View.GONE
            imgSms.visibility = View.GONE
            imgVideo.visibility = View.GONE

            // copy 버튼 눌렀을때 복사(전화번호)
            tvCopyPhone.setOnClickListener {
                copyText(tvNumberPhone.text.toString())
            }
            // copy 버튼 눌렀을때 복사(이메일)
            tvCopyEmail.setOnClickListener {
                copyText(tvDetailEmail.text.toString())
            }
            // blog아이콘 눌렀을때 연결
            ivBlog.setOnClickListener {
                openLink(tvBlog.text.toString())
            }
            // github아이콘 눌렀을때 연결
            ivGit.setOnClickListener {
                openLink(tvGit.text.toString())
            }
        }
        val firstUser = UserProvider.users.firstOrNull()
        firstUser?.let { user ->
            displayUserInfo(user.name, user.phoneNumber, user.email, user.blogLink, user.githubLink)
        }
    }

    fun displayUserInfo(
        name: String,
        phoneNumber: String,
        email: String,
        blogLink: String?,
        githubLink: String?,
    ) {
        binding.tvName.text = name
        binding.tvNumberPhone.text = phoneNumber
        binding.tvDetailEmail.text = email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun copyText(text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun openLink(text: String) {
        if (text != "There is no blog link" && text != "There is no github link") {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
            startActivity(intent)
        }
    }
}