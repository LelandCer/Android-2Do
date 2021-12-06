package com.lelandcer.twodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelandcer.twodo.domain.ActionHandler
import com.lelandcer.twodo.domain.actions.*
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.ToDoListFactory
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.ToDoTaskFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val saveToDoTask: SaveToDoTask,
    private val saveToDoList: SaveToDoList,
    private val deleteToDoList: DeleteToDoList,
    private val deleteToDoTask: DeleteToDoTask,
    private val toDoListFactory: ToDoListFactory,
    private val toDoTaskFactory: ToDoTaskFactory,
    private val getLists: GetLists
) : ViewModel() {
    private val actionHandler = ActionHandler(viewModelScope)
    private val _toDoLists = MutableLiveData<Collection<ToDoList>>()
    private val _currentToDoList = MutableLiveData<ToDoList?>()
    private val _currentToDoTask = MutableLiveData<ToDoTask?>()

    val toDoLists: LiveData<Collection<ToDoList>> = _toDoLists
    val currentToDoList: LiveData<ToDoList?> = _currentToDoList
    val currentToDoTask: LiveData<ToDoTask?> = _currentToDoTask


    init {
        viewModelScope.launch {
            updateLists()
        }
    }

    private fun updateLists() {
        actionHandler.perform(getLists, GetLists.getParameters()) {
            _toDoLists.postValue(it.toDoLists)

            val currentList = it.toDoLists.find { l -> l.id == _currentToDoList.value?.id }
            _currentToDoList.postValue(currentList)

            val currentTask =
                currentList?.toDoTasks?.firstOrNull { t -> t == _currentToDoTask.value }
            _currentToDoTask.postValue(currentTask)
        }
    }

    fun setCurrentList(toDoList: ToDoList) {
        _currentToDoList.value = toDoList
    }

    fun setNewCurrentList() {
        _currentToDoList.value = toDoListFactory.makeToDoList("", Date())
    }

    fun setCurrentTask(toDoTask: ToDoTask) {
        _currentToDoTask.value = toDoTask
    }

    fun setNewCurrentTask() {
        _currentToDoTask.value = currentToDoList.value?.let { toDoTaskFactory.makeToDoTask(it, "") }
    }

    fun saveCurrentTask() {
        val toDoList = currentToDoList.value!!
        val toDoTask = currentToDoTask.value!!
        _currentToDoList.postValue(toDoList)
        _currentToDoTask.postValue(toDoTask)
        actionHandler.perform(saveToDoTask, SaveToDoTask.getParameters(toDoTask)) {
            updateLists()

        }


    }

    fun saveCurrentList() {
        val toDoList = currentToDoList.value!!
        _currentToDoList.postValue(toDoList)
        actionHandler.perform(saveToDoList, SaveToDoList.getParameters(toDoList)) {
            updateLists()
        }

    }

    fun deleteTask(toDoTask: ToDoTask) {
        if (currentToDoTask.value == toDoTask) {
            _currentToDoTask.postValue(null)
        }

        actionHandler.perform(deleteToDoTask, DeleteToDoTask.getParameters(toDoTask)) {
            updateLists()

        }
    }

    fun deleteList(toDoList: ToDoList) {
        if (currentToDoList.value == toDoList) _currentToDoList.postValue(null)
        if (currentToDoList.value?.id == currentToDoTask.value?.listId) _currentToDoTask.postValue(
            null
        )

        actionHandler.perform(deleteToDoList, DeleteToDoList.getParameters(toDoList)) {
            updateLists()
        }

    }
}


