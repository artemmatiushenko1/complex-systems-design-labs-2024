package org.example

class Thread1(
    private val expressionA: ExpressionA,
    private val printOutput: Boolean = false,
) : Runnable {
    override fun run() {
        expressionA.calculate()
        
        if (printOutput) {
            printVector(expressionA.result, "${Thread1::class.simpleName} - calculation of expression A completed")
        }
    }
}
