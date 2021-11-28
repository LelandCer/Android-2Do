package com.lelandcer.twodo.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
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
import java.util.*
import javax.inject.Inject

/** Fragment for creating and editing a ToDoList */
@AndroidEntryPoint
class EditToDoListFragment : DialogFragment(), Observer<ToDoList?> {

    private lateinit var binding: FragmentEditToDoListBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()

    @Inject
    lateinit var toDoListDisplay: ToDoListDisplay
    private lateinit var toDoList: ToDoList
    private val listForm = ListForm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditToDoListBinding.inflate(inflater, container, false)
        bindInteractionListeners()
        toDoViewModel.currentToDoList.observe(viewLifecycleOwner, this)

        return binding.root
    }

    private fun bindInteractionListeners() {
        binding.btnTdlEditCancel.setOnClickListener {
            onCancel()
        }

        binding.btnTdlEditSubmit.setOnClickListener {
            onSubmit()
        }

        binding.btnTdlEditDate.setOnClickListener {
            launchDateSelector()
        }

        binding.etTdlEditName.doAfterTextChanged {
            listForm.name = it.toString()
        }
    }

    private fun onCancel() {
        findNavController().popBackStack()
    }

    private fun onSubmit() {

        toDoList.name = listForm.name
        toDoList.dueAt = listForm.dueAt
        toDoViewModel.saveCurrentList()

        findNavController().popBackStack()
    }

    private fun launchDateSelector() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.tdl_edit_when_picker_title))
                .setInputMode(INPUT_MODE_CALENDAR)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = convertDateToLocal(it)
            listForm.dueAt = selectedDate
            bindForm(listForm)
        }
        activity?.supportFragmentManager?.let { datePicker.show(it, "") }
    }

    private fun convertDateToLocal(it: Long): Date {
        //The MaterialDatePicker always returns the utc representation in millis
        // We only want the "Day of the month/year" which means different user timezones often get an
        // off by one error.

        // Issue: https://github.com/material-components/material-components-android/issues/1468

        // For now I just use the calendar to get the day in "UTC" since that's what the user selected
        // And then set it to a calendar instance for the devices local tz
        val calLocal = Calendar.getInstance()
        val calUTC = Calendar.getInstance(TimeZone.getTimeZone("utc"))
        calUTC.time = Date(it)

        calLocal[Calendar.DAY_OF_YEAR] = calUTC[Calendar.DAY_OF_YEAR]
        calLocal[Calendar.HOUR_OF_DAY] = 0
        return calLocal.time
    }

    override fun onChanged(toDoList: ToDoList?) {
        toDoList?.let {
            this.toDoList = it
            bindList(toDoList)
        }
    }

    private fun bindList(toDoList: ToDoList) {
        listForm.name = toDoList.name
        listForm.dueAt = toDoList.dueAt
        bindForm(listForm)
    }

    private fun bindForm(listForm: ListForm) {
        val display = toDoListDisplay.forToDoList(toDoList)
        binding.etTdlEditName.setText(listForm.name)
        binding.tvTdlEditSelectedDate.text = display.formatDate(listForm.dueAt)
    }

    private class ListForm(var name: String = "", var dueAt: Date = Date()) {

        fun validate():Boolean {
            // TODO validate the form data
            return true
        }
    }

}