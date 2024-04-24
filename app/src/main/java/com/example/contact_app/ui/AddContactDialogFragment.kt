package com.example.contact_app.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.contact_app.extension.ValidExtension.validEmail
import com.example.contact_app.extension.ValidExtension.validName
import com.example.contact_app.extension.ValidExtension.validPhoneNumber
import com.example.contact_app.data.model.Image
import com.example.contact_app.data.model.User
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentAddContactDialogBinding
import com.example.contact_app.extension.ButtonClickListener

class AddContactDialogFragment(private val buttonClickListener: ButtonClickListener) : DialogFragment() {
    private lateinit var binding: FragmentAddContactDialogBinding
    private lateinit var galleryResultLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null
    private lateinit var image: Image
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        galleryResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uriData: Intent? = result.data
                uriData?.data?.let {
                    selectedImageUri = it
                    binding.ivProfile.setImageURI(selectedImageUri)
                    image = Image.ImageUri(selectedImageUri!!)
                }

                isConfirmButtonEnable()
            }
        }
        binding.ivProfile.clipToOutline = true

        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                editText.checkValidElements()
            }
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus.not()) {
                    isConfirmButtonEnable()
                }
            }
        }
        onClick()
    }

    private fun onClick() {
        binding.ivProfile.setOnClickListener {
            openGallery()
        }

        binding.btnNegative.setOnClickListener {
            dismiss()
        }

        binding.btnPositive.setOnClickListener {
            addNewUser()
            buttonClickListener.onSaveButtonClick()
            dismiss()
        }
    }

    private fun openGallery() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResultLauncher.launch(intent)
    }

    private fun checkValidName(text: String) {
        when {
            text.validName() -> {
                binding.tvNameWarning.visibility = View.INVISIBLE
                nameEnable = true
            }
            else -> {
                binding.tvNameWarning.visibility = View.VISIBLE
                nameEnable = true
            }
        }
    }

    private fun checkValidPhoneNumber(text: String) {
        when {
            text.validPhoneNumber() -> {
                binding.tvNumberWarning.visibility = View.INVISIBLE
                phoneNumberEnable = true
            }
            else -> {
                binding.tvNameWarning.visibility = View.VISIBLE
                phoneNumberEnable = false
            }
        }
    }

    private fun checkValidEmail(text: String) {
        when {
            text.validEmail() -> {
                binding.tvEmailWarning.visibility = View.INVISIBLE
                nameEnable = true
            }
            else -> {
                binding.tvNameWarning.visibility = View.VISIBLE
                nameEnable = false
            }
        }
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
        binding.btnPositive.isEnabled = image != null && nameEnable && phoneNumberEnable && emailEnable
    }

    private fun addNewUser() {
        val name = binding.etName.text.toString()
        val phoneNumber = binding.etNumber.text.toString()
        val email = binding.etEmail.text.toString()

        val newUser = User(
            name,
            phoneNumber,
            image,
            email,
        )
        UserProvider.addUser(newUser)
    }
}