package com.lelandcer.twodo.database.room.repositories

import com.lelandcer.twodo.models.task.repositories.LocalStorageToDoTaskRepository
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
    abstract fun bindLocalStorageToDoTaskRepository(taskRepository: RoomLocalStorageToDoTaskRepository): LocalStorageToDoTaskRepository

}