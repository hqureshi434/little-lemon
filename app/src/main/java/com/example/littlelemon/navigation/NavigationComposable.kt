package com.example.littlelemon.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.EMAIL
import com.example.littlelemon.USER_PROFILE
import com.example.littlelemon.data.AppDatabase
import com.example.littlelemon.screens.Home
import com.example.littlelemon.screens.OnBoarding
import com.example.littlelemon.screens.Profile

@Composable
fun Navigation(navController: NavHostController, database: AppDatabase) {
    val hasUserData = hasUserData()

    NavHost(
        navController = navController,
        startDestination = if(hasUserData) OnboardingDest.route else HomeDest.route
    ) {
        composable(OnboardingDest.route) { OnBoarding(navController = navController) }
        composable(HomeDest.route) { Home(navController = navController, database) }
        composable(ProfileDest.route) { Profile(navController = navController) }
    }
}

@Composable
fun hasUserData(): Boolean {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
    val email = sharedPreferences.getString(EMAIL, "") ?: ""
    return email.isNotBlank()
}