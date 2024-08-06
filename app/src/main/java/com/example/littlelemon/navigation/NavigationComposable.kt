package com.example.littlelemon.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.LITTLE_LEMON
import com.example.littlelemon.USER_REGISTERED
import com.example.littlelemon.screens.Home
import com.example.littlelemon.screens.OnBoarding
import com.example.littlelemon.screens.Profile

@Composable
fun Navigation(context: Context, navController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
    var startDestination = OnboardingDest.route

    if(sharedPreferences.getBoolean(USER_REGISTERED, false)) {
        startDestination = HomeDest.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(OnboardingDest.route) { OnBoarding(context, navController = navController) }
        composable(HomeDest.route) { Home(navController = navController) }
        composable(ProfileDest.route) { Profile(context, navController = navController) }
    }
}