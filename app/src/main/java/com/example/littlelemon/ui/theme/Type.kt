package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R

val karla = FontFamily(Font(R.font.karla))
val markazi = FontFamily(Font(R.font.markazi_text))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = karla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = markazi,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),
    titleSmall = TextStyle(
        fontFamily = karla,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = LemonGreen
    )
)