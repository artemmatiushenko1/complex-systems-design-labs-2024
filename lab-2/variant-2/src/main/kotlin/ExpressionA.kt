package org.example

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock

// А=В*МС+D*MZ+E*MM;
class ExpressionA(
    private val B: DoubleArray,
    private val MC: Array<DoubleArray>,
    private val D: DoubleArray,
    private val MZ: Array<DoubleArray>,
    private val E: DoubleArray,
    private val MM: Array<DoubleArray>,
    private val n: Int,
) {
    val barrier = CyclicBarrier(APP_THREADS_COUNT)

    val result = DoubleArray(n)

    companion object {
        private val FINAL_CALCULATION_LOCK = ReentrantLock()
    }

    // AH = В*МСH + D*MZH + E*MMH
    fun calc1(range: IntRange) {
        for (i in range) {
            val sumPerItem = DoubleArray(n) { j ->
                kahanSum(
                    B[j] * MC[j][i],
                    D[j] * MZ[j][i],
                    E[j] * MM[j][i]
                )
            }

            FINAL_CALCULATION_LOCK.lock()
            try {
                result[i] = kahanSum(*sumPerItem)
            } finally {
                FINAL_CALCULATION_LOCK.unlock()
            }
        }
    }
}
