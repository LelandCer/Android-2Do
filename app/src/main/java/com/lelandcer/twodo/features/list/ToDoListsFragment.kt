package com.lelandcer.twodo.features.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.R
import com.lelandcer.twodo.databinding.FragmentToDoListsListBinding
import com.lelandcer.twodo.main.FabActivity
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
    ): View {
        binding = FragmentToDoListsListBinding.inflate(inflater, container, false)


        (activity as FabActivity?)?.fab?.setOnClickListener {
            toDoViewModel.setNewCurrentList()
            findNavController().navigate(ToDoListsFragmentDirections.actionToDoListsFragmentToEditToDoListFragment())
        }

        // Set up the RecyclerView
        with(binding.rvTdlList) {
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
            adapter =
                ToDoListRecyclerViewAdapter(
                    toDoListItems,
                    this@ToDoListsFragment,
                    toDoListDisplay,
                    ToDoListRecyclerViewAdapter.ColorList(
                        ContextCompat.getColor(context, R.color.list_completed),
                        ContextCompat.getColor(context, R.color.list_failed),
                        ContextCompat.getColor(context, R.color.list_soon),
                        ContextCompat.getColor(context, R.color.list_default),
                    )
                )

            // Add a touch helper to slide delete a list
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //Note we remove it from the adapter's list first to avoid a delay
                    val list = toDoListItems.removeAt(viewHolder.absoluteAdapterPosition)
                    toDoViewModel.deleteList(list)
                    adapter?.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                }
            }).attachToRecyclerView(this)


        }
        toDoViewModel.toDoLists.observe(viewLifecycleOwner, this)

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onChanged(toDoLists: Collection<ToDoList>?) {
        toDoLists?.let {

            // Todo move to state handling
            if (it.isEmpty()) {
                binding.rvTdlList.visibility = View.GONE
                binding.vTdlEmpty.visibility = View.VISIBLE
            } else {
                binding.rvTdlList.visibility = View.VISIBLE
                binding.vTdlEmpty.visibility = View.GONE
            }
            toDoListItems.clear()
            toDoListItems.addAll(it.sortedBy { tdl -> tdl.dueAt })

            // Right now I'm passing in a new list every time, so understanding
            // what changed would take more than a notify all call would
            binding.rvTdlList.adapter?.notifyDataSetChanged()
        }
    }

    override fun onItemClicked(position: Int, list: ToDoList) {
        toDoViewModel.setCurrentList(list)
        findNavController().navigate(ToDoListsFragmentDirections.actionToDoListsFragmentToToDoTasksFragment())
    }

    override fun onItemDelete(view: View, list: ToDoList) {
        // TODO require confirmation or allow undo
        toDoViewModel.deleteList(list)

    }
}