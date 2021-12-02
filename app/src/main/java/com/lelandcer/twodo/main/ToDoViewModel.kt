package com.lelandcer.twodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelandcer.twodo.domain.*
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.ToDoListFactory
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.ToDoTaskFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ToDoViewModel @Inject constructor(
    createPlaceholderData: CreatePlaceholderData,
    private val saveToDoTask: SaveToDoTask,
    private val saveToDoList: SaveToDoList,
    private val deleteToDoList: DeleteToDoList,
    private val deleteToDoTask: DeleteToDoTask,
    private val toDoListFactory: ToDoListFactory,
    private val toDoTaskFactory: ToDoTaskFactory,
    private val getLists: GetLists
) : ViewModel() {
    private val _toDoLists = MutableLiveData<Collection<ToDoList>>()
    private val _currentToDoList = MutableLiveData<ToDoList?>()
    private val _currentToDoTask = MutableLiveData<ToDoTask?>()
    var toDoLists: LiveData<Collection<ToDoList>> = _toDoLists
    var currentToDoList: LiveData<ToDoList?> = _currentToDoList
    var currentToDoTask: LiveData<ToDoTask?> = _currentToDoTask

    private val listsFlow = flow {
        while (true) {
            emit(getLists.execute())
            delay(500)
            break;
        }
    }

    init {
        viewModelScope.launch {
            listsFlow.collect {
                _toDoLists.value = it
            }
        }


        createPlaceholderData.create()

    }

    private fun updateLists() {
        viewModelScope.launch {
            _toDoLists.postValue(getLists.execute())
        }
    }

    fun setCurrentList(toDoList: ToDoList) {
        _currentToDoList.value = toDoList;
    }

    fun setNewCurrentList() {
        _currentToDoList.value = toDoListFactory.makeToDoList("", Date())
    }

    fun setCurrentTask(toDoTask: ToDoTask) {
        _currentToDoTask.value = toDoTask;
    }

    fun setNewCurrentTask() {
        _currentToDoTask.value = currentToDoList.value?.let { toDoTaskFactory.makeToDoTask(it, "") }
    }

    fun saveCurrentTask() {
        val toDoList = currentToDoList.value!!
        val toDoTask = currentToDoTask.value!!
        viewModelScope.launch {
            saveToDoTask.execute(toDoTask)
            updateLists()
            _currentToDoList.postValue(toDoList)
            _currentToDoTask.postValue(toDoTask)
        }

    }

    fun saveCurrentList() {
        val toDoList = currentToDoList.value!!
        viewModelScope.launch {
            saveToDoList.execute(toDoList)
            updateLists()
            _currentToDoList.postValue(toDoList)
        }

    }

    fun deleteTask(toDoTask: ToDoTask) {
        val toDoList = currentToDoList.value!!
        viewModelScope.launch {
            if (currentToDoTask.value == toDoTask) {
                _currentToDoTask.postValue(null)
            }
            deleteToDoTask.execute(toDoTask)
            _currentToDoList.postValue(toDoList)
        }
    }

    fun deleteList(toDoList: ToDoList) {
        runBlocking {
            if (currentToDoList.value == toDoList) _currentToDoList.postValue(null)
            if (currentToDoList.value?.id == currentToDoTask.value?.listId) _currentToDoTask.postValue(
                null
            )
            deleteToDoList.execute(toDoList)
        }
    }


}