package org.example

class Thread2(
    private val expressionMG: ExpressionMG,
    private val printOutput: Boolean = false,
) : Runnable {
    override fun run() {
        val result = expressionMG.calculate()

        if (printOutput) {
            printMatrix(result)
        }
    }
}
