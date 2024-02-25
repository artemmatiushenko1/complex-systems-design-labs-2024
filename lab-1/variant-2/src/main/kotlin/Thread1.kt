package org.example

class Thread1(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG
) : Runnable {
    override fun run() {
        val n = expressionA.n
        val calculationRange = 0 until (n / APP_THREADS_COUNT)

        expressionA.calc1(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc1(calculationRange)

        expressionA.barrier.await()

        printVector(expressionA.result)

        expressionMG.calc2(calculationRange)

        expressionMG.calculationsFinishBarrier.await()

        printMatrix(expressionMG.result)
    }
}
