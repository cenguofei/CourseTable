package com.example.schedule.screen.add

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.schedule.colors
import com.example.schedule.courses
import com.example.schedule.screen.ScaffoldLayout
import com.example.schedule.screen.SnackBar
import com.example.schedule.screen.TextBoldComponent
import com.example.schedule.screen.TextInputComponent
import com.example.schedule.screen.home.BottomAddFloatingActionButton
import com.example.schedule.weeks
import com.example.schedule.weeksNumber

@Composable
fun AddScreen(
    viewModel: AddViewModel,
    scaffoldState: ScaffoldState,
    onBack: () -> Unit,
    verticalScrollState: ScrollState = rememberScrollState(),
    onComplete: () -> Unit
) {
    val messageState = remember { mutableStateOf(false) }

    ConstraintLayout(constraintSet = decoupledConstraints(0.dp)) {
        AddContent(
            modifier = Modifier.layoutId(addScreenContent),
            viewModel = viewModel,
            scaffoldState = scaffoldState,
            onBack = onBack,
            verticalScrollState = verticalScrollState,
            onNotValid = { messageState.value = true },
            onComplete = onComplete
        )
        DisposableEffect(key1 = "", effect = {
            onDispose {
                viewModel.onClear()
            }
        })

        if (messageState.value) {
//            ShowDialog {
//                messageState.value = false
//            }
            SnackBar(visible = messageState)
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val content = createRefFor(addScreenContent)
        val dialog = createRefFor(dialog)
        constrain(content) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(dialog) {
            top.linkTo(parent.top, margin = margin)
        }
    }
}

private inline val addScreenContent: String get() = "button"
private inline val dialog: String get() = "text"

@OptIn(InternalTextApi::class)
@Composable
private fun AddContent(
    modifier: Modifier,
    viewModel: AddViewModel,
    scaffoldState: ScaffoldState,
    onBack: () -> Unit,
    verticalScrollState: ScrollState,
    onNotValid: () -> Unit,
    onComplete: () -> Unit
) {
    ScaffoldLayout(
        scaffoldState = scaffoldState,
        navigationIcons = {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable { onBack() }
            )
        },
        floatingActionButtonContent = {
            BottomAddFloatingActionButton(
                onAdd = { viewModel.save(onNotValid = onNotValid, onComplete = onComplete) }
            )
        },
        drawerGesturesEnabled = false
    ) {
        Column(modifier = modifier.verticalScroll(verticalScrollState)) {
            TextBoldComponent(text = "Enter course name")
            TextInputComponent { viewModel.onNameChange(it) }

            TextBoldComponent(text = "What day in week")
            SelectorComponent(
                onLabelChange = { viewModel.onWeekChange(it) },
                scrollState = rememberScrollState(),
                data = weeks.keys,
                isSelected = { it == viewModel.week }
            )

            TextBoldComponent(text = "Enter teacher name")
            TextInputComponent { viewModel.onTeacherNameChange(it) }

            TextBoldComponent(text = "Enter address")
            TextInputComponent { viewModel.onAddressChange(it) }

            TextBoldComponent(text = "select first and last course")
            SelectorComponent(
                onLabelChange = { viewModel.onInternalChange(it, courses, false) },
                scrollState = rememberScrollState(),
                data = courses.keys,
                isSelected = { it == viewModel.firstCourse.text || it == viewModel.lastCourse.text }
            )

            TextBoldComponent(text = "Select start week and end week")
            SelectorComponent(
                onLabelChange = { viewModel.onInternalChange(it, weeksNumber, true) },
                scrollState = rememberScrollState(),
                data = weeksNumber.keys,
                isSelected = { it == viewModel.weekStart.text || it == viewModel.weekEnd.text }
            )
        }
    }
}

@Composable
fun SelectorComponent(
    onLabelChange: (String) -> Unit,
    scrollState: ScrollState,
    data: Set<String>,
    isSelected: (String) -> Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for ((index, item) in data.withIndex()) {
            Label(
                text = item,
                isSelected = isSelected(item),
                onLabelSelected = onLabelChange,
                colorIndex = index % colors.size
            )
        }
    }
}

@Composable
fun Label(
    text: String,
    isSelected: Boolean = false,
    colorIndex: Int = 0,
    onLabelSelected: (String) -> Unit
) {
    val color = if (isSelected) Color.Red.copy(alpha = 0.6f) else colors[colorIndex]
    TextButton(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = color)
            .padding(8.dp),
        onClick = { onLabelSelected(text) },
    ) {
        Text(text = text)
    }
}