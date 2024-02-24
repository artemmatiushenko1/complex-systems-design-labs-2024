package org.example

class Thread2(
    private val expressionA: ExpressionA
) : Runnable {
    override fun run() {
        val n = expressionA.n
        val calculationRange = (n / 2) until n

        this.expressionA.calc1(calculationRange)
        this.expressionA.calc2(calculationRange)
        this.expressionA.calc3(calculationRange)

        expressionA.barrier.await()

        expressionA.calc4(calculationRange)

        expressionA.barrier.await()
    }
}
