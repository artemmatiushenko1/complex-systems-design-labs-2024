package org.example

// А=В*МС+D*MZ+E*MM;
class ExpressionA(
    private val B: DoubleArray,
    private val MC: Array<DoubleArray>,
    private val D: DoubleArray,
    private val MZ: Array<DoubleArray>,
    private val E: DoubleArray,
    private val MM: Array<DoubleArray>
) {
    private fun multiplyVectorByMatrix(vector: DoubleArray, matrix: Array<DoubleArray>): DoubleArray {
        val result = DoubleArray(vector.size)

        for (i in matrix.indices) {
            val products = vector.mapIndexed { j, el -> el * matrix[j][i] }
            result[i] = kahanSum(*products.toDoubleArray())
        }

        return result
    }

    fun calculate(): DoubleArray {
        val multipliedPairs = listOf(B to MC, D to MZ, E to MM)
            .map { multiplyVectorByMatrix(it.first, it.second) }

        return DoubleArray(multipliedPairs.first().size) { i ->
            kahanSum(*multipliedPairs.map { it[i] }.toDoubleArray())
        }
    }
}
