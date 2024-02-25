package org.example

class Thread1(
    private val expressionA: ExpressionA,
    private val expressionMG: ExpressionMG
) : Runnable {
    override fun run() {
        val n = expressionA.n
        val calculationRange = 0 until (n / 2)

        expressionA.calc1(calculationRange)

        expressionA.barrier.await()

        printVector(expressionA.result)

        expressionMG.calc1(calculationRange)

        expressionA.barrier.await()

//        println("Min = ${expressionMG.a}")
    }
}
