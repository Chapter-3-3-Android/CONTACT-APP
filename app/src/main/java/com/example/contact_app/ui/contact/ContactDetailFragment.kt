package com.example.contact_app.ui.contact

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.contact_app.R
import com.example.contact_app.data.model.Image
import com.example.contact_app.data.model.User
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentContactBinding
import com.example.contact_app.extension.ButtonClickListener
import com.example.contact_app.ui.dialog.AddScheduleDialogFragment

private const val ARG_PARAM1 = "position"

class ContactDetailFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //키값으로 데이터 받아옴
        arguments?.let {
            position = it.getInt(ARG_PARAM1)
        }
        if (position == null) {
            position = 0
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivAddSchedule.setOnClickListener {
            val dialog = AddScheduleDialogFragment(
                userIndex = 0,
                buttonClickListener = object: ButtonClickListener {
                    override fun onSaveButtonClick() {

                    }
                }
            )
            dialog.show(parentFragmentManager, "AddScheduleDialog")
        }

        //전달받은 position 데이터값 ui에 binding
        position?.let {
            val detailData: User = UserProvider.users[position!!]

            val image = detailData.profileImage
            binding.apply {
                tvName.text = detailData.name
                tvNumberPhone.text = detailData.phoneNumber

                when (image) {
                    is Image.ImageDrawable -> imgSheep.setImageResource(image.drawable)
                    is Image.ImageUri -> imgSheep.setImageURI(image.uri)
                }
                tvDetailEmail.text = detailData.email
            }
        }

        val popupMenu = PopupMenu(requireContext(), binding.imgOption)
        popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_correction -> {
                    val builder = AlertDialog.Builder(requireContext())

                    builder.setTitle("프로필 수정")
                    builder.setMessage("수정할 내용을 입력해 주세요")
                    builder.setIcon(R.mipmap.ic_launcher)

                    val listener = object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> position?.let {
                                    // UserProvider.modifyUser(it)
                                }
                            }
                        }
                    }

                    builder.setPositiveButton("확인", listener)
                    builder.setNegativeButton("취소", listener)
                    builder.show()

                    true
                }

                R.id.action_delete -> {
                    //dialog 띄우고 확인 누르면 삭제 기능 구현 추가
                    val builder = AlertDialog.Builder(requireContext())

                    builder.setTitle("프로필 삭제")
                    builder.setMessage("정말 삭제하시겠습니까?")
                    builder.setIcon(R.mipmap.ic_launcher)

                    val listener = object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> position?.let {
                                    // UserProvider.deleteUser(it)
                                }
                            }
                        }
                    }

                    builder.setPositiveButton("확인", listener)
                    builder.setNegativeButton("취소", listener)
                    builder.show()

                    true
                }

                else -> false
            }
        }

        with(binding) {
            imgOption.setOnClickListener {
                popupMenu.show()
            }
            // 전화 아이콘 눌렀을때 연결
            imgTelephone.setOnClickListener {
                openTelephone(tvNumberPhone.text.toString())
            }
            // 문자 아이콘 눌렀을때 연결
            imgSms.setOnClickListener {
                openMessenger(tvNumberPhone.text.toString())
            }
            // copy 버튼 눌렀을때 복사 (전화번호)
            tvCopyPhone.setOnClickListener {
                copyText(tvNumberPhone.text.toString())
            }
            // copy 버튼 눌렀을때 복사 (이메일)
            tvCopyEmail.setOnClickListener {
                copyText(tvDetailEmail.text.toString())
            }
            // blog 아이콘 눌렀을때 연결
            ivBlog.setOnClickListener {
                val link = UserProvider.users[position!!].blogLink
                openLink(link ?: "")
            }
            // github 아이콘 눌렀을때 연결
            ivGit.setOnClickListener {
                val link = UserProvider.users[position!!].githubLink
                openLink(link ?: "")
            }
        }
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
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun openLink(text: String) {
        if (text == "") {
            return
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
        startActivity(intent)
    }

    companion object {
        fun newInstance(position: Int): ContactDetailFragment =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, position)
                }
            }
    }
}