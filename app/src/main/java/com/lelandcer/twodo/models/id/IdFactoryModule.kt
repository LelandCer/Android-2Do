package com.lelandcer.twodo.models.id

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
/**
 * Bind the Id factory implementation
 */
abstract class IdFactoryModule {

    @Binds
    abstract fun bind(idFactory: UUIDIdFactory): IdFactory

}