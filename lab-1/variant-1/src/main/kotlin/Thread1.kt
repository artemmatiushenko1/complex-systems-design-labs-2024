package org.example

class Thread1(
    private val expressionA: ExpressionA,
    private val printOutput: Boolean = false,
) : Runnable {
    override fun run() {
        val result = expressionA.calculate()

        if (printOutput) {
            printVector(result)
        }
    }
}
