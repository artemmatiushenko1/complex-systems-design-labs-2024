package org.example

fun main() {
    // ВАРІАНТ 12
    // А=В*МС+D*MZ+E*MM;
    // MG=min(D+E)*MM*MT-MZ*ME;

    testExecutionTime(
        initialN = 100,
        step = 100,
        iterationsCount = 5,
        outputFilePath = "./charts/variant_2.stats.json"
    ) {
        val expressionA = ExpressionA(
            B = it.B,
            MC = it.MC,
            D = it.D,
            E = it.E,
            MZ = it.MZ,
            MM = it.MM,
            n = it.n,
        )

        val expressionMG = ExpressionMG(
            MT = it.MT,
            ME = it.ME,
            D = it.D,
            E = it.E,
            MZ = it.MZ,
            MM = it.MM,
            n = it.n,
        )

        val thread1 = Thread(Thread1(expressionA, expressionMG, it.n, false))
        val thread2 = Thread(Thread2(expressionA, expressionMG, it.n))

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()
    }
}
