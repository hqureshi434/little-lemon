package com.example.littlelemon.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.littlelemon.LITTLE_LEMON
import com.example.littlelemon.R
import com.example.littlelemon.navigation.HomeDest
import com.example.littlelemon.navigation.OnboardingDest
import com.example.littlelemon.ui.theme.LemonGreen
import com.example.littlelemon.ui.theme.LemonYellow
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.Secondary4

@Composable
fun Profile(context: Context, navController: NavController) {
    val sharedPreferences = context.getSharedPreferences(LITTLE_LEMON, Context.MODE_PRIVATE)
    val firstName = remember { mutableStateOf(sharedPreferences.getString(FIRST_NAME, "Unknown")) }
    val lastName = remember { mutableStateOf(sharedPreferences.getString(LAST_NAME, "Unknown")) }
    val email = remember { mutableStateOf(sharedPreferences.getString(EMAIL, "Unknown")) }
    val scrollState = rememberScrollState()

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

        Text(
            text = "Personal Information",
            style = typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = firstName.value!!,
            onValueChange ={},
            label = { Text(text = "First Name")},
            singleLine = true,
            placeholder = { Text(text = "John")},
            colors = OutlinedTextFieldDefaults. colors(
                disabledBorderColor = LemonGreen,
                disabledLabelColor = LemonGreen
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = lastName.value!!,
            onValueChange ={},
            label = { Text(text = "First Name")},
            singleLine = true,
            placeholder = { Text(text = "John")},
            colors = OutlinedTextFieldDefaults. colors(
                disabledBorderColor = LemonGreen,
                disabledLabelColor = LemonGreen
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = email.value!!,
            onValueChange ={},
            label = { Text(text = "First Name")},
            singleLine = true,
            placeholder = { Text(text = "John")},
            colors = OutlinedTextFieldDefaults. colors(
                disabledBorderColor = LemonGreen,
                disabledLabelColor = LemonGreen
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                navController.navigate(HomeDest.route) {
                    popUpTo(HomeDest.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LemonYellow,
                contentColor = Secondary4
            )
        ) {
            Text("Menu")
        }

        Button(
            onClick = {
                sharedPreferences.edit().clear().apply()
                navController.navigate(OnboardingDest.route) {
                    popUpTo(OnboardingDest.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LemonYellow,
                contentColor = Secondary4
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
        val context = LocalContext.current
        val navController = NavController(LocalContext.current)
        Profile(context, navController)
    }
}