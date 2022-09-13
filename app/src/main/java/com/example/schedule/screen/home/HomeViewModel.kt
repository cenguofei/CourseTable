package com.example.schedule.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule.DataResult
import com.example.schedule.data.local.CourseRepository
import com.example.schedule.data.model.CourseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiState(
    val courses: List<CourseModel> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.N)
    private var _courses = repository.getAll().onStart { DataResult.Loading }
    private var _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<UiState> =
        combine(_courses, _isLoading) { courses, isLoading ->
            when (courses) {
                is DataResult.Loading -> {
                    UiState()
                }
                is DataResult.Success -> {
                    UiState(
                        courses.data,
                        isLoading
                    )
                }
                else -> {
                    UiState()
                }
            }
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000, 5000),
                initialValue = UiState(emptyList())
            )

    fun onDeleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun onDeleteOne(deleteItem: CourseModel) {
        viewModelScope.launch {
            repository.deleteOne(deleteItem)
        }
    }
}