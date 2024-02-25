package org.example

class Thread1(
    private val expressionA: ExpressionA,
    private val printOutput: Boolean = false,
) : Runnable {
    override fun run() {
        expressionA.calculate()

        println("${Thread1::class.simpleName} - calculation of expression A completed")
        if (printOutput) {
            printVector(expressionA.result)
        }
    }
}
