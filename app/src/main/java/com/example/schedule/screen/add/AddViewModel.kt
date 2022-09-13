package com.example.schedule.screen.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.data.local.CourseRepository
import com.example.schedule.data.model.CourseModel
import com.example.schedule.int
import com.example.schedule.isEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {
    private var courseName by mutableStateOf("")
    var week by mutableStateOf("")
        private set
    private var teacherName by mutableStateOf("")
    private var address by mutableStateOf("")
    var firstCourse by mutableStateOf(TextFieldValue(""))
        private set
    var lastCourse by mutableStateOf(TextFieldValue(""))
        private set
    var weekStart by mutableStateOf(TextFieldValue(""))
        private set
    var weekEnd by mutableStateOf(TextFieldValue(""))
        private set

    fun onNameChange(value: TextFieldValue) {
        courseName = value.text
    }

    fun onWeekChange(value: String) {
        week = value
    }

    fun onTeacherNameChange(value: TextFieldValue) {
        teacherName = value.text
    }

    fun onAddressChange(value: TextFieldValue) {
        address = value.text
    }

    fun onInternalChange(value: String, datas: Map<String, Int>, isWeeks: Boolean) {
        var first: TextFieldValue?
        var second: TextFieldValue?
        if (isWeeks) {
            first = weekStart
            second = weekEnd
        } else {
            first = firstCourse
            second = lastCourse
        }
        val new = TextFieldValue(value)
        val newIndex = datas[value]!!
        val firstIndex = datas[first.text] ?: 0
        val lastIndex = datas[second.text] ?: 0
        val empty = TextFieldValue("")
        if (value == first.text) {
            first = empty
        } else if (value == second.text) {
            second = empty
        } else if (second.isEmpty) {
            if (first.isEmpty) first = new
            else second = new
        } else if (first.isEmpty) {
            if (newIndex > lastIndex) {
                first = TextFieldValue(second.text)
                second = new
            } else {
                first = new
            }
        } else {
            if (newIndex < firstIndex) {
                first = new
            } else if (newIndex in firstIndex..lastIndex) {
                if (newIndex - firstIndex < lastIndex - newIndex) {
                    first = new
                } else {
                    second = new
                }
            } else {
                second = new
            }
        }
        if (isWeeks) {
            weekStart = first
            weekEnd = second
        } else {
            firstCourse = first
            lastCourse = second
        }
    }

    fun save(
        onNotValid: () -> Unit,
        onComplete: () -> Unit
    ) {
        if (notValid()) {
            onNotValid()
            return
        }
        viewModelScope.launch {
            repository.insertOne(
                CourseModel(
                    courseName = courseName,
                    week = week,
                    teacherName = teacherName,
                    firstCourse = firstCourse.text.int,
                    lastCourse = lastCourse.text.int,
                    address = address,
                    weekStart = weekStart.text.int,
                    weekEnd = weekEnd.text.int
                )
            )
            onComplete()
        }
    }

    private fun notValid() =
        courseName.isEmpty || teacherName.isEmpty
                || firstCourse.isEmpty || lastCourse.isEmpty || address.isEmpty
                || weekStart.isEmpty || weekEnd.isEmpty
                || week.isEmpty  /*weeks[week]!! !in (weeks[weekStart.text]!!..weeks[weekEnd.text]!!)*/

    fun onClear() {
        week = ""
        firstCourse = TextFieldValue("")
        lastCourse = TextFieldValue("")
        weekStart = TextFieldValue("")
        weekEnd = TextFieldValue("")
    }
}