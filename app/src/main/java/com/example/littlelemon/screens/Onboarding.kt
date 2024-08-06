package com.example.littlelemon.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.EMAIL
import com.example.littlelemon.FIRST_NAME
import com.example.littlelemon.LAST_NAME
import com.example.littlelemon.R
import com.example.littlelemon.LITTLE_LEMON
import com.example.littlelemon.USER_REGISTERED
import com.example.littlelemon.navigation.HomeDest
import com.example.littlelemon.navigation.OnboardingDest
import com.example.littlelemon.ui.theme.LemonGreen
import com.example.littlelemon.ui.theme.LemonYellow
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.Secondary4
import com.example.littlelemon.utils.isValidForm
import com.example.littlelemon.utils.rememberImeState

@Composable
fun OnBoarding(context: Context, navController: NavController) {
    val sharedPreferences = context.getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()

    LaunchedEffect(imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(Modifier.fillMaxWidth(0.6f)) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Row(
            modifier = Modifier.height(150.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.titleLarge,
                color = LemonGreen
            )
        }

        Text(
            text = "Personal Information",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall
        )
        OutlinedTextField(
            value = firstName.value,
            onValueChange = {
                firstName.value = it
            },
            label = { Text("First name")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = LemonGreen,
                focusedBorderColor = LemonGreen
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = lastName.value,
            onValueChange = {
                lastName.value = it
            },
            label = { Text("Last name") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = LemonGreen,
                focusedBorderColor = LemonGreen
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = { Text("Email") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = LemonGreen,
                focusedBorderColor = LemonGreen
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                if(isValidForm(firstName.value, lastName.value, email.value)) {
                    with(sharedPreferences.edit()) {
                        putString(FIRST_NAME, firstName.value)
                        putString(LAST_NAME, lastName.value)
                        putString(EMAIL, email.value)
                        putBoolean(USER_REGISTERED, true)
                        commit()
                    }
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate(HomeDest.route) {
                        popUpTo(OnboardingDest.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                else {
                    Toast.makeText(context, "Invalid Details. Please input valid details.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LemonYellow,
                contentColor = Secondary4
            )
        ) {
            Text("Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    LittleLemonTheme {
        val context = LocalContext.current
        val navController = NavController(LocalContext.current)
        OnBoarding(context, navController)
    }
}