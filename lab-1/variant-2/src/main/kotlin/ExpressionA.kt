package org.example

import java.util.concurrent.CyclicBarrier

// А=В*МС+D*MZ+E*MM;
class ExpressionA(
    private val inputData: InputData,
) {
    val n: Int
        get() = inputData.n

    val barrier = CyclicBarrier(APP_THREADS_COUNT)

    val result = DoubleArray(inputData.n)

    // AH = В*МСH + D*MZH + E*MMH
    fun calc1(range: IntRange) {
        for (i in range) {
            val sumPerItem = DoubleArray(n) { j ->
                kahanSum(
                    inputData.B[j] * inputData.MC[j][i],
                    inputData.D[j] * inputData.MZ[j][i],
                    inputData.E[j] * inputData.MM[j][i]
                )
            }

            result[i] = kahanSum(*sumPerItem)
        }
    }
}
