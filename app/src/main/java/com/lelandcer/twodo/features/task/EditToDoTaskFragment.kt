package com.lelandcer.twodo.features.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lelandcer.twodo.R
import com.lelandcer.twodo.databinding.FragmentEditToDoTaskBinding
import com.lelandcer.twodo.main.ToDoViewModel
import com.lelandcer.twodo.models.task.ToDoTask

class EditToDoTaskFragment : DialogFragment(), Observer<ToDoTask?> {

    private lateinit var binding: FragmentEditToDoTaskBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()
    private var taskForm = TaskForm()
    private lateinit var toDoTask: ToDoTask


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditToDoTaskBinding.inflate(inflater, container, false)
        toDoViewModel.currentToDoTask.observe(viewLifecycleOwner, this)
        bindInteractionListeners()
        return binding.root
    }

    private fun bindInteractionListeners() {
        binding.btnTdtEditCancel.setOnClickListener {
            onCancel()
        }

        binding.btnTdtEditSubmit.setOnClickListener {
            onSubmit()
        }

        binding.etTdtEditName.doAfterTextChanged {
            taskForm.name = it.toString()
        }
    }

    private fun onCancel() {
        findNavController().popBackStack()
    }

    private fun onSubmit() {
        if (!taskForm.validate()) return
        toDoTask.name = taskForm.name
        toDoViewModel.saveCurrentTask()

        findNavController().popBackStack()
    }

    override fun onChanged(t: ToDoTask?) {
        t?.let { bindTask(it) }
    }

    private fun bindTask(toDoTask: ToDoTask) {
        this.toDoTask = toDoTask
        taskForm.name = toDoTask.name
        bindForm(taskForm)
    }

    private fun bindForm(taskForm: TaskForm) {
        binding.etTdtEditName.setText(taskForm.name)

    }

    private inner class TaskForm(var name: String = "") {

        fun validate(): Boolean {
            if (name.isBlank()) {
                binding.etTdtEditName.error = getString(R.string.tdt_edit_validate_name)
                return false
            }
            return true
        }
    }

}