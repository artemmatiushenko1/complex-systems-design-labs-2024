package org.example

class Thread2(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG,
    private val n: Int,
    private val printOutput: Boolean = false,
) : Runnable {
    override fun run() {
        val calculationRange = (n / APP_THREADS_COUNT) until n

        expressionA.calc1(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc1(calculationRange)

        expressionMG.barrier.await()

        expressionMG.calc2(calculationRange)

        expressionMG.barrier.await()

        if (printOutput) {
            printMatrix(expressionMG.result, "${Thread2::class.simpleName} - calculation of expression MG completed")
        }
    }
}
