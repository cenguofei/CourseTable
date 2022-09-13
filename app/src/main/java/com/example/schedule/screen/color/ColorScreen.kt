package com.example.schedule.screen.color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.schedule.screen.MenuIcon
import com.example.schedule.screen.ScaffoldLayout

data class ColorItem(
    val name: String,
    val color: Color
)

@Composable
fun ColorScreen(
    title: String,
    scaffoldState: ScaffoldState,
    onHomeClick: () -> Unit,
    onColorClick: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    ScaffoldLayout(
        title = title,
        scaffoldState = scaffoldState,
        onHomeClick = onHomeClick,
        onColorClick = onColorClick,
        navigationIcons = {
            MenuIcon(onOpenDrawer = onOpenDrawer)
        },
        content = {
            ColorContent()
        }
    )
}

@Composable
fun ColorContent(
    colorItems: List<ColorItem> = getColors()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(colorItems) { item ->
            ColorItemBox(item)
        }
    }
}

@Composable
fun ColorItemBox(item: ColorItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.Gray.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(10.dp))
            .shadow(elevation = 1.dp, shape = RectangleShape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = item.name, modifier = Modifier
                .padding(16.dp)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .background(color = item.color)
                .padding(16.dp)
                .weight(1f)
        )
    }
}

@Composable
private fun getColors() =
    listOf(
        ColorItem("primary", MaterialTheme.colorScheme.primary),
        ColorItem("onPrimary", MaterialTheme.colorScheme.onPrimary),
        ColorItem("primaryContainer", MaterialTheme.colorScheme.primaryContainer),
        ColorItem("onPrimaryContainer", MaterialTheme.colorScheme.onPrimaryContainer),
        ColorItem("inversePrimary", MaterialTheme.colorScheme.inversePrimary),
        ColorItem("secondary", MaterialTheme.colorScheme.secondary),
        ColorItem("onSecondary", MaterialTheme.colorScheme.onSecondary),
        ColorItem("secondaryContainer", MaterialTheme.colorScheme.secondaryContainer),
        ColorItem("onSecondaryContainer", MaterialTheme.colorScheme.onSecondaryContainer),
        ColorItem("tertiary", MaterialTheme.colorScheme.tertiary),
        ColorItem("onTertiary", MaterialTheme.colorScheme.onTertiary),
        ColorItem("tertiaryContainer", MaterialTheme.colorScheme.tertiaryContainer),
        ColorItem("onTertiaryContainer", MaterialTheme.colorScheme.onTertiaryContainer),
        ColorItem("background", MaterialTheme.colorScheme.background),
        ColorItem("onBackground", MaterialTheme.colorScheme.onBackground),
        ColorItem("surface", MaterialTheme.colorScheme.surface),
        ColorItem("onSurface", MaterialTheme.colorScheme.onSurface),
        ColorItem("surfaceVariant", MaterialTheme.colorScheme.surfaceVariant),
        ColorItem("onSurfaceVariant", MaterialTheme.colorScheme.onSurfaceVariant),
        ColorItem("inverseSurface", MaterialTheme.colorScheme.inverseSurface),
        ColorItem("inverseOnSurface", MaterialTheme.colorScheme.inverseOnSurface),
        ColorItem("error", MaterialTheme.colorScheme.error),
        ColorItem("onError", MaterialTheme.colorScheme.onError),
        ColorItem("errorContainer", MaterialTheme.colorScheme.errorContainer),
        ColorItem("onErrorContainer", MaterialTheme.colorScheme.onErrorContainer),
        ColorItem("outline", MaterialTheme.colorScheme.outline)
    )