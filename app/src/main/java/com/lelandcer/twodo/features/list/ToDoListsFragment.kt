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
import com.lelandcer.twodo.models.list.ToDoList
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ToDoListsFragment : Fragment(), Observer<Collection<ToDoList>>,
    ToDoListRecyclerViewAdapter.OnInteractionListener {

    @Inject
    lateinit var toDoListDisplay: ToDoListDisplay
    private lateinit var binding: FragmentToDoListsListBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()
    private val toDoListItems: MutableList<ToDoList> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoListsListBinding.inflate(inflater, container, false)

        binding.fabTdlNew.setOnClickListener {
            toDoViewModel.setNewCurrentList()
            findNavController().navigate(ToDoListsFragmentDirections.actionToDoListsFragmentToEditToDoListFragment())
        }
        with(binding.lvTdlList) {
            layoutManager = LinearLayoutManager(context)
            adapter =
                ToDoListRecyclerViewAdapter(
                    toDoListItems,
                    this@ToDoListsFragment,
                    toDoListDisplay
                )

        }
        toDoViewModel.toDoLists.observe(viewLifecycleOwner, this)

        return binding.root
    }

    override fun onChanged(toDoLists: Collection<ToDoList>?) {
        toDoLists?.let {
            toDoListItems.clear()
            toDoListItems.addAll(it.sortedBy { tdl -> tdl.dueAt })
            binding.lvTdlList.adapter?.notifyDataSetChanged()
        }
    }

    override fun onItemClicked(position: Int, list: ToDoList) {
        toDoViewModel.setCurrentList(list)
        findNavController().navigate(ToDoListsFragmentDirections.actionToDoListsFragmentToToDoTasksFragment())
    }

    override fun onItemDelete(view: View, list: ToDoList) {
        // TODO require confirmation
        toDoViewModel.deleteList(list)

    }
}