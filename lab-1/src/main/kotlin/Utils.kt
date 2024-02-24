package org.example

import java.security.InvalidParameterException

fun generateVector(size: Int): DoubleArray {
    val result = DoubleArray(size)

    for (i in 0 until size) {
//        result[i] = Random.nextDouble()
        result[i] = 1.0
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

val IO_LOCK = Any()

fun printVector(vector: DoubleArray) {
    synchronized(IO_LOCK) {
        println(vector.joinToString(" "))
    }
}

fun printMatrix(matrix: Array<DoubleArray>) {
    synchronized(IO_LOCK) {
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
