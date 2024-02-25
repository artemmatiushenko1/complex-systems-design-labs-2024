package org.example

class Thread1(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG,
    private val n: Int,
    private val printOutput: Boolean = false,
) : Runnable {
    override fun run() {
        val calculationRange = 0 until (n / APP_THREADS_COUNT)

        expressionA.calc1(calculationRange)

        expressionA.barrier.await()

        println("${Thread1::class.simpleName} - calculation of expression A completed")
        if (printOutput) {
            printVector(expressionA.result)
        }

        expressionMG.calc1(calculationRange)

        expressionMG.barrier.await()

        expressionMG.calc2(calculationRange)

        expressionMG.barrier.await()
    }
}
