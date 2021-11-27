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
        /** TODO extract these strings into resources */
        /** TODO fix the "Today" can't be selected bug */
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("When do you want 2 do it?")
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
        if (date < Date()) {
            newText = "Too late now!"
        } else {
            val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            newText = format.format(date)
        }

        return newText
    }
}