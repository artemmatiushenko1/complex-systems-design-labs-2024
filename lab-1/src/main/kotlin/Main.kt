package org.example

import kotlinx.serialization.Serializable

@Serializable
data class InputData(
    val B: DoubleArray,
    val MC: Array<DoubleArray>,
    val D: DoubleArray,
    val MZ: Array<DoubleArray>,
    val E: DoubleArray,
    val MM: Array<DoubleArray>,
    val MT: Array<DoubleArray>,
    val ME: Array<DoubleArray>
)

fun main() {
    // ВАРІАНТ 12
    // А=В*МС+D*MZ+E*MM;
    // MG=min(D+E)*MM*MT-MZ*ME

    testExecutionTime(
        initialN = 100,
        step = 100,
        iterationsCount = 10,
    ) {
        val expressionA = ExpressionA(
            B = it.B,
            MC = it.MC,
            D = it.D.copyOf(),
            E = it.E.copyOf(),
            MZ = copyMatrix(it.MZ),
            MM = copyMatrix(it.MM),
            printOutput = false,
        )

        val expressionMG = ExpressionMG(
            MT = it.MT,
            ME = it.ME,
            D = it.D.copyOf(),
            E = it.E.copyOf(),
            MZ = copyMatrix(it.MZ),
            MM = copyMatrix(it.MM),
            printOutput = false
        )

        val thread1 = Thread(expressionA)
        val thread2 = Thread(expressionMG)

        thread1.start();
        thread2.start();

        thread1.join()
        thread2.join()
    }
}
