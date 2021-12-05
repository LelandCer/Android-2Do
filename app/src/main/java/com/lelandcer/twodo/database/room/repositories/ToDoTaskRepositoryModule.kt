package com.lelandcer.twodo.database.room.repositories

import com.lelandcer.twodo.models.task.repositories.LocalStorageToDoTaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
/**
 * Bind the Room Repository Implementation to the interface.
 */
abstract class ToDoTaskRepositoryModule {

    @Binds
    abstract fun bindLocalStorageToDoTaskRepository(taskRepository: RoomLocalStorageToDoTaskRepository): LocalStorageToDoTaskRepository

}