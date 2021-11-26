package com.lelandcer.twodo.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lelandcer.twodo.R
import com.lelandcer.twodo.databinding.FragmentToDoListsBinding
import com.lelandcer.twodo.databinding.FragmentToDoListsListBinding
import com.lelandcer.twodo.models.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ToDoListsFragment : Fragment() {

    private lateinit var binding: FragmentToDoListsListBinding

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoListsListBinding.inflate(inflater, container, false)
        // Set the adapter
        with(binding.lvTdlList) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = ToDoListRecyclerViewAdapter(PlaceholderContent.ITEMS)
        }

        binding.fabTdlNew.setOnClickListener {
            findNavController().navigate(ToDoListsFragmentDirections.actionTwoDoListsFragmentToEditToDoListFragment())
        }
        return binding.root
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ToDoListsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}