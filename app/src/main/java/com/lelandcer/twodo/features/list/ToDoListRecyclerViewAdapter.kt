package com.lelandcer.twodo.features.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.databinding.FragmentToDoListsBinding
import com.lelandcer.twodo.models.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ToDoListRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
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
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentToDoListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.tvTdlCompletionPercentage
        val contentView: TextView = binding.tvTdlName

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}