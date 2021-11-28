package com.lelandcer.twodo.features.list

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
/**
 * Bind Display interface to implementation
 */
abstract class ToDoListDisplayModule {

    @Binds
    abstract fun bind(toDoListDisplay: FriendlyToDoListDisplay): ToDoListDisplay

}