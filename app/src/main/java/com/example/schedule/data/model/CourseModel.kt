package com.example.schedule.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "course_table"
)
data class CourseModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var courseId: Long = 0,
    @ColumnInfo(name = "course_name") val courseName: String = "", //课程名称
    @ColumnInfo(name = "first_course") val firstCourse: Int = 0, //上课时间
    @ColumnInfo(name = "last_course") val lastCourse: Int = 0, //下课时间
    @ColumnInfo(name = "week_start") val weekStart: Int = 0, //开始周
    @ColumnInfo(name = "week_end") val weekEnd: Int = 0, //结束周
    @ColumnInfo(name = "week") val week: String = "", //星期几的课
    @ColumnInfo(name = "address") val address: String = "", //上课地点
    @ColumnInfo(name = "teacher_name") val teacherName: String = "" //教师
) {
    override fun toString(): String {
        return "name:$courseName week in:$weekStart-$weekEnd  " +
                "week:$week course:$firstCourse-$lastCourse address:$address teacher:$teacherName"
    }

    var courseNumber = lastCourse - firstCourse + 1

    var time = "$week $firstCourse-$lastCourse"

    var weekInternal = "weeks:$weekStart-$weekEnd"
}