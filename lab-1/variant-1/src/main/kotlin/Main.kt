package org.example

fun main() {
    // ВАРІАНТ 12
    // А=В*МС+D*MZ+E*MM;
    // MG=min(D+E)*MM*MT-MZ*ME

    testExecutionTime(
        initialN = 100,
        step = 100,
        iterationsCount = 5,
        outputFilePath = "./charts/variant_1.stats.json"
    ) {
        val expressionA = ExpressionA(
            B = it.B,
            MC = it.MC,
            D = it.D.copyOf(),
            E = it.E.copyOf(),
            MZ = copyMatrix(it.MZ),
            MM = copyMatrix(it.MM),
        )

        val expressionMG = ExpressionMG(
            MT = it.MT,
            ME = it.ME,
            D = it.D.copyOf(),
            E = it.E.copyOf(),
            MZ = copyMatrix(it.MZ),
            MM = copyMatrix(it.MM),
        )

        val thread1 = Thread(Thread1(expressionA))
        val thread2 = Thread(Thread2(expressionMG))

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()
    }
}
