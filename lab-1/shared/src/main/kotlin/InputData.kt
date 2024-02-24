package org.example

import kotlinx.serialization.Serializable

@Serializable
data class InputData(
    val n: Int,

    val B: DoubleArray,
    val MC: Array<DoubleArray>,
    val D: DoubleArray,
    val MZ: Array<DoubleArray>,
    val E: DoubleArray,
    val MM: Array<DoubleArray>,
    val MT: Array<DoubleArray>,
    val ME: Array<DoubleArray>
)
