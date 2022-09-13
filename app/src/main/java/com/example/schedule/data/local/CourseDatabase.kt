package com.example.schedule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.schedule.data.model.CourseModel
import javax.inject.Inject

@Database(
    entities = [CourseModel::class],
    version = 1,
    exportSchema = false
)
abstract class CourseDatabase : RoomDatabase(){

    abstract fun getDao():CourseDao
}