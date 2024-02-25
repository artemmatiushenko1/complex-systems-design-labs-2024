package org.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun generateVector(size: Int): DoubleArray {
    val result = DoubleArray(size)

    for (i in 0 until size) {
        result[i] = Random.nextDouble()
//        result[i] = 1.0
    }

    return result
}

fun generateMatrix(cols: Int, rows: Int): Array<DoubleArray> {
    val result = Array(rows) {
        DoubleArray(
            cols
        )
    }

    for (i in 0 until rows) {
        result[i] = generateVector(cols)
    }

    return result
}

fun printVector(vector: DoubleArray) {
    synchronized(SYSTEM_OUT_LOCK) {
        println(vector.joinToString(" "))
    }
}

fun printMatrix(matrix: Array<DoubleArray>) {
    synchronized(SYSTEM_OUT_LOCK) {
        for (i in matrix.indices) {
            println(matrix[i].joinToString(" "))
        }
    }
}

fun kahanSum(vararg numbers: Double): Double {
    var sum = 0.0
    var compensation = 0.0

    for (number in numbers) {
        val y = number - compensation
        val tempSum = sum + y
        compensation = (tempSum - sum) - y
        sum = tempSum
    }

    return sum
}

fun multiplyMatrices(matrixA: Array<DoubleArray>, matrixB: Array<DoubleArray>): Array<DoubleArray> {
    val rows = matrixA.size
    val columns = matrixB[0].size
    val n = matrixA[0].size

    val result = Array(rows) { DoubleArray(columns) }

    for (i in 0 until rows) {
        for (j in 0 until columns) {
            val products = DoubleArray(n) { k -> matrixA[i][k] * matrixB[k][j] }
            result[i][j] = kahanSum(*products)
        }
    }

    return result
}

fun multiplyVectorByMatrix(vector: DoubleArray, matrix: Array<DoubleArray>): DoubleArray {
    val result = DoubleArray(vector.size)

    for (i in matrix.indices) {
        val products = vector.mapIndexed { j, el -> el * matrix[j][i] }
        result[i] = kahanSum(*products.toDoubleArray())
    }

    return result
}

fun subtractMatrices(matrixA: Array<DoubleArray>, matrixB: Array<DoubleArray>): Array<DoubleArray> {
    val difference = Array(matrixA.size) { DoubleArray(matrixA.size) }

    for (i in matrixA.indices) {
        for (j in matrixB.indices) {
            difference[i][j] = matrixA[i][j] - matrixB[i][j]
        }
    }

    return difference
}

fun copyMatrix(matrix: Array<DoubleArray>): Array<DoubleArray> = matrix.map { it.copyOf() }.toTypedArray()

fun generateInputData(n: Int): InputData {
    val data = InputData(
        n = n,

        B = generateVector(n),
        MC = generateMatrix(n, n),
        D = generateVector(n),
        MZ = generateMatrix(n, n),
        E = generateVector(n),
        MM = generateMatrix(n, n),
        MT = generateMatrix(n, n),
        ME = generateMatrix(n, n)
    )

    return data
}

fun testExecutionTime(
    iterationsCount: Int,
    step: Int,
    initialN: Int,
    outputFilePath: String,
    testingCode: (inputData: InputData) -> Unit
) {
    val statistics = mutableMapOf<Int, Long>()

    for (n in initialN..(iterationsCount * initialN) step step) {
        val inputData = generateInputData(n)

        val executionTime = measureTimeMillis {
            testingCode(inputData)
        }

        statistics[n] = executionTime
        println("N = $n, Execution time = ${executionTime}ms")
    }

    val outputFile = File(outputFilePath)

    outputFile.bufferedWriter().use { writer ->
        writer.write(Json.encodeToString(statistics))
    }
}
