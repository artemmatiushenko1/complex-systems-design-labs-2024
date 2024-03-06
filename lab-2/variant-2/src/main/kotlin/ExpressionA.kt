package org.example

import java.util.concurrent.CyclicBarrier

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

            result[i] = kahanSum(*sumPerItem)
        }
    }
}
