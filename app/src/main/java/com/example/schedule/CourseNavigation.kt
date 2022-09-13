package com.example.schedule

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

object Screen {
    const val HOME = "home"
    const val COLOR = "color"
    const val ADD = "add"
}

class CourseNavigation(private val navController: NavController) {
    fun navigateToHome(){
        navController.navigate(route = Screen.HOME){
            popUpTo(navController.graph.findStartDestination().id){
                inclusive = false
            }
            launchSingleTop = true
        }.also { test() }
    }

    fun navigateToColor(){
        navController.navigate(Screen.COLOR){
            popUpTo(navController.graph.findStartDestination().id){
                inclusive = false
            }
            launchSingleTop = true
        }.also { test() }
    }

    fun navigateToAdd(){
        navController.navigate(Screen.ADD).also { test() }
    }

    private fun test(){
        "backQueue size = ${navController.backQueue.size}"
        navController.backQueue.forEach { entry ->
            entry.destination.displayName.logv()
        }
    }
}