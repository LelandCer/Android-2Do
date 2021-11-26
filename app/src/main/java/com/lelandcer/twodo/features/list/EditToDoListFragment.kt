package com.lelandcer.twodo.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.lelandcer.twodo.databinding.FragmentEditToDoListBinding
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class EditToDoListFragment : DialogFragment() {

    private lateinit var binding: FragmentEditToDoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditToDoListBinding.inflate(inflater, container, false)

        binding.btnTdlEditCancel.setOnClickListener {
            onCancel()
        }

        binding.btnTdlEditSubmit.setOnClickListener {
            onSubmit()
        }

        binding.btnTdlEditDate.setOnClickListener {
            launchDateSelector()
        }
        return binding.root
    }

    private fun onCancel() {
        findNavController().popBackStack()
    }

    private fun onSubmit() {
        findNavController().popBackStack()
    }

    private fun launchDateSelector() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("When do you want 2 do it?")
                .setInputMode(INPUT_MODE_CALENDAR)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = Date(it)
            var newText = ""
            if(selectedDate < Date()) {
                newText = "Too late now!"
            } else {
                val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                format.timeZone = TimeZone.getTimeZone("UTC")
                newText = format.format(Date(it))
            }

            binding.tvTdlEditSelectedDate.text = newText

        }
        activity?.supportFragmentManager?.let { datePicker.show(it, "") };
    }
}