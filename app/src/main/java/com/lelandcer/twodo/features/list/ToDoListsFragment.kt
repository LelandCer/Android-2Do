package com.lelandcer.twodo.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lelandcer.twodo.databinding.FragmentToDoListsListBinding
import com.lelandcer.twodo.main.ToDoViewModel
import com.lelandcer.twodo.models.PlaceholderContent
import com.lelandcer.twodo.models.list.ToDoList
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ToDoListsFragment : Fragment(), Observer<Collection<ToDoList>> {

    private lateinit var binding: FragmentToDoListsListBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toDoViewModel.toDoLists.observe(this, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoListsListBinding.inflate(inflater, container, false)

        binding.fabTdlNew.setOnClickListener {
            findNavController().navigate(ToDoListsFragmentDirections.actionTwoDoListsFragmentToEditToDoListFragment())
        }
        return binding.root
    }

    override fun onChanged(toDoLists: Collection<ToDoList>?) {
        with(binding.lvTdlList) {
            layoutManager = LinearLayoutManager(context)
            adapter = toDoLists?.toList()?.let { ToDoListRecyclerViewAdapter(it) }
        }
    }
}