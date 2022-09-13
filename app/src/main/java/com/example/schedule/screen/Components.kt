package com.example.schedule.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.schedule.R
import kotlinx.coroutines.delay


@Composable
fun SnackBar(
    visible: MutableState<Boolean>
) {
    AnimatedVisibility(
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing)
        ),
        visible = visible.value
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 4.dp,
            tonalElevation = 4.dp,
        ) {
            Text(
                text = stringResource(id = R.string.dialog),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center
                )
            )
        }
        LaunchedEffect(Unit) {
            delay(1000)
            visible.value = false
        }
    }
}

@Composable
fun AlertDialogComponent(
    shown: Boolean,
    text: String = "Are you sure to continue?",
    confirm: String = "confirm",
    cancel: String = "cancel",
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    if (shown) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            text = { Text(text = text) },
            confirmButton = {
                TextButton(onClick = onConfirmClick) {
                    Text(text = confirm)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = cancel)
                }
            },
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false), content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { onDismiss() }
            ) {
                Text(
                    text = "填写信息有误！",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.Cyan)
                )
            }
        }
    )
}

@Composable
fun TextBoldComponent(text: String) {
    // Text is a predefined composable that does exactly what you'd expect it to - display text on
    // the screen. It allows you to customize its appearance using style, fontWeight, fontSize, etc.
    Text(
        text, style = TextStyle(
            fontFamily = FontFamily.Monospace, fontWeight = FontWeight.W900,
            fontSize = 14.sp, color = Color.Black
        ), modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@InternalTextApi
@Composable
fun TextInputComponent(
    onValueChange: (TextFieldValue) -> Unit
) {
    Surface(
        color = Color.LightGray.copy(alpha = 0.2f),
        modifier = Modifier.padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
    ) {
        var textValue by remember { mutableStateOf(TextFieldValue("")) }
        BasicTextField(
            value = textValue,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onValueChange = {
                textValue = it
                onValueChange(it)
            },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
            )
        )
    }
}

@Composable
fun ScaffoldLayout(
    scaffoldState: ScaffoldState,
    title: String = "",
    drawerGesturesEnabled: Boolean = true,
    floatingActionButtonContent: @Composable () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onColorClick: () -> Unit = {},
    navigationIcons: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = title,
                navigationIcons = navigationIcons,
                actions = actions
            )
        },
        floatingActionButton = floatingActionButtonContent,
        drawerContent = {
            DrawerContent(
                onHomeClick = onHomeClick,
                onColorClick = onColorClick
            )
        },
        drawerShape = RoundedCornerShape(8.dp),
        drawerGesturesEnabled = drawerGesturesEnabled,
        content = content
    )
}

@Composable
fun DrawerContent(
    onHomeClick: () -> Unit,
    onColorClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        DrawerItem(text = "Home", onClick = onHomeClick)
        DrawerItem(text = "Color", onClick = onColorClick)
    }
}

@Composable
fun DrawerItem(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        onClick = onClick,
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 16.dp
        ),
        border = BorderStroke(1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) { Text(text = text) }
}

@Composable
fun MenuIcon(onOpenDrawer: () -> Unit = {}) {
    IconButton(onClick = onOpenDrawer) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun TopAppBar(
    title: String = "",
    navigationIcons: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    SmallTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = navigationIcons,
        actions = actions
    )
}
