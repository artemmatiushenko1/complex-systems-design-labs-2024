package org.example

fun generateVector(size: Int, fillCallback: () -> Double): DoubleArray {
    val result = DoubleArray(size)

    for (i in 0 until size) {
        result[i] = fillCallback()
    }

    return result
}

fun generateMatrix(cols: Int, rows: Int, fillCallback: () -> Double): Array<DoubleArray> {
    val result = Array(rows) {
        DoubleArray(
            cols
        )
    }

    for (i in 0 until rows) {
        result[i] = generateVector(cols, fillCallback)
    }

    return result
}

var IO_LOCK = Any()

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

