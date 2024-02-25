package org.example

import kotlinx.serialization.Serializable

@Serializable
data class CalculationResult(
    val A: DoubleArray,
    val MG: Array<DoubleArray>
)
