package com.lelandcer.twodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
    private val toDoTaskRepository: ToDoTaskRepository
) : ViewModel() {
    private val _toDoLists = MutableLiveData<Collection<ToDoList>>()
    private val _currentToDoList = MutableLiveData<ToDoList?>()
    var toDoLists: LiveData<Collection<ToDoList>> = _toDoLists
    var currentToDoList: LiveData<ToDoList?> = _currentToDoList

    init {
        _toDoLists.value = toDoListRepository.index()
    }


}