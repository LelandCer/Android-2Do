package com.lelandcer.twodo.features.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.lelandcer.twodo.databinding.FragmentEditToDoTaskBinding
import com.lelandcer.twodo.main.ToDoViewModel
import com.lelandcer.twodo.models.task.ToDoTask

class EditToDoTaskFragment : DialogFragment(), Observer<ToDoTask?> {

    private lateinit var binding: FragmentEditToDoTaskBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditToDoTaskBinding.inflate(inflater, container, false)
        toDoViewModel.currentToDoTask.observe(viewLifecycleOwner, this)
        return binding.root
    }

    override fun onChanged(t: ToDoTask?) {
        t?.let { bindModelToView(it) }
    }

    private fun bindModelToView(t: ToDoTask) {
        binding.etTdtEditName.setText(t.name)
    }

}