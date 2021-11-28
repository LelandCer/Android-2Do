package com.lelandcer.twodo.features.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lelandcer.twodo.databinding.FragmentToDoTasksListBinding
import com.lelandcer.twodo.models.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ToDoTasksFragment : Fragment() {

    private lateinit var binding: FragmentToDoTasksListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoTasksListBinding.inflate(inflater, container, false)

        // Set the adapter
        with(binding.rvTdtTasks) {
            layoutManager = LinearLayoutManager(context)
            adapter = ToDoTaskRecyclerViewAdapter(PlaceholderContent.ITEMS)
        }
        return binding.root
    }
    
}