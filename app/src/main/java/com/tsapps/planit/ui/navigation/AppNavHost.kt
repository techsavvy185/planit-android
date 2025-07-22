package com.tsapps.planit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tsapps.planit.ui.screens.aboutScreen.AboutScreen
import com.tsapps.planit.ui.screens.calendarScreen.CalendarScreen
import com.tsapps.planit.ui.screens.homeScreen.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCalendar = {
                    navController.navigate(Screen.Calendar.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(Screen.Calendar.route) {
            CalendarScreen()
        }
        composable(Screen.About.route) {
            AboutScreen()
        }
    }
}