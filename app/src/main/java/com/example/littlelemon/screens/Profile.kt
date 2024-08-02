package com.example.littlelemon.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.EMAIL
import com.example.littlelemon.FIRST_NAME
import com.example.littlelemon.LAST_NAME
import com.example.littlelemon.USER_PROFILE
import com.example.littlelemon.R
import com.example.littlelemon.navigation.HomeDest
import com.example.littlelemon.navigation.OnboardingDest
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString(FIRST_NAME, "Unknown")
    val lastName = sharedPreferences.getString(LAST_NAME, "Unknown")
    val email = sharedPreferences.getString(EMAIL, "Unknown")

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.padding(top = 16.dp)
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Personal Information",
            style = typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            "First Name: $firstName",
            modifier = Modifier.padding(8.dp),
            style = typography.bodyMedium
        )
        Text(
            "Last Name: $lastName",
            modifier = Modifier.padding(8.dp),
            style = typography.bodyMedium
        )

        Text(
            "Email: $email",
            modifier = Modifier.padding(8.dp),
            style = typography.bodyMedium
        )

        Button(
            onClick = {
                //sharedPreferences.edit().clear().apply()
                navController.navigate(HomeDest.route) {
                    popUpTo(HomeDest.route) { inclusive = true }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.lemon_yellow),
                contentColor = colorResource(id = R.color.secondary_4)
            )
        ) {
            Text("Main Menu")
        }

        Button(
            onClick = {
                //sharedPreferences.edit().clear().apply()
                navController.navigate(OnboardingDest.route) {
                    popUpTo(OnboardingDest.route) { inclusive = true }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.lemon_yellow),
                contentColor = colorResource(id = R.color.secondary_4)
            )
        ) {
            Text("Log out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        val navController = NavController(LocalContext.current)
        Profile(navController)
    }
}