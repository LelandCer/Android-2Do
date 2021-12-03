package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.database.repositories.RoomLocalStorageToDoTaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
/**
 * Bind Task repository interface to implementation
 */
abstract class ToDoTaskRepositoryModule {

    @Binds
    abstract fun bindToDoTaskRepository(taskRepository: MultiToDoTaskRepository): ToDoTaskRepository

    @Binds
    abstract fun bindCacheToDoTaskRepository(taskRepository: MemCacheToDoTaskRepository): CacheToDoTaskRepository

    @Binds
    abstract fun bindLocalStorageToDoTaskRepository(taskRepository: RoomLocalStorageToDoTaskRepository): LocalStorageToDoTaskRepository

}