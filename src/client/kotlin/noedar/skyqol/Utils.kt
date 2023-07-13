package noedar.skyqol

import kotlin.math.abs

fun equalWithError(lhs: Double, rhs: Double, error: Double): Boolean {
    return abs(lhs - rhs) < error
}