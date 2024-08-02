package com.example.littlelemon.navigation

interface Destinations {
    val route: String
}

object OnboardingDest : Destinations {
    override val route = "Onboarding"
}

object HomeDest : Destinations {
    override val route = "Home"
}

object ProfileDest : Destinations {
    override val route = "Profile"
}