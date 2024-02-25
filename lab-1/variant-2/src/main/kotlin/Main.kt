package org.example

fun main() {
    // ВАРІАНТ 12
    // А=В*МС+D*MZ+E*MM;
    // MG=min(D+E)*MM*MT-MZ*ME;

    val inputData = generateInputData(3)

    val expressionA = ExpressionA(inputData)
    val expressionMG = ExpressionMG(inputData)

    val thread1 = Thread(Thread1(expressionA, expressionMG))
    val thread2 = Thread(Thread2(expressionA, expressionMG))

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()
}
