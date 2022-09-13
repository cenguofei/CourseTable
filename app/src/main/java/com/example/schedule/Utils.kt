package com.example.schedule

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue

sealed class DataResult<out T> {
    object Loading : DataResult<Nothing>()
    data class Error(val error: Throwable) : DataResult<Nothing>()
    data class Success<out T>(val data: T) : DataResult<T>()
}

fun String.logv(tag: String = "cgf") {
    Log.v(tag, this)
}

inline val Int.isEmpty: Boolean get() = this == 0
inline val Int.string: String get() = this.toString()
inline val String.isEmpty: Boolean get() = this.isEmpty()
inline val String.int: Int get() = this.toInt()
inline val TextFieldValue.isEmpty: Boolean get() = this.text.isEmpty()

inline val weeks: Map<String, Int>
    get() = mapOf(
        "Mo" to 1,
        "Tu" to 2,
        "We" to 3,
        "Th" to 4,
        "Fr" to 5,
        "Sa" to 6,
        "Su" to 7
    )

inline val weeksColors: Map<String,Color>
    get() = mapOf(
        "Mo" to Color.Green,
        "Tu" to Color.Blue,
        "We" to Color.Magenta,
        "Th" to Color.Red,
        "Fr" to Color.Yellow,
        "Sa" to Color.Cyan,
        "Su" to Color(0xAFF099FF)
    )

inline val colors: List<Color>
    @Composable get() = listOf(
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
        MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f),
        MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    )

inline val courses: Map<String, Int>
    get() = mapOf(
        "1" to 1, "2" to 2, "3" to 3,
        "4" to 4, "5" to 5, "6" to 6,
        "7" to 7, "8" to 8, "9" to 9,
        "10" to 10, "11" to 11, "12" to 12,
        "13" to 13, "14" to 14
    )

inline val weeksNumber: Map<String, Int>
    get() = mapOf(
        "1" to 1, "2" to 2, "3" to 3,
        "4" to 4, "5" to 5, "6" to 6,
        "7" to 7, "8" to 8, "9" to 9,
        "10" to 10, "11" to 11, "12" to 12,
        "13" to 13, "14" to 14, "15" to 15,
        "16" to 16, "17" to 17, "18" to 18,
        "19" to 19, "20" to 20, "21" to 21,
        "22" to 22, "23" to 23, "24" to 24,
        "25" to 25
    )