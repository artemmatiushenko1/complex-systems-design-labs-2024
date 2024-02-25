package org.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.math.pow
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun generateDouble(): Double {
    val decimalPlaces = Random.nextInt(0, 14)

    val randomNumber = Random.nextDouble(0.0, 100.0)

    return Math.round(randomNumber * 10.0.pow(decimalPlaces.toDouble())) / 10.0.pow(decimalPlaces.toDouble());
}

fun generateVector(size: Int): DoubleArray {
    val result = DoubleArray(size)

    for (i in 0 until size) {
        result[i] = generateDouble()
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

fun printVector(vector: DoubleArray, message: String?) {
    synchronized(SYSTEM_OUT_LOCK) {
        println(message)
        println(vector.joinToString(" "))
    }
}

fun printMatrix(matrix: Array<DoubleArray>, message: String?) {
    synchronized(SYSTEM_OUT_LOCK) {
        println(message)
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

fun copyMatrix(matrix: Array<DoubleArray>): Array<DoubleArray> = matrix.map { it.copyOf() }.toTypedArray()

fun generateInputData(n: Int): InputData {
    val data = InputData(
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

inline fun <reified T> writeJsonFile(data: T, filepath: String): File {
    val outputFile = File(filepath)

    outputFile.bufferedWriter().use { writer ->
        writer.write(Json.encodeToString(data))
    }

    return outputFile
}

fun getInputData(n: Int): InputData {
    val inputData: InputData

    val inputDataFile = File("data/input-$n.json")

    if (inputDataFile.exists()) {
        println("Using existing input file -> ${inputDataFile.absolutePath}")
        val inputDataJson = File("data/input-$n.json").readText()
        inputData = Json.decodeFromString<InputData>(inputDataJson)
    } else {
        println("No input data found for N = $n. Generating...")
        inputData = generateInputData(n)
        writeJsonFile(inputData, "data/input-$n.json")
    }

    return inputData
}

fun testExecutionTime(
    iterationsCount: Int,
    step: Int,
    initialN: Int,
    outputFilePath: String,
    testingCode: (n: Int) -> Unit
) {
    val statistics = mutableMapOf<Int, Long>()

    for (n in initialN..(iterationsCount * initialN) step step) {
        val executionTime = measureTimeMillis {
            testingCode(n)
        }

        statistics[n] = executionTime
        println("Completed in ${executionTime}ms.")
        println()
    }

    val reportFile = writeJsonFile(statistics, outputFilePath)
    println("Execution time report saved to: ${reportFile.absolutePath}")
}
