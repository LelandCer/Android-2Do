package com.lelandcer.twodo.features.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lelandcer.twodo.R
import com.lelandcer.twodo.databinding.FragmentToDoTasksListBinding
import com.lelandcer.twodo.features.list.ToDoListDisplay
import com.lelandcer.twodo.main.MainActivity
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
    private val toDoTaskItems: MutableList<ToDoTask> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoTasksListBinding.inflate(inflater, container, false)
        with(binding.rvTdtTasks) {
            layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration =
                DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(this.context, R.drawable.blue_divider)!!)
            addItemDecoration(dividerItemDecoration)
            adapter = ToDoTaskRecyclerViewAdapter(
                toDoTaskItems,
                this@ToDoTasksFragment
            )
        }
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
        binding.tvTdtDueAtFormatted.text = display.formatDate(toDoList.dueAt)

        toDoTaskItems.clear()
        toDoTaskItems.addAll(toDoList.toDoTasks.sortedBy { tdt -> tdt.createdAt })
        binding.rvTdtTasks.adapter?.notifyDataSetChanged()

        if (toDoTaskItems.isEmpty()) {
            binding.rvTdtTasks.visibility = View.GONE
            binding.vTdtEmpty.visibility = View.VISIBLE
        } else {
            binding.rvTdtTasks.visibility = View.VISIBLE
            binding.vTdtEmpty.visibility = View.GONE
        }

        binding.btnTdtTaskDelete.setOnClickListener {
            toDoViewModel.deleteList(toDoList)
            findNavController().navigateUp()
        }
        binding.btnTdtTaskEdit.setOnClickListener {
            val action = ToDoTasksFragmentDirections.actionToDoTasksFragmentToEditToDoListFragment()
            findNavController().navigate(action)
        }
        (activity as MainActivity?)?.fab?.setOnClickListener {
            toDoViewModel.setNewCurrentTask()
            val action = ToDoTasksFragmentDirections.actionToDoTasksFragmentToEditToDoTaskFragment()
            findNavController().navigate(action)
        }

    }

    override fun onItemClicked(view: View, task: ToDoTask) {
        toDoViewModel.setCurrentTask(task)
        if (!task.isCompleted) task.complete() else task.unComplete()
        toDoViewModel.saveCurrentTask()
    }

    override fun onItemDelete(view: View, task: ToDoTask) {
        toDoViewModel.deleteTask(task)
    }

}