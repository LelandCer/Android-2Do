package com.lelandcer.twodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
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

    private val  listsFlow =  flow {
        while (true) {
            emit(toDoListRepository.index())
            delay(500)
        }
    }

    init {
        viewModelScope.launch {
            listsFlow.collect {
                _toDoLists.value = it
            }
        }

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

    fun saveCurrentTask() {
        val toDoList = currentToDoList.value!!
        val toDoTask = currentToDoTask.value!!
        toDoTaskRepository.store(toDoList, toDoTask)
        _currentToDoList.postValue(toDoList)
        _currentToDoTask.postValue(toDoTask)
    }

    fun saveCurrentList() {
        val toDoList = currentToDoList.value!!
        toDoListRepository.store(toDoList)
        _currentToDoList.postValue(toDoList)

    }


}