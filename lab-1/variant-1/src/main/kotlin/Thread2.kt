package org.example

class Thread2(
    private val expressionMG: ExpressionMG,
    private val printOutput: Boolean = false,
) : Runnable {
    override fun run() {
        expressionMG.calculate()

        println("${Thread2::class.simpleName} - calculation of expression MG completed")
        if (printOutput) {
            printMatrix(expressionMG.result)
        }
    }
}
