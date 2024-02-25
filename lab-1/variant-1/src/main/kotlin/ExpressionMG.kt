package org.example

// MG=min(D+E)*MM*MT-MZ*ME
class ExpressionMG(
    private val D: DoubleArray,
    private val E: DoubleArray,
    private val MM: Array<DoubleArray>,
    private val MT: Array<DoubleArray>,
    private val MZ: Array<DoubleArray>,
    private val ME: Array<DoubleArray>,
    private val n: Int,
) {
    var result = Array(n) { DoubleArray(n) }

    fun calculate() {
        // a=min(D + E)
        val a = D.zip(E).minOf { kahanSum(it.first, it.second) }

        // MG=a*MM*MT-MZ*ME
        for (i in 0 until n) {
            for (j in 0 until n) {
                val products1 = DoubleArray(n) { k -> MM[j][k] * MT[k][i] }
                val products2 = DoubleArray(n) { k -> MZ[j][k] * ME[k][i] }

                result[j][i] = a * kahanSum(*products1) - kahanSum(*products2)
            }
        }
    }
}
