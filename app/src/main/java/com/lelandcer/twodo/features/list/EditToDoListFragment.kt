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
import javax.inject.Inject

/** Fragment for creating and editing a ToDoList */
@AndroidEntryPoint
class EditToDoListFragment : DialogFragment(), Observer<ToDoList?> {

    private lateinit var binding: FragmentEditToDoListBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()
    @Inject lateinit var toDoListDisplay: ToDoListDisplay
    private lateinit var toDoList: ToDoList;

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
            toDoList.dueAt = Date(it)
            bindList(toDoList)
            binding.tvTdlEditSelectedDate.text = toDoListDisplay.dueAtDateFormat()

        }
        activity?.supportFragmentManager?.let { datePicker.show(it, "") }
    }

    override fun onChanged(toDoList: ToDoList?) {
        toDoList?.let {
            this.toDoList = it
            bindList(toDoList)
        }
    }

    private fun bindList(toDoList: ToDoList) {
        val display = toDoListDisplay.forToDoLIst(toDoList)
        binding.etTdlEditName.setText(display.name())
        binding.tvTdlEditSelectedDate.text = display.dueAtDateFormat()
    }

}