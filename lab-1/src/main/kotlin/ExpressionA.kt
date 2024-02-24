package org.example

// А=В*МС+D*MZ+E*MM;
class ExpressionA(
    private val B: DoubleArray,
    private val MC: Array<DoubleArray>,
    private val D: DoubleArray,
    private val MZ: Array<DoubleArray>,
    private val E: DoubleArray,
    private val MM: Array<DoubleArray>
) : Runnable {
    private fun calculate(): DoubleArray {
        // B*MC, D*MZ, E*MM
        val multipliedPairs = listOf(B to MC, D to MZ, E to MM)
            .map { multiplyVectorByMatrix(it.first, it.second) }

        // B*MC + D*MZ + E*MM
        val sum = DoubleArray(multipliedPairs.first().size) { i ->
            kahanSum(*multipliedPairs.map { it[i] }.toDoubleArray())
        }

        return sum
    }

    override fun run() {
        val A = calculate()
        printVector(A)
    }
}
