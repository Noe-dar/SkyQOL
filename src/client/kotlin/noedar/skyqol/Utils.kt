package noedar.skyqol

import kotlin.math.abs

fun equalWithError(lhs: Double, rhs: Double, error: Double): Boolean {
    return abs(lhs - rhs) < error
}

data class Color(val red: Float, val green: Float, val blue: Float) {
    companion object {
        operator fun invoke(color: Int): Color {
            val red = ((color shr 16) and 0xFF) / 255.0F
            val green = ((color shr 8) and 0xFF) / 255.0F
            val blue = (color and 0xFF) / 255.0F

            return Color(red, green, blue)
        }
    }
}
