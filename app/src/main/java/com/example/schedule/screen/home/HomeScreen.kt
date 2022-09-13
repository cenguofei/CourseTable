package com.example.schedule.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schedule.colors
import com.example.schedule.data.model.CourseModel
import com.example.schedule.screen.AlertDialogComponent
import com.example.schedule.screen.MenuIcon
import com.example.schedule.screen.ScaffoldLayout
import com.example.schedule.weeksColors

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    title: String,
    //drawer
    onOpenDrawer: () -> Unit,
    onHomeClick: () -> Unit,
    onColorClick: () -> Unit,
    onAdd: () -> Unit,
    courses: List<CourseModel>,
    onDetail: () -> Unit,
    isLoading: Boolean,
    //dropdown menu
    onDeleteAll: () -> Unit,
    onDeleteOne: (CourseModel) -> Unit
) {
    ScaffoldLayout(
        title = title,
        scaffoldState = scaffoldState,
        onHomeClick = onHomeClick,
        onColorClick = onColorClick,
        floatingActionButtonContent = {
            BottomAddFloatingActionButton(onAdd = onAdd)
        },
        navigationIcons = {
            MenuIcon(onOpenDrawer = onOpenDrawer)
        },
        actions = {
            TopAppBarDropdownMenu(
                iconContent = {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null
                    )
                }
            ) { closeMenu ->
                DropdownMenuItem(
                    text = { Text(text = "clear") },
                    onClick = {
                        onDeleteAll()
                        closeMenu()
                    }
                )
            }
        },
        content = {
            if (isLoading) {
                LoadingContent()
            } else {
                var delete by remember { mutableStateOf(CourseModel(courseId = -1)) }
                var showDialog by remember { mutableStateOf(false) }
                CourseContent(
                    courses = courses,
                    onDetail = onDetail,
                    onItemLongPress = {
                        delete = it
                        showDialog = true
                    }
                )
                val dismissDialog = { showDialog = false }
                AlertDialogComponent(showDialog, onDismissRequest = dismissDialog) {
                    dismissDialog()
                    onDeleteOne(delete)
                }
            }
        }
    )
}

@Composable
private fun TopAppBarDropdownMenu(
    iconContent: @Composable () -> Unit,
    content: @Composable ColumnScope.(() -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            iconContent()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            content { expanded = !expanded }
        }
    }
}

@Composable
fun LoadingContent() {
    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}

@Composable
fun CourseContent(
    courses: List<CourseModel>,
    onDetail: () -> Unit,
    onItemLongPress: (CourseModel) -> Unit
) {
    LazyColumn {
        itemsIndexed(courses) { index, item ->
            CourseItem(
                item,
                index,
                onDetail = onDetail,
                onItemLongPress = { onItemLongPress(item) })
        }
    }
}

@Preview
@Composable
fun CourseItemPreview() {
    CourseItem(
        item = CourseModel(
            1,
            "aadafaewfasdfawefasdfawefasdfwefafe",
            1,
            2,
            1,
            2,
            "Mo",
            "addressaddressaddressaddressaddressaddress",
            "tea"
        ),
        0,
        onItemLongPress = {},
        onDetail = {}
    )
}

@Composable
fun CourseItem(
    item: CourseModel,
    index: Int,
    onDetail: () -> Unit,
    onItemLongPress: (Offset) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colors[index % colors.size])
            .clickable { onDetail() }
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = onItemLongPress)
            },
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.courseName,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .sizeIn(maxWidth = 210.dp)

            )
            TextComponent(text = item.time, color = weeksColors[item.week]!!)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextComponent(text = item.teacherName)
            Text(
                text = item.weekInternal,
                style = TextStyle(
                    color = Color.LightGray
                ),
                fontSize = 18.sp
            )
            TextComponent(text = item.address, modifier = Modifier.sizeIn(maxWidth = 100.dp))
        }
    }
}

@Composable
fun TextComponent(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            color = color
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun BottomAddFloatingActionButton(
    tint: Color = MaterialTheme.colorScheme.tertiary,
    onAdd: () -> Unit
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null,
        tint = tint,
        modifier = Modifier
            .size(40.dp)
            .clickable { onAdd() },
    )
}