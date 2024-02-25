package org.example

// А=В*МС+D*MZ+E*MM;
class ExpressionA(
    private val B: DoubleArray,
    private val MC: Array<DoubleArray>,
    private val D: DoubleArray,
    private val MZ: Array<DoubleArray>,
    private val E: DoubleArray,
    private val MM: Array<DoubleArray>,
    private val n: Int,
) {
    var result: DoubleArray = DoubleArray(n)

    fun calculate() {
        for (i in 0 until n) {
            val sumPerItem = DoubleArray(n) { j ->
                kahanSum(
                    B[j] * MC[j][i],
                    D[j] * MZ[j][i],
                    E[j] * MM[j][i]
                )
            }

            result[i] = kahanSum(*sumPerItem)
        }
    }
}
