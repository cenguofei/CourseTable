package com.example.schedule.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.schedule.DataResult
import com.example.schedule.data.model.CourseModel
import com.example.schedule.di.IoDispatcher
import com.example.schedule.weeks
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CourseRepository @Inject constructor(
    courseDatabase: CourseDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    private val courseDao = courseDatabase.getDao()

    @RequiresApi(Build.VERSION_CODES.N)
    fun getAll(): Flow<DataResult<List<CourseModel>>> =
        courseDao.selectAll().map { courses -> DataResult.Success(sortCourses(courses)) }

    suspend fun insertOne(courseModel: CourseModel) {
        withContext(ioDispatcher){
            courseDao.insertOne(courseModel)
        }
    }

    suspend fun deleteOne(courseModel: CourseModel) {
        withContext(ioDispatcher){
            courseDao.deleteOne(courseModel)
        }
    }

    suspend fun deleteAll(){
        withContext(ioDispatcher){
            courseDao.deleteAll()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun sortCourses(courses:List<CourseModel>):List<CourseModel>{
        val result = mutableListOf<CourseModel>()
        courses.groupBy {
            return@groupBy it.week
        }.let { map ->
            for (week in weeks.keys){
                val day = map.getOrDefault(week, listOf())
                result += day.sortedBy { it.firstCourse }
            }
        }
        return result
    }
}