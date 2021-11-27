package com.lelandcer.twodo.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.lelandcer.twodo.R
import com.lelandcer.twodo.databinding.FragmentEditToDoListBinding
import com.lelandcer.twodo.main.ToDoViewModel
import com.lelandcer.twodo.models.list.ToDoList
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

/** Fragment for creating and editing a ToDoList */
@AndroidEntryPoint
class EditToDoListFragment : DialogFragment(), Observer<ToDoList?> {

    private lateinit var binding: FragmentEditToDoListBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditToDoListBinding.inflate(inflater, container, false)
        bindClickListeners()
        toDoViewModel.currentToDoList.observe(this, this)

        return binding.root
    }

    private fun bindClickListeners() {
        binding.btnTdlEditCancel.setOnClickListener {
            onCancel()
        }

        binding.btnTdlEditSubmit.setOnClickListener {
            onSubmit()
        }

        binding.btnTdlEditDate.setOnClickListener {
            launchDateSelector()
        }
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
                .setTitleText(getString(R.string.tdl_edit_when_picker_title))
                .setInputMode(INPUT_MODE_CALENDAR)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            binding.tvTdlEditSelectedDate.text = dateToString(Date(it))

        }
        activity?.supportFragmentManager?.let { datePicker.show(it, "") }
    }

    override fun onChanged(toDoList: ToDoList?) {
        toDoList?.let {
            bindList(toDoList)
        }
    }

    private fun bindList(toDoList: ToDoList) {
        binding.etTdlEditName.setText(toDoList.name)
        binding.tvTdlEditSelectedDate.text = dateToString(toDoList.dueAt)

    }

    private fun dateToString(date: Date): String {
        val newText: String
        if (date < getDateForToday()) {
            newText = getString(R.string.tdl_due_late)
        } else {
            val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            newText = format.format(date)
        }

        return newText
    }

    private fun getDateForToday(): Date? {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.time
    }
}