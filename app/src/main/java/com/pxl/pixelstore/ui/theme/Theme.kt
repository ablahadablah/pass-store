package com.pxl.pixelstore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

private object Colors {
    val Haiti = Color(0xFF1B1340)
    val Valhalla = Color(0xFF271B54)
    val Jacarta = Color(0xFF2E2260)
    val Chambray = Color(0xFF372580)
    val SanMarino = Color(0xFF4D34B3)
    val CornflowerBlue = Color(0xFF6D4AFF)
    val Portage = Color(0xFF8A6EFF)
    val Perano = Color(0xFFC4B7FF)

    val BalticSea = Color(0xFF1C1B24)
    val Bastille = Color(0xFF292733)
    val SteelGray = Color(0xFF343140)
    val BlackCurrant = Color(0xFF3B3747)
    val GunPowder = Color(0xFF4A4658)
    val Smoky = Color(0xFF5B576B)
    val Dolphin = Color(0xFF6D697D)
    val CadetBlue = Color(0xFFA7A4B5)
    val Cinder = Color(0xFF0C0C14)
    val ShipGray = Color(0xFF35333D)
    val DoveGray = Color(0xFF706D6B)
    val Dawn = Color(0xFF999693)
    val CottonSeed = Color(0xFFC2BFBC)
    val Cloud = Color(0xFFD1CFCD)
    val Ebb = Color(0xFFEAE7E4)
    val Pampas = Color(0xFFF1EEEB)
    val Carrara = Color(0xFFF5F4F2)
    val White = Color(0xFFFFFFFF)

    val Woodsmoke = Color(0xFF17181C)

    val Pomegranate = Color(0xFFCC2D4F)
    val Mauvelous = Color(0xFFF08FA4)
    val Sunglow = Color(0xFFE65200)
    val TexasRose = Color(0xFFFFB84D)
    val Apple = Color(0xFF007B58)
    val PuertoRico = Color(0xFF4AB89A)
}

