package com.lelandcer.twodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
    private val toDoTaskRepository: ToDoTaskRepository
) : ViewModel() {
    private val _toDoLists = MutableLiveData<Collection<ToDoList>>()
    private val _currentToDoList = MutableLiveData<ToDoList?>()
    private val _currentToDoTask = MutableLiveData<ToDoTask?>()
    var toDoLists: LiveData<Collection<ToDoList>> = _toDoLists
    var currentToDoList: LiveData<ToDoList?> = _currentToDoList
    var currentToDoTask: LiveData<ToDoTask?> = _currentToDoTask

    init {
        _toDoLists.value = toDoListRepository.index()
    }

    fun setCurrentList(toDoList: ToDoList) {
        _currentToDoList.value = toDoList;
    }

    fun setNewCurrentList() {
        _currentToDoList.value = toDoListRepository.create("", Date())
    }

    fun setCurrentTask(toDoTask: ToDoTask) {
        _currentToDoTask.value = toDoTask;
    }

    fun setNewCurrentTask() {
        _currentToDoTask.value = currentToDoList.value?.let { toDoTaskRepository.create(it, "") }
    }


}