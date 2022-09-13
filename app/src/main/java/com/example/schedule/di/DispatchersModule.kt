package com.example.schedule.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @IoDispatcher
    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}