package org.example

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Semaphore
import javax.swing.text.html.HTML.Tag.P

// А=В*МС+D*MZ+E*MM;
class ExpressionA(
    private val inputData: InputData
) {
    val n: Int
        get() = inputData.n

    val barrier = CyclicBarrier(APP_THREADS_COUNT);

    val A1 = DoubleArray(inputData.n)
    val A2 = DoubleArray(inputData.n)
    val A3 = DoubleArray(inputData.n)

    val result = DoubleArray(inputData.n)

    // A1 = B*MCH
    fun calc1(range: IntRange) {
        for (i in range) {
            val products = inputData.B.mapIndexed { j, el -> el * inputData.MC[j][i] }
            A1[i] = kahanSum(*products.toDoubleArray())
        }
    }

    // A2 = D*MZH
    fun calc2(range: IntRange) {
        for (i in range) {
            val products = inputData.D.mapIndexed { j, el -> el * inputData.MZ[j][i] }
            A2[i] = kahanSum(*products.toDoubleArray())
        }
    }

    // A3 = E*MMH
    fun calc3(range: IntRange) {
        for (i in range) {
            val products = inputData.E.mapIndexed { j, el -> el * inputData.MM[j][i] }
            A3[i] = kahanSum(*products.toDoubleArray())
        }
    }

    //AH = A1H + A2H + A3H
    fun calc4(range: IntRange) {
        for (i in range) {
            result[i] = kahanSum(A1[i], A2[i], A3[i])
        }
    }
}