@Stable
class ColorTheme(
    isDark: Boolean,

    shade100: Color,
    shade80: Color,
    shade60: Color,
    shade50: Color,
    shade40: Color,
    shade20: Color,
    shade15: Color,
    shade10: Color,
    shade0: Color,

    brandDarken40: Color = Colors.Chambray,
    brandDarken20: Color = Colors.SanMarino,
    brandNorm: Color = Colors.CornflowerBlue,
    brandLighten20: Color = Colors.Portage,
    brandLighten40: Color = Colors.Perano,

    textNorm: Color = shade100,
    textAccent: Color = brandNorm,
    textWeak: Color = shade80,
    textHint: Color = shade60,
    textDisabled: Color = shade50,
    textInverted: Color = shade0,

    iconNorm: Color = shade100,
    iconAccent: Color = brandNorm,
    iconWeak: Color = shade80,
    iconHint: Color = shade60,
    iconDisabled: Color = shade50,
    iconInverted: Color = shade0,

    interactionStrongNorm: Color = shade100,
    interactionStrongPressed: Color = shade80,

    interactionWeakNorm: Color = shade20,
    interactionWeakPressed: Color = shade40,
    interactionWeakDisabled: Color = shade10,

    backgroundNorm: Color = shade0,
    backgroundSecondary: Color = shade10,
    backgroundDeep: Color = shade15,

    separatorNorm: Color = shade20,

    blenderNorm: Color,

    notificationNorm: Color = shade100,
    notificationError: Color = Colors.Pomegranate,
    notificationWarning: Color = Colors.Sunglow,
    notificationSuccess: Color = Colors.Apple,

    interactionNorm: Color = brandNorm,
    interactionPressed: Color = brandDarken20,
    interactionDisabled: Color = brandLighten40,

    floatyBackground: Color = Colors.ShipGray,
    floatyPressed: Color = Colors.Cinder,
    floatyText: Color = Color.White,

    shadowNorm: Color,
    shadowRaised: Color,
    shadowLifted: Color,

    sidebarColors: ColorTheme? = null,
) {
    var isDark: Boolean by mutableStateOf(isDark, structuralEqualityPolicy())
        internal set

    var shade100: Color by mutableStateOf(shade100, structuralEqualityPolicy())
        internal set
    var shade80: Color by mutableStateOf(shade80, structuralEqualityPolicy())
        internal set
    var shade60: Color by mutableStateOf(shade60, structuralEqualityPolicy())
        internal set
    var shade50: Color by mutableStateOf(shade50, structuralEqualityPolicy())
        internal set
    var shade40: Color by mutableStateOf(shade40, structuralEqualityPolicy())
        internal set
    var shade20: Color by mutableStateOf(shade20, structuralEqualityPolicy())
        internal set
    var shade15: Color by mutableStateOf(shade15, structuralEqualityPolicy())
        internal set
    var shade10: Color by mutableStateOf(shade10, structuralEqualityPolicy())
        internal set
    var shade0: Color by mutableStateOf(shade0, structuralEqualityPolicy())
        internal set

    var textNorm: Color by mutableStateOf(textNorm, structuralEqualityPolicy())
        internal set
    var textAccent: Color by mutableStateOf(textAccent, structuralEqualityPolicy())
        internal set
    var textWeak: Color by mutableStateOf(textWeak, structuralEqualityPolicy())
        internal set
    var textHint: Color by mutableStateOf(textHint, structuralEqualityPolicy())
        internal set
    var textDisabled: Color by mutableStateOf(textDisabled, structuralEqualityPolicy())
        internal set
    var textInverted: Color by mutableStateOf(textInverted, structuralEqualityPolicy())
        internal set

    var iconNorm: Color by mutableStateOf(iconNorm, structuralEqualityPolicy())
        internal set
    var iconAccent: Color by mutableStateOf(iconAccent, structuralEqualityPolicy())
        internal set
    var iconWeak: Color by mutableStateOf(iconWeak, structuralEqualityPolicy())
        internal set
    var iconHint: Color by mutableStateOf(iconHint, structuralEqualityPolicy())
        internal set
    var iconDisabled: Color by mutableStateOf(iconDisabled, structuralEqualityPolicy())
        internal set
    var iconInverted: Color by mutableStateOf(iconInverted, structuralEqualityPolicy())
        internal set

    var interactionStrongNorm: Color by mutableStateOf(interactionStrongNorm, structuralEqualityPolicy())
        internal set
    var interactionStrongPressed: Color by mutableStateOf(interactionStrongPressed, structuralEqualityPolicy())
        internal set

    var interactionWeakNorm: Color by mutableStateOf(interactionWeakNorm, structuralEqualityPolicy())
        internal set
    var interactionWeakPressed: Color by mutableStateOf(interactionWeakPressed, structuralEqualityPolicy())
        internal set
    var interactionWeakDisabled: Color by mutableStateOf(interactionWeakDisabled, structuralEqualityPolicy())
        internal set

    var backgroundNorm: Color by mutableStateOf(backgroundNorm, structuralEqualityPolicy())
        internal set
    var backgroundSecondary: Color by mutableStateOf(backgroundSecondary, structuralEqualityPolicy())
        internal set
    var backgroundDeep: Color by mutableStateOf(backgroundDeep, structuralEqualityPolicy())
        internal set

    var separatorNorm: Color by mutableStateOf(separatorNorm, structuralEqualityPolicy())
        internal set

    var blenderNorm: Color by mutableStateOf(blenderNorm, structuralEqualityPolicy())
        internal set

    var brandDarken40: Color by mutableStateOf(brandDarken40, structuralEqualityPolicy())
        internal set
    var brandDarken20: Color by mutableStateOf(brandDarken20, structuralEqualityPolicy())
        internal set
    var brandNorm: Color by mutableStateOf(brandNorm, structuralEqualityPolicy())
        internal set
    var brandLighten20: Color by mutableStateOf(brandLighten20, structuralEqualityPolicy())
        internal set
    var brandLighten40: Color by mutableStateOf(brandLighten40, structuralEqualityPolicy())
        internal set

    var notificationNorm: Color by mutableStateOf(notificationNorm, structuralEqualityPolicy())
        internal set
    var notificationError: Color by mutableStateOf(notificationError, structuralEqualityPolicy())
        internal set
    var notificationWarning: Color by mutableStateOf(notificationWarning, structuralEqualityPolicy())
        internal set
    var notificationSuccess: Color by mutableStateOf(notificationSuccess, structuralEqualityPolicy())
        internal set

    var interactionNorm: Color by mutableStateOf(interactionNorm, structuralEqualityPolicy())
        internal set
    var interactionPressed: Color by mutableStateOf(interactionPressed, structuralEqualityPolicy())
        internal set
    var interactionDisabled: Color by mutableStateOf(interactionDisabled, structuralEqualityPolicy())
        internal set

    var floatyBackground: Color by mutableStateOf(floatyBackground, structuralEqualityPolicy())
        internal set
    var floatyPressed: Color by mutableStateOf(floatyPressed, structuralEqualityPolicy())
        internal set
    var floatyText: Color by mutableStateOf(floatyText, structuralEqualityPolicy())
        internal set

    var shadowNorm: Color by mutableStateOf(shadowNorm, structuralEqualityPolicy())
        internal set
    var shadowRaised: Color by mutableStateOf(shadowRaised, structuralEqualityPolicy())
        internal set
    var shadowLifted: Color by mutableStateOf(shadowLifted, structuralEqualityPolicy())
        internal set

    var sidebarColors: ColorTheme? by mutableStateOf(sidebarColors, structuralEqualityPolicy())

    fun copy(
        isDark: Boolean = this.isDark,

        shade100: Color = this.shade100,
        shade80: Color = this.shade80,
        shade60: Color = this.shade60,
        shade50: Color = this.shade50,
        shade40: Color = this.shade40,
        shade20: Color = this.shade20,
        shade15: Color = this.shade15,
        shade10: Color = this.shade10,
        shade0: Color = this.shade0,

        textNorm: Color = this.textNorm,
        textAccent: Color = this.textAccent,
        textWeak: Color = this.textWeak,
        textHint: Color = this.textHint,
        textDisabled: Color = this.textDisabled,
        textInverted: Color = this.textInverted,

        iconNorm: Color = this.iconNorm,
        iconAccent: Color = this.iconAccent,
        iconWeak: Color = this.iconWeak,
        iconHint: Color = this.iconHint,
        iconDisabled: Color = this.iconDisabled,
        iconInverted: Color = this.iconInverted,

        interactionStrongNorm: Color = this.interactionStrongNorm,
        interactionStrongPressed: Color = this.interactionStrongPressed,

        interactionWeakNorm: Color = this.interactionWeakNorm,
        interactionWeakPressed: Color = this.interactionWeakPressed,
        interactionWeakDisabled: Color = this.interactionWeakDisabled,

        backgroundNorm: Color = this.backgroundNorm,
        backgroundSecondary: Color = this.backgroundSecondary,
        backgroundDeep: Color = this.backgroundDeep,

        separatorNorm: Color = this.separatorNorm,

        blenderNorm: Color = this.blenderNorm,

        brandDarken40: Color = this.brandDarken40,
        brandDarken20: Color = this.brandDarken20,
        brandNorm: Color = this.brandNorm,
        brandLighten20: Color = this.brandLighten20,
        brandLighten40: Color = this.brandLighten40,

        notificationNorm: Color = this.notificationNorm,
        notificationError: Color = this.notificationError,
        notificationWarning: Color = this.notificationWarning,
        notificationSuccess: Color = this.notificationSuccess,

        interactionNorm: Color = this.interactionNorm,
        interactionPressed: Color = this.interactionPressed,
        interactionDisabled: Color = this.interactionDisabled,

        floatyBackground: Color = this.floatyBackground,
        floatyPressed: Color = this.floatyPressed,
        floatyText: Color = this.floatyText,

        shadowNorm: Color = this.shadowNorm,
        shadowRaised: Color = this.shadowRaised,
        shadowLifted: Color = this.shadowLifted,

        sidebarColors: ColorTheme? = this.sidebarColors,
    ) = ColorTheme(
        isDark = isDark,

        shade100 = shade100,
        shade80 = shade80,
        shade60 = shade60,
        shade50 = shade50,
        shade40 = shade40,
        shade20 = shade20,
        shade15 = shade15,
        shade10 = shade10,
        shade0 = shade0,

        textNorm = textNorm,
        textAccent = textAccent,
        textWeak = textWeak,
        textHint = textHint,
        textDisabled = textDisabled,
        textInverted = textInverted,

        iconNorm = iconNorm,
        iconAccent = iconAccent,
        iconWeak = iconWeak,
        iconHint = iconHint,
        iconDisabled = iconDisabled,
        iconInverted = iconInverted,

        interactionStrongNorm = interactionStrongNorm,
        interactionStrongPressed = interactionStrongPressed,

        interactionWeakNorm = interactionWeakNorm,
        interactionWeakPressed = interactionWeakPressed,
        interactionWeakDisabled = interactionWeakDisabled,

        backgroundNorm = backgroundNorm,
        backgroundSecondary = backgroundSecondary,
        backgroundDeep = backgroundDeep,

        separatorNorm = separatorNorm,

        blenderNorm = blenderNorm,

        brandDarken40 = brandDarken40,
        brandDarken20 = brandDarken20,
        brandNorm = brandNorm,
        brandLighten20 = brandLighten20,
        brandLighten40 = brandLighten40,

        notificationNorm = notificationNorm,
        notificationError = notificationError,
        notificationWarning = notificationWarning,
        notificationSuccess = notificationSuccess,

        interactionNorm = interactionNorm,
        interactionPressed = interactionPressed,
        interactionDisabled = interactionDisabled,

        floatyBackground = floatyBackground,
        floatyPressed = floatyPressed,
        floatyText = floatyText,

        shadowNorm = shadowNorm,
        shadowRaised = shadowRaised,
        shadowLifted = shadowLifted,

        sidebarColors = sidebarColors,
    )

    companion object {

        val Light = baseLight().copy(sidebarColors = sidebarLight())
        val Dark = baseDark().copy(sidebarColors = sidebarDark())

        private fun baseLight(
            brandDarken40: Color = Colors.Chambray,
            brandDarken20: Color = Colors.SanMarino,
            brandNorm: Color = Colors.CornflowerBlue,
            brandLighten20: Color = Colors.Portage,
            brandLighten40: Color = Colors.Perano,
        ) = ColorTheme(
            isDark = false,
            brandDarken40 = brandDarken40,
            brandDarken20 = brandDarken20,
            brandNorm = brandNorm,
            brandLighten20 = brandLighten20,
            brandLighten40 = brandLighten40,
            notificationError = Colors.Pomegranate,
            notificationWarning = Colors.Sunglow,
            notificationSuccess = Colors.Apple,
            shade100 = Colors.Cinder,
            shade80 = Colors.DoveGray,
            shade60 = Colors.Dawn,
            shade50 = Colors.CottonSeed,
            shade40 = Colors.Cloud,
            shade20 = Colors.Ebb,
            shade15 = Colors.Pampas,
            shade10 = Colors.Carrara,
            shade0 = Color.White,
            shadowNorm = Color.Black.copy(alpha = 0.1f),
            shadowRaised = Color.Black.copy(alpha = 0.1f),
            shadowLifted = Color.Black.copy(alpha = 0.1f),
            blenderNorm = Colors.Woodsmoke.copy(alpha = 0.48f),
            textAccent = brandNorm,
            iconAccent = brandNorm,
        )

        private fun baseDark(
            brandDarken40: Color = Colors.Chambray,
            brandDarken20: Color = Colors.SanMarino,
            brandNorm: Color = Colors.CornflowerBlue,
            brandLighten20: Color = Colors.Portage,
            brandLighten40: Color = Colors.Perano,
        ) = ColorTheme(
            isDark = true,
            brandDarken40 = brandDarken40,
            brandDarken20 = brandDarken20,
            brandNorm = brandNorm,
            brandLighten20 = brandLighten20,
            brandLighten40 = brandLighten40,
            notificationError = Colors.Mauvelous,
            notificationWarning = Colors.TexasRose,
            notificationSuccess = Colors.PuertoRico,
            shade100 = Color.White,
            shade80 = Colors.CadetBlue,
            shade60 = Colors.Dolphin,
            shade50 = Colors.Smoky,
            shade40 = Colors.GunPowder,
            shade20 = Colors.BlackCurrant,
            shade15 = Colors.Bastille,
            shade10 = Colors.BalticSea,
            shade0 = Colors.Cinder,
            shadowNorm = Color.Black.copy(alpha = 0.8f),
            shadowRaised = Color.Black.copy(alpha = 0.8f),
            shadowLifted = Color.Black.copy(alpha = 0.86f),
            blenderNorm = Color.Black.copy(alpha = 0.52f),
            textAccent = brandLighten20,
            iconAccent = brandLighten20,
        ).let {
            it.copy(
                interactionWeakNorm = it.shade20,
                interactionWeakPressed = it.shade40,
                interactionWeakDisabled = it.shade15,
                interactionDisabled = it.brandDarken40,
                backgroundNorm = it.shade10,
                backgroundSecondary = it.shade15,
                backgroundDeep = it.shade0,
            )
        }

        private fun sidebarLight(
            brandDarken40: Color = Colors.Chambray,
            brandDarken20: Color = Colors.SanMarino,
            brandNorm: Color = Colors.CornflowerBlue,
            brandLighten20: Color = Colors.Portage,
            brandLighten40: Color = Colors.Perano,
        ) = baseLight(
            brandDarken40 = brandDarken40,
            brandDarken20 = brandDarken20,
            brandNorm = brandNorm,
            brandLighten20 = brandLighten20,
            brandLighten40 = brandLighten40,
        ).copy(
            backgroundNorm = Colors.Haiti,
            interactionWeakNorm = Colors.Jacarta,
            interactionWeakPressed = Colors.Valhalla,
            separatorNorm = Colors.Jacarta,
            textNorm = Colors.White,
            textWeak = Colors.CadetBlue,
            iconNorm = Colors.White,
            iconWeak = Colors.CadetBlue,
            interactionPressed = Colors.SanMarino,
        )

        private fun sidebarDark(
            brandDarken40: Color = Colors.Chambray,
            brandDarken20: Color = Colors.SanMarino,
            brandNorm: Color = Colors.CornflowerBlue,
            brandLighten20: Color = Colors.Portage,
            brandLighten40: Color = Colors.Perano,
        ) = baseDark(
            brandDarken40 = brandDarken40,
            brandDarken20 = brandDarken20,
            brandNorm = brandNorm,
            brandLighten20 = brandLighten20,
            brandLighten40 = brandLighten40,
        ).copy(
            backgroundNorm = Colors.Cinder,
            interactionWeakNorm = Colors.BlackCurrant,
            interactionWeakPressed = Colors.GunPowder,
            separatorNorm = Colors.BlackCurrant,
            textNorm = Colors.White,
            textWeak = Colors.CadetBlue,
            iconNorm = Colors.White,
            iconWeak = Colors.CadetBlue,
            interactionPressed = Colors.SanMarino,
        )
    }
}

