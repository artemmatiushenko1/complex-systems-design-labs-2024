package org.example

class Thread1(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG,
    private val printOutput: Boolean,
) : Runnable {
    override fun run() {
        val n = expressionA.n
        val calculationRange = 0 until (n / APP_THREADS_COUNT)

        expressionA.calc1(calculationRange)

        expressionA.barrier.await()

        expressionMG.calc1(calculationRange)

        expressionA.barrier.await()

        if (printOutput) {
            printVector(expressionA.result)
        }

        expressionMG.calc2(calculationRange)

        expressionMG.calculationsFinishBarrier.await()

        if (printOutput) {
            printMatrix(expressionMG.result)
        }
    }
}
