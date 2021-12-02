package com.lelandcer.twodo.models.list.repositories

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
    abstract fun bindToDoListRepository(toDoListRepository: MultiToDoListRepository): ToDoListRepository

    @Binds
    abstract fun bindCacheToDoListRepository(cacheToDoListRepository: MemCacheToDoListRepository): CacheToDoListRepository

    @Binds
    abstract fun bindLocalStorageToDoListRepository(localStorageToDoListRepository: RoomLocalStorageRepository): LocalStorageToDoListRepository

}