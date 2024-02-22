package org.example

import kotlin.random.Random

fun generateVector(size: Int): DoubleArray {
    val result = DoubleArray(size)

    for (i in 0 until size) {
        result[i] = Random.nextDouble()
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

fun printMatrix (matrix: Array<DoubleArray>) {
    synchronized(IO_LOCK) {
        for (i in matrix.indices) {
            println(matrix[i].joinToString(" "))
        }
    }
}

