package com.lelandcer.twodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelandcer.twodo.models.list.ToDoList

class ToDoViewModel : ViewModel() {
    private val _toDoLists = MutableLiveData<Collection<ToDoList>>()
    private val _currentToDoList = MutableLiveData<ToDoList?>()
    var toDoLists: LiveData<Collection<ToDoList>> = _toDoLists
    var currentToDoList: LiveData<ToDoList?> = _currentToDoList


}