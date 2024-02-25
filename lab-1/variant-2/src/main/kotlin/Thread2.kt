package org.example

class Thread2(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG,
    private val n: Int,
) : Runnable {
    override fun run() {
        val calculationRange = (n / APP_THREADS_COUNT) until n

        expressionA.calc1(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc1(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc2(calculationRange)

        expressionMG.calculationsFinishBarrier.await()
    }
}