internal fun ColorTheme.toMaterial3ThemeColors() = ColorScheme(
    primary = brandNorm,
    onPrimary = Color.White,
    primaryContainer = backgroundNorm,
    onPrimaryContainer = textNorm,
    inversePrimary = Color.White,
    secondary = brandNorm,
    onSecondary = Color.White,
    secondaryContainer = backgroundSecondary,
    onSecondaryContainer = textNorm,
    tertiary = brandDarken20,
    onTertiary = Color.White,
    tertiaryContainer = backgroundNorm,
    onTertiaryContainer = textNorm,
    background = backgroundNorm,
    onBackground = textNorm,
    surface = backgroundNorm,
    onSurface = textNorm,
    surfaceVariant = backgroundNorm,
    onSurfaceVariant = textNorm,
    inverseSurface = backgroundNorm,
    inverseOnSurface = textNorm,
    error = notificationError,
    onError = textInverted,
    errorContainer = backgroundNorm,
    onErrorContainer = textNorm,
    outline = brandNorm,
    surfaceTint = Color.Unspecified,
    outlineVariant = brandNorm,
    scrim = blenderNorm,
)

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

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
         ColorTheme.Dark
    } else {
        ColorTheme.Light
    }

    MaterialTheme(
        colorScheme = colorScheme.toMaterial3ThemeColors(),
        typography = typography,
        content = content
    )
}