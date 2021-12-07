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
    private val values: List<ToDoList>,
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
        val toDoList = getItemAt(position)
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

        when {
            display.isComplete() -> bindColor(holder, colorList.completed)
            display.isLate() -> bindColor(holder, colorList.late)
            display.isSoon() -> bindColor(holder, colorList.soon)
            else -> bindColor(holder, colorList.default)
        }
    }

    private fun bindColor(holder: ToDoListRecyclerViewAdapter.ViewHolder, color: Int) {
        holder.nameView.setTextColor(color)
        holder.completionView.setTextColor(color)
        holder.dueDateView.setTextColor(color)

    }

    override fun getItemId(position: Int): Long {
        return getItemAt(position).id.getKey().hashCode().toLong()
    }

    private fun getItemAt(position: Int): ToDoList {
        return values[position]
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
    // The primary problem was using color resources, which was difficult inside the adapter
    // since it requires context calls. So instead the adapter is given a list of colors to use
    // TODO find a way to abstract this into styles or states
    class ColorList(
        val completed: Int,
        val late: Int,
        val soon: Int,
        val default: Int
    )


}