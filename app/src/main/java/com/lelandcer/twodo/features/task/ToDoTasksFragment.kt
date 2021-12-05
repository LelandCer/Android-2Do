package com.lelandcer.twodo.features.task

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.R
import com.lelandcer.twodo.databinding.FragmentToDoTasksListBinding
import com.lelandcer.twodo.features.list.ToDoListDisplay
import com.lelandcer.twodo.main.FabActivity
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
    ): View {
        binding = FragmentToDoTasksListBinding.inflate(inflater, container, false)
        with(binding.rvTdtTasks) {
            layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration =
                DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(
                AppCompatResources.getDrawable(
                    this.context,
                    R.drawable.blue_divider
                )!!
            )
            addItemDecoration(dividerItemDecoration)
            adapter = ToDoTaskRecyclerViewAdapter(
                toDoTaskItems,
                this@ToDoTasksFragment
            )

            // Add a touch helper to slide delete a task
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //Note we remove it from the adapter's list first to avoid a visual delay
                    val task = toDoTaskItems.removeAt(viewHolder.absoluteAdapterPosition)
                    toDoViewModel.deleteTask(task)
                    adapter?.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                }
            }).attachToRecyclerView(this)
        }
        toDoViewModel.currentToDoList.observe(viewLifecycleOwner, this)
        return binding.root
    }

    override fun onChanged(toDoList: ToDoList?) {
        toDoList?.let { bindToView(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bindToView(toDoList: ToDoList) {
        val display = toDoListDisplay.forToDoList(toDoList)

        val actionbar = (activity as AppCompatActivity?)?.supportActionBar
        actionbar?.let {
            //if we have an actionbar put the title there, otherwise use the in-fragment name textview
            actionbar.title = display.name()
            binding.tvTdtName.visibility = View.INVISIBLE

        }

        // Bind the display provided strings
        binding.tvTdtName.text = display.name()
        binding.tvTdtCompletion.text = display.completionRatio()
        binding.tvTdtDueAt.text = display.dueAt()
        binding.tvTdtDueAtFormatted.text = display.formatDate(toDoList.dueAt)

        // Set the adapter items to the fresh set
        toDoTaskItems.clear()
        toDoTaskItems.addAll(toDoList.toDoTasks.sortedBy { tdt -> tdt.createdAt })
        binding.rvTdtTasks.adapter?.notifyDataSetChanged()

        // Show the empty state if empty
        if (toDoTaskItems.isEmpty()) {
            binding.rvTdtTasks.visibility = View.GONE
            binding.vTdtEmpty.visibility = View.VISIBLE
        } else {
            binding.rvTdtTasks.visibility = View.VISIBLE
            binding.vTdtEmpty.visibility = View.GONE
        }

        // Bind interaction listeners
        binding.btnTdtTaskDelete.setOnClickListener {
            toDoViewModel.deleteList(toDoList)
            findNavController().navigateUp()
        }
        binding.btnTdtTaskEdit.setOnClickListener {
            val action = ToDoTasksFragmentDirections.actionToDoTasksFragmentToEditToDoListFragment()
            findNavController().navigate(action)
        }
        (activity as FabActivity?)?.fab?.setOnClickListener {
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