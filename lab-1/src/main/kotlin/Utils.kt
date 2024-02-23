package org.example

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
