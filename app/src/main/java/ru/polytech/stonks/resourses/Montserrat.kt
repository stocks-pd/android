package ru.polytech.stonks.resourses

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.polytech.stonks.R

object Montserrat {

    private val montserrat = FontFamily(
        listOf(
//            Font(
//                resId = R.font.montserrat_black,
//                weight = FontWeight.Black,
//                style = FontStyle.Normal
//            ),
//            Font(
//                resId = R.font.montserrat_black_italic,
//                weight = FontWeight.Black,
//                style = FontStyle.Italic
//            ),
            Font(
                resId = R.font.montserrat_bold,
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            ),
//            Font(
//                resId = R.font.montserrat_bold_italic,
//                weight = FontWeight.Bold,
//                style = FontStyle.Italic
//            ),
//            Font(
//                resId = R.font.montserrat_extra_bold,
//                weight = FontWeight.ExtraBold,
//                style = FontStyle.Normal
//            ),
//            Font(
//                resId = R.font.montserrat_extra_bold_italic,
//                weight = FontWeight.ExtraBold,
//                style = FontStyle.Italic
//            ),
//            Font(
//                resId = R.font.montserrat_extra_light,
//                weight = FontWeight.ExtraBold,
//                style = FontStyle.Normal
//            ),
//            Font(
//                resId = R.font.montserrat_extra_light_italic,
//                weight = FontWeight.ExtraLight,
//                style = FontStyle.Italic
//            ),
//            Font(
//                resId = R.font.montserrat_italic,
//                weight = FontWeight.Normal,
//                style = FontStyle.Italic
//            ),
//            Font(
//                resId = R.font.montserrat_light,
//                weight = FontWeight.Light,
//                style = FontStyle.Normal
//            ),
//            Font(
//                resId = R.font.montserrat_light_italic,
//                weight = FontWeight.Light,
//                style = FontStyle.Italic
//            ),
//            Font(
//                resId = R.font.montserrat_medium,
//                weight = FontWeight.Medium,
//                style = FontStyle.Normal
//            ),
//            Font(
//                resId = R.font.montserrat_medium_italic,
//                weight = FontWeight.Medium,
//                style = FontStyle.Italic
//            ),
            Font(
                resId = R.font.montserrat_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            ),
            Font(
                resId = R.font.montserrat_semi_bold,
                weight = FontWeight.SemiBold,
                style = FontStyle.Normal
            ),
//            Font(
//                resId = R.font.montserrat_semi_bold_italic,
//                weight = FontWeight.SemiBold,
//                style = FontStyle.Italic
//            ),
//            Font(
//                resId = R.font.montserrat_thin,
//                weight = FontWeight.Thin,
//                style = FontStyle.Normal
//            ),
//            Font(
//                resId = R.font.montserrat_thin_italic,
//                weight = FontWeight.Thin,
//                style = FontStyle.Italic
//            ),
        )
    )


    object SemiBold600 {
        val SP10 = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W600,
            fontStyle = FontStyle.Normal,
            fontSize = 10.sp,
            lineHeight = 16.sp
        )

        val SP16 = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W600,
            fontStyle = FontStyle.Normal,
            fontSize = 16.sp,
            lineHeight = 16.sp
        )

        val SP18 = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W600,
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            lineHeight = 16.sp
        )
    }

    object Medium500 {
        val SP14 = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W500,
            fontStyle = FontStyle.Normal,
            fontSize = 14.sp,
            lineHeight = 16.sp
        )
    }

    object Bold700 {
        val SP18 = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W700,
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            lineHeight = 16.sp
        )
    }
}