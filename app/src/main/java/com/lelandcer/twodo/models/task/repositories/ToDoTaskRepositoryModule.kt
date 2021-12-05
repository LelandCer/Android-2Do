package com.lelandcer.twodo.models.task.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
/**
 * Bind ToDoTask repository interface to implementation
 */
abstract class ToDoTaskRepositoryModule {

    @Binds
    abstract fun bindToDoTaskRepository(taskRepository: MultiToDoTaskRepository): ToDoTaskRepository

    @Binds
    abstract fun bindCacheToDoTaskRepository(taskRepository: MemCacheToDoTaskRepository): CacheToDoTaskRepository
}