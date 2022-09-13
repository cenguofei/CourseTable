package com.example.schedule

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.schedule.screen.AlertDialogComponent
import com.example.schedule.screen.add.AddScreen
import com.example.schedule.screen.add.AddViewModel
import com.example.schedule.screen.color.ColorScreen
import com.example.schedule.screen.home.HomeScreen
import com.example.schedule.screen.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseGraph(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navigation: CourseNavigation = remember { CourseNavigation(navController) },
    homeViewModel: HomeViewModel = hiltViewModel(),
    addViewModel: AddViewModel = hiltViewModel()
) {
    val openDrawer = {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }
    val closeDrawer = {
        coroutineScope.launch {
            scaffoldState.drawerState.close()
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.HOME,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Screen.HOME) {
            val homeUiState = homeViewModel.uiState.collectAsState()
            homeUiState.value.courses.forEach {
                "courseName=$it".logv()
            }
            var shown by remember { mutableStateOf(false) }
            HomeScreen(
                onOpenDrawer = { openDrawer() },
                onHomeClick = { closeDrawer() },
                scaffoldState = scaffoldState,
                title = "Course Table",
                onColorClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                        navigation.navigateToColor()
                    }
                },
                onAdd = { navigation.navigateToAdd() },
                courses = homeUiState.value.courses,
                onDetail = {},
                isLoading = homeUiState.value.isLoading,
                onDeleteAll = { shown = true },
                onDeleteOne = { homeViewModel.onDeleteOne(it) }
            )
            AlertDialogComponent(shown, onDismissRequest = { shown = false }) {
                homeViewModel.onDeleteAll()
                shown = false
            }
        }
        composable(Screen.COLOR) {
            ColorScreen(
                title = "Browse Color",
                scaffoldState = scaffoldState,
                onHomeClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                        navigation.navigateToHome()
                    }
                },
                onColorClick = { closeDrawer() },
                onOpenDrawer = { openDrawer() }
            )
        }
        composable(Screen.ADD) {
            AddScreen(
                onBack = { navigation.navigateToHome() },
                viewModel = addViewModel,
                scaffoldState = scaffoldState,
                onComplete = {
                    addViewModel.onClear()
                    navigation.navigateToHome()
                }
            )
        }
    }
}