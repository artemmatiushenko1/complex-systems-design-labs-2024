package org.example

class Thread2(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG
) : Runnable {
    override fun run() {
        val n = expressionA.n
        val calculationRange = (n / 2) until n

        expressionA.calc1(calculationRange)
        expressionA.calc2(calculationRange)
        expressionA.calc3(calculationRange)

        expressionA.barrier.await()

        expressionA.calc4(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc1(calculationRange)

        expressionA.barrier.await()
    }
}
