package com.enfz.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

val typography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W100,
        fontSize = 96.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 44.sp,
        letterSpacing = 1.5.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),
//    h4 = TextStyle(
//        fontWeight = FontWeight.W700,
//        fontSize = 34.sp
//    ),
//    h5 = TextStyle(
//        fontWeight = FontWeight.W700,
//        fontSize = 24.sp
//    ),
//    h6 = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontSize = 18.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 3.sp
//    ),
//    subtitle1 = TextStyle(
//        fontWeight = FontWeight.Light,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 3.sp
//    ),
//    subtitle2 = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        letterSpacing = 0.1.em
//    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.1.em
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.em
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.em
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    ),
)
