package org.example

import java.util.concurrent.atomic.AtomicInteger

// MG=min(D+E)*MM*MT-MZ*ME;
class ExpressionMG(
    private val inputData: InputData
) {
    companion object {
        private val MIN_CALCULATION_LOCK = Any()
    }

    val n: Int
        get() = inputData.n

    var a = Double.MAX_VALUE

    // a = min(DH + EH)
    fun calc1(range: IntRange) {
        val rangeMin = inputData.D
            .slice(range)
            .zip(inputData.E.slice(range))
            .minOf { kahanSum(it.first, it.second) }

        synchronized(MIN_CALCULATION_LOCK) {
            if (rangeMin < a) {
                a = rangeMin
            }
        }
    }
}
