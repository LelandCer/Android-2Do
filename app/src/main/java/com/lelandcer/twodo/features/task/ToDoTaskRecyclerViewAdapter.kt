package com.lelandcer.twodo.features.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.databinding.FragmentToDoTasksBinding
import com.lelandcer.twodo.models.task.ToDoTask

/** An adapter for a ToDoTask list item */
class ToDoTaskRecyclerViewAdapter(
    private val values: List<ToDoTask>,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.Adapter<ToDoTaskRecyclerViewAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentToDoTasksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name

        // With Slide to delete hide the button. Flag for removal
        holder.deleteButton.isGone = true
        holder.deleteButton.setOnClickListener {
            onInteractionListener.onItemDelete(holder.itemView, item)
        }
        holder.itemView.setOnClickListener {
            onInteractionListener.onItemClicked(holder.itemView, item)
        }


        with(holder.completionLineView) {
            if (item.isCompleted) {
                // If showing for the first time animate it
                // Note I think this only works be stable Ids is true so the view gets reused
                if (isVisible) return
                isVisible = true
                StrikeThroughAnimator(this).animate()
            } else {
                // Important, using Gone results in no animation the first time
                isInvisible = true
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentToDoTasksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.tvTdtTaskListName
        val deleteButton: Button = binding.btnTdtTaskListDelete
        val completionLineView: View = binding.vTdtTaskListCompletionLine

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

    override fun getItemId(position: Int): Long {
        return values[position].id.getKey().hashCode().toLong()
    }

    interface OnInteractionListener {
        fun onItemClicked(view: View, task: ToDoTask)
        fun onItemDelete(view: View, task: ToDoTask)
    }

}