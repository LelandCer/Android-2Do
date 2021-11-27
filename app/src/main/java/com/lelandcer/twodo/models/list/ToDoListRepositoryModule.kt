package com.lelandcer.twodo.models.list

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
/**
 * Bind List repository interface to implementation
 */
abstract class ToDoListRepositoryModule {

    @Binds
    abstract fun bind(listRepository: PlaceholderToDoListRepository): ToDoListRepository

}