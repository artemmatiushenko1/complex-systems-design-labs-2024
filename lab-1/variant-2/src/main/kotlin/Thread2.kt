package org.example

class Thread2(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG,
) : Runnable {
    override fun run() {
        val n = expressionA.n
        val calculationRange = (n / APP_THREADS_COUNT) until n

        expressionA.calc1(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc1(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc2(calculationRange)

        expressionMG.calculationsFinishBarrier.await()
    }
}
