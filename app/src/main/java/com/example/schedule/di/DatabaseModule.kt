package com.example.schedule.di

import android.content.Context
import androidx.room.Room
import com.example.schedule.data.local.CourseDao
import com.example.schedule.data.local.CourseDatabase
import com.example.schedule.data.local.CourseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context:Context) : CourseDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            CourseDatabase::class.java,
            "CourseTable.db"
        ).build()

    @Singleton
    @Provides
    fun provideLocalRepository(
        courseDatabase: CourseDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ):CourseRepository =
        CourseRepository(courseDatabase,ioDispatcher)
}