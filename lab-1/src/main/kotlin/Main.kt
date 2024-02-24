package org.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

    val N = 3

    val B = generateVector(N)
    val MC = generateMatrix(N, N)
    val D = generateVector(N)
    val MZ = generateMatrix(N, N)
    val E = generateVector(N)
    val MM = generateMatrix(N, N)

    val MT = generateMatrix(N, N)
    val ME = generateMatrix(N, N)

    val data = InputData(
        B = B,
        MC = MC,
        D = D,
        MZ = MZ,
        E = E,
        MM = MM,
        MT = MT,
        ME = ME
    )

    val string = Json.encodeToString(data)

    val expressionA = ExpressionA(
        B = B,
        MC = MC,
        D = D.copyOf(),
        E = E.copyOf(),
        MZ = copyMatrix(MZ),
        MM = copyMatrix(MM),
    )

    val expressionMG = ExpressionMG(
        MT = MT,
        ME = ME,
        D = D.copyOf(),
        E = E.copyOf(),
        MZ = copyMatrix(MZ),
        MM = copyMatrix(MM),
    )

    val thread1 = Thread(expressionA)
    val thread2 = Thread(expressionMG)

    thread1.start();
    thread2.start();

    thread1.join()
    thread2.join()
}
