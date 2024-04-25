package com.example.contact_app.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.contact_app.R
import com.example.contact_app.data.model.Schedule
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentAddScheduleDialogBinding
import com.example.contact_app.extension.ButtonClickListener
import com.example.contact_app.extension.ValidExtension.validDate
import com.example.contact_app.extension.ValidExtension.validName
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
    private var nameEnable = false
    private var dateEnable = false
    private var remindTimeEnable = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        binding = FragmentAddScheduleDialogBinding.inflate(inflater)

        builder.setView(binding.root)
        with(binding) {
            tvDialogTitle.setText("Add Schedule")
            btnNegative.setOnClickListener {
                dismiss()
            }
            btnPositive.setOnClickListener {
                addSchedule()
                buttonClickListener.onSaveButtonClick()
                dismiss()
            }
        }

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

    private fun checkValidName(text: String) {
        nameEnable = text.validName()
        binding.tvNameWarning.visibility = if (nameEnable) View.GONE else View.VISIBLE
    }

    private fun checkValidDate(text: String) {
        dateEnable = text.validDate()
        binding.tvDateWarning.visibility = if (dateEnable) View.GONE else View.VISIBLE
    }

    private fun checkValidRemindTime(text: String) {
        remindTimeEnable = text.validRemindTime()
        binding.tvRemindTimeWarning.visibility = if (remindTimeEnable) View.GONE else View.VISIBLE
    }

    private fun EditText.checkValidElements() = with(binding) {
        when (this@checkValidElements) {
            etName -> checkValidName(etName.text.toString())
            etDate -> checkValidDate(etDate.text.toString())
            etRemindTime -> checkValidRemindTime(etRemindTime.text.toString())

            else -> Unit
        }
    }

    private fun isConfirmButtonEnable() {
        with(binding) {
            btnPositive.isEnabled = nameEnable && dateEnable && (remindTimeEnable || etRemindTime.text.isBlank())
            if(btnPositive.isEnabled){
                btnPositive.setBackgroundResource(R.drawable.shape_dialog_btn_dark_clicked)
            } else {
                btnPositive.setBackgroundResource(R.drawable.shape_dialog_btn_dark)
            }
        }
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