package com.example.schedule.data.local

import androidx.room.*
import com.example.schedule.data.model.CourseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM course_table ORDER BY week ASC")
    fun selectAll():Flow<List<CourseModel>>

    /* 插入一个课题，当出现冲突时说明是修改，应该替换   */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(courseModel: CourseModel)

    @Delete
    suspend fun deleteOne(courseModel: CourseModel)

    @Query("DELETE FROM course_table")
    suspend fun deleteAll()
}