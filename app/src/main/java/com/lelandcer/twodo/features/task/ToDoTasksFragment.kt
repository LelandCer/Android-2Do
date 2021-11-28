package com.lelandcer.twodo.features.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lelandcer.twodo.databinding.FragmentToDoTasksListBinding
import com.lelandcer.twodo.features.list.ToDoListDisplay
import com.lelandcer.twodo.main.ToDoViewModel
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ToDoTasksFragment : Fragment(), Observer<ToDoList?>,
    ToDoTaskRecyclerViewAdapter.OnInteractionListener {

    @Inject
    lateinit var toDoListDisplay: ToDoListDisplay
    private lateinit var binding: FragmentToDoTasksListBinding
    private val toDoViewModel: ToDoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoTasksListBinding.inflate(inflater, container, false)

        toDoViewModel.currentToDoList.observe(viewLifecycleOwner, this)
        return binding.root
    }

    override fun onChanged(toDoList: ToDoList?) {
        toDoList?.let { bindToView(it) }
    }

    private fun bindToView(toDoList: ToDoList) {
        val display = toDoListDisplay.forToDoList(toDoList)

        val actionbar = (activity as AppCompatActivity?)?.supportActionBar
        actionbar?.let {
            //if we have an actionbar put the title there, otherwise use the in-fragment name textview
            actionbar.title = display.name()
            binding.tvTdtName.visibility = View.INVISIBLE

        }

        binding.tvTdtName.text = display.name()
        binding.tvTdtCompletion.text = display.completionRatio()
        binding.tvTdtDueAt.text = display.dueAt()
        binding.tvTdtDueAtFormatted.text = display.dueAtDateFormat()

        // Set the adapter
        with(binding.rvTdtTasks) {
            layoutManager = LinearLayoutManager(context)
            adapter = ToDoTaskRecyclerViewAdapter(
                toDoList.toDoTasks.toList(),
                this@ToDoTasksFragment
            )
        }

    }

    override fun onItemClicked(view: View, task: ToDoTask) {
        TODO("Not yet implemented")
    }

    override fun onItemDelete(view: View, task: ToDoTask) {
        TODO("Not yet implemented")
    }

}