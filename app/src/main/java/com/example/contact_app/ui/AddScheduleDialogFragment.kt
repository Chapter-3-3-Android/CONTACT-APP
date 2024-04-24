package com.example.contact_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.contact_app.data.model.Schedule
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentAddScheduleDialogBinding
import com.example.contact_app.extension.ButtonClickListener
import com.example.contact_app.extension.ValidExtension.validDate
import com.example.contact_app.extension.ValidExtension.validRemindTime

class AddScheduleDialogFragment(private val userIndex: Int, private val buttonClickListener: ButtonClickListener) : DialogFragment() {
    private lateinit var binding: FragmentAddScheduleDialogBinding
    private val editTexts
        get() = with(binding) {
            listOf(
                etName,
                etDate,
                etRemindTime
            )
        }
    private var dateEnable = false
    private var remindTimeEnable = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddScheduleDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
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
        binding.btnNegative.setOnClickListener {
            dismiss()
        }

        binding.btnPositive.setOnClickListener {
            addSchedule()
            buttonClickListener.onSaveButtonClick()
            dismiss()
        }
    }

    private fun checkValidDate(text: String) {
        when {
            text.validDate() -> {
                binding.tvDateWarning.visibility = View.INVISIBLE
                remindTimeEnable = true
            }
            else -> {
                binding.tvDateWarning.visibility = View.VISIBLE
                remindTimeEnable = false
            }
        }
    }

    private fun checkValidRemindTime(text: String) {
        when {
            text.validRemindTime() -> {
                binding.tvRemindTimeWarning.visibility = View.INVISIBLE
                remindTimeEnable = true
            }
            else -> {
                binding.tvRemindTimeWarning.visibility = View.VISIBLE
                remindTimeEnable = false
            }
        }
    }

    private fun EditText.checkValidElements() = with(binding) {
        when (this@checkValidElements) {
            etDate -> checkValidDate(etDate.text.toString())
            etRemindTime -> checkValidRemindTime(etRemindTime.text.toString())

            else -> Unit
        }
    }

    private fun isConfirmButtonEnable() {
        binding.btnPositive.isEnabled = binding.etName.text.toString().isNotBlank()
                && dateEnable
    }

    private fun addSchedule() {
        val name = binding.etName.text.toString()
        val date = binding.etDate.text.toString()
        val remindTime = binding.etRemindTime.text.toString()

        val newSchedule = Schedule(
            name,
            date,
            remindTime.toInt(),
        )
        UserProvider.addSchedule(userIndex, newSchedule)
    }
}