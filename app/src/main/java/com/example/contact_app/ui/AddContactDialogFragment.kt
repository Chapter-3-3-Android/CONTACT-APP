package com.example.contact_app.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.contact_app.R
import com.example.contact_app.databinding.FragmentAddContactDialogBinding
import com.example.contact_app.data.model.Image
import com.example.contact_app.data.model.User
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.extension.ButtonClickListener
import com.example.contact_app.extension.ValidExtension.validEmail
import com.example.contact_app.extension.ValidExtension.validName
import com.example.contact_app.extension.ValidExtension.validPhoneNumber

class AddContactDialogFragment(private val buttonClickListener: ButtonClickListener) : DialogFragment() {
    private lateinit var binding: FragmentAddContactDialogBinding
    private lateinit var galleryResultLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null
    private var image: Image? = null
    private val editTexts
        get() = with(binding) {
            listOf(
                etName,
                etNumber,
                etEmail
            )
        }
    private var nameEnable = false
    private var phoneNumberEnable = false
    private var emailEnable = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        binding = FragmentAddContactDialogBinding.inflate(inflater)

        builder.setView(binding.root)
        with(binding) {
            tvDialogTitle.setText("Add Contact")
            btnNegative.setOnClickListener {
                dismiss()
            }
            btnPositive.setOnClickListener {
                addNewUser()
                buttonClickListener.onSaveButtonClick()
                dismiss()
            }
            ivProfile.clipToOutline = true
            ivProfile.setOnClickListener {
                openGallery()
            }
        }
        galleryResultLauncher()

        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                editText.checkValidElements()
                isConfirmButtonEnable()
            }
        }

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun galleryResultLauncher() {
        galleryResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uriData: Intent? = result.data
                uriData?.data?.let {
                    selectedImageUri = it
                    binding.ivProfile.setImageURI(selectedImageUri)
                    image = Image.ImageUri(selectedImageUri!!)
                    isConfirmButtonEnable()
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResultLauncher.launch(intent)
    }

    private fun checkValidName(text: String) {
        nameEnable = text.validName()
        binding.tvNameWarning.visibility = if (nameEnable) View.GONE else View.VISIBLE
    }

    private fun checkValidPhoneNumber(text: String) {
        phoneNumberEnable = text.validPhoneNumber()
        binding.tvNumberWarning.visibility = if (phoneNumberEnable) View.GONE else View.VISIBLE
    }

    private fun checkValidEmail(text: String) {
        emailEnable = text.validEmail()
        binding.tvEmailWarning.visibility = if (emailEnable) View.GONE else View.VISIBLE
    }

    private fun EditText.checkValidElements() = with(binding) {
        when (this@checkValidElements) {
            etName -> checkValidName(etName.text.toString())
            etNumber -> checkValidPhoneNumber(etNumber.text.toString())
            etEmail -> checkValidEmail(etEmail.text.toString())

            else -> Unit
        }
    }

    private fun isConfirmButtonEnable() {
        with(binding) {
            btnPositive.isEnabled = nameEnable && phoneNumberEnable && emailEnable
            if(btnPositive.isEnabled){
                btnPositive.setBackgroundResource(R.drawable.shape_dialog_btn_dark_clicked)
            } else {
                btnPositive.setBackgroundResource(R.drawable.shape_dialog_btn_dark)
            }
        }
    }

    private fun addNewUser() {
        val name = binding.etName.text.toString()
        val phoneNumber = binding.etNumber.text.toString()
        val email = binding.etEmail.text.toString()

        val newUser = User(
            name = name,
            phoneNumber = phoneNumber,
            profileImage = image ?: Image.ImageDrawable(R.drawable.ic_profile),
            email = email
        )
        UserProvider.addUser(newUser)
    }
}