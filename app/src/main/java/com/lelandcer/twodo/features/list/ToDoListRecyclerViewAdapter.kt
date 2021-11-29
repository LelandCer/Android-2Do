package com.lelandcer.twodo.features.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.databinding.FragmentToDoListsBinding
import com.lelandcer.twodo.models.list.ToDoList

class ToDoListRecyclerViewAdapter(
    private var values: List<ToDoList>,
    private val onInteractionListener: OnInteractionListener,
    private val toDoListDisplay: ToDoListDisplay
) : RecyclerView.Adapter<ToDoListRecyclerViewAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)

    }


   fun setValues (values: List<ToDoList>) {
       this.values = values
        notifyDataSetChanged()
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
        holder.dueDateView.text =
            display.dueAt() //SimpleDateFormat("dd/MM", Locale.getDefault()).format(toDoList.dueAt)
        holder.itemView.setOnClickListener {
            onInteractionListener.onItemClicked(position, toDoList)
        }

        holder.deleteButton.setOnClickListener {
            onInteractionListener.onItemDelete(it, toDoList)
        }
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
        fun onItemDelete(view: View, list:ToDoList)
    }

}