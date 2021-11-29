package com.lelandcer.twodo.features.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.databinding.FragmentToDoTasksBinding
import com.lelandcer.twodo.models.task.ToDoTask

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
        holder.deleteButton.setOnClickListener {
            onInteractionListener.onItemDelete(holder.itemView, item)
        }
        holder.itemView.setOnClickListener {
            onInteractionListener.onItemClicked(holder.itemView, item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentToDoTasksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.tvTdtTaskListName
        val deleteButton: Button = binding.btnTdtTaskListDelete

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