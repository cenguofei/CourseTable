package com.example.schedule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.schedule.data.model.CourseModel

@Database(
    entities = [CourseModel::class],
    version = 1,
    exportSchema = false
)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun getDao(): CourseDao
}