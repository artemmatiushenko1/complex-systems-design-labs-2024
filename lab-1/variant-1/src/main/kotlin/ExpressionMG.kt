package org.example

// MG=min(D+E)*MM*MT-MZ*ME
class ExpressionMG(
    private val D: DoubleArray,
    private val E: DoubleArray,
    private val MM: Array<DoubleArray>,
    private val MT: Array<DoubleArray>,
    private val MZ: Array<DoubleArray>,
    private val ME: Array<DoubleArray>,
    private val printOutput: Boolean,
) : Runnable {
    private fun calculate(): Array<DoubleArray> {
        // min(D + E)
        val scalar = D.zip(E).minOf { kahanSum(it.first, it.second) }

        // min(D + E) * MM
        val scalarByMatrixProduct = MM.map { row -> row.map { col -> col * scalar }.toDoubleArray() }
            .toTypedArray()

        // (min(D + E) * MM) * MT
        val minuend = multiplyMatrices(scalarByMatrixProduct, MT)

        // MZ * ME
        val subtrahend = multiplyMatrices(MZ, ME)

        // (min(D + E) * MM) * MT - MZ * ME
        val difference = subtractMatrices(minuend, subtrahend)

        return difference
    }

    override fun run() {
        val result = calculate()

        if (printOutput) {
            printMatrix(result)
        }
    }
}
