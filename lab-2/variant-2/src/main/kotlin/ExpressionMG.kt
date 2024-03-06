package org.example

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock

// MG=min(D+E)*MM*MT-MZ*ME;
class ExpressionMG(
    private val D: DoubleArray,
    private val E: DoubleArray,
    private val MM: Array<DoubleArray>,
    private val MT: Array<DoubleArray>,
    private val MZ: Array<DoubleArray>,
    private val ME: Array<DoubleArray>,
    private val n: Int,
) {
    private var a = Double.MAX_VALUE

    val result = Array(n) { DoubleArray(n) }

    companion object {
        private val MIN_CALCULATION_LOCK = Any()
        private val FINAL_CALCULATION_LOCK = ReentrantLock()
    }

    val barrier = CyclicBarrier(APP_THREADS_COUNT)

    // a=min(DH+EH)
    fun calc1(range: IntRange) {
        val rangeMin = D
            .slice(range)
            .zip(E.slice(range))
            .minOf { kahanSum(it.first, it.second) }

        synchronized(MIN_CALCULATION_LOCK) {
            if (rangeMin < a) {
                a = rangeMin
            }
        }
    }

    // MGH=a*MM*MTH-MZ*MEH;
    fun calc2(range: IntRange) {
        for (i in range) {
            for (j in 0 until n) {
                val products1 = DoubleArray(n) { k -> MM[j][k] * MT[k][i] }
                val products2 = DoubleArray(n) { k -> MZ[j][k] * ME[k][i] }

                FINAL_CALCULATION_LOCK.lock()
                try {
                    result[j][i] = a * kahanSum(*products1) - kahanSum(*products2)
                } finally {
                    FINAL_CALCULATION_LOCK.unlock()
                }
            }
        }
    }
}
