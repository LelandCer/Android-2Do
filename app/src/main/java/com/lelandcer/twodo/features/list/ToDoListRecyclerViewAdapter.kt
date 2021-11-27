package com.lelandcer.twodo.features.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.databinding.FragmentToDoListsBinding
import com.lelandcer.twodo.models.list.ToDoList
import java.text.SimpleDateFormat
import java.util.*

class ToDoListRecyclerViewAdapter(
    private val values: List<ToDoList>,
    private val toDoListItemClickedListener: OnToDoListItemClickedListener
) : RecyclerView.Adapter<ToDoListRecyclerViewAdapter.ViewHolder>() {

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
        holder.completionView.text = "1/1"
        holder.nameView.text = toDoList.name
        holder.dueDateView.text = SimpleDateFormat("dd/MM", Locale.getDefault()).format(toDoList.dueAt)
        holder.itemView.setOnClickListener{
            toDoListItemClickedListener.onToDoListItemClicked(position, toDoList)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentToDoListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val completionView: TextView = binding.tvTdlCompletionPercentage
        val nameView: TextView = binding.tvTdlName
        val dueDateView: TextView = binding.tvTdlDueAt


    }

    fun interface OnToDoListItemClickedListener {
        fun onToDoListItemClicked(pos: Int, toDoList: ToDoList)
    }

}