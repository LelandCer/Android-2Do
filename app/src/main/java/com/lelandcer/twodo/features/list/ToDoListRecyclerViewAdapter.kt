package com.lelandcer.twodo.features.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.databinding.FragmentToDoListsBinding
import com.lelandcer.twodo.models.list.ToDoList

/** Adapter for the ToDoLists Items */
class ToDoListRecyclerViewAdapter(
    private var values: List<ToDoList>,
    private val onInteractionListener: OnInteractionListener,
    private val toDoListDisplay: ToDoListDisplay,
    private val colorList: ColorList
) : RecyclerView.Adapter<ToDoListRecyclerViewAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentToDoListsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDoList = values[position]
        val display = toDoListDisplay.forToDoList(toDoList)
        holder.completionView.text = display.completionRatio()
        holder.nameView.text = display.name()

        // With slide delete implemented, hide the button. Flag for removal
        holder.deleteButton.isGone = true
        holder.dueDateView.text =
            display.dueAt()
        holder.itemView.setOnClickListener {
            onInteractionListener.onItemClicked(position, toDoList)
        }

        holder.deleteButton.setOnClickListener {
            onInteractionListener.onItemDelete(it, toDoList)
        }

        if (display.isComplete()) bindColor(holder, colorList.completed)
        else if (display.isLate()) bindColor(holder, colorList.late)
        else if (display.isSoon()) bindColor(holder, colorList.soon)
    }

    private fun bindColor(holder: ToDoListRecyclerViewAdapter.ViewHolder, color: Int) {
        holder.nameView.setTextColor(color)
        holder.completionView.setTextColor(color)
        holder.dueDateView.setTextColor(color)

    }

    override fun getItemId(position: Int): Long {
        return values[position].id.getKey().hashCode().toLong()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentToDoListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val completionView: TextView = binding.tvTdlCompletionPercentage
        val nameView: TextView = binding.tvTdlName
        val dueDateView: TextView = binding.tvTdlDueAt
        val deleteButton: Button = binding.btnTdlDelete


    }

    interface OnInteractionListener {
        fun onItemClicked(position: Int, list: ToDoList)
        fun onItemDelete(view: View, list: ToDoList)
    }

    // This is the most straightforward solution for now
    // TODO find a way to abstract this into styles or states
    class ColorList(
        val completed: Int,
        val late: Int,
        val soon: Int
    )


}