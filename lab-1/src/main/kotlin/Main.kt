package org.example

import kotlin.random.Random

fun main() {
    val matrix = generateMatrix(5, 5){
        Random.nextDouble()
    }

    val vector = generateVector(5){ Random.nextDouble() }

    val t1 = Thread { printMatrix(matrix) }
    val t2 = Thread { printVector(vector) }

    t2.start()
    t1.start()


    t1.join()
    t2.join()
}
