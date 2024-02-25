package org.example

fun main() {
    // ВАРІАНТ 12
    // А=В*МС+D*MZ+E*MM;
    // MG=min(D+E)*MM*MT-MZ*ME;

    testExecutionTime(
        initialN = 100,
        step = 100,
        iterationsCount = 10,
        outputFilePath = "data/variant-2.report.json"
    ) { n ->
        println("Computing expressions for N = $n")
        val inputData: InputData = getInputData(n)

        val expressionA = ExpressionA(
            B = inputData.B,
            MC = inputData.MC,
            D = inputData.D,
            E = inputData.E,
            MZ = inputData.MZ,
            MM = inputData.MM,
            n = n,
        )

        val expressionMG = ExpressionMG(
            MT = inputData.MT,
            ME = inputData.ME,
            D = inputData.D,
            E = inputData.E,
            MZ = inputData.MZ,
            MM = inputData.MM,
            n = n,
        )

        val thread1 = Thread(Thread1(expressionA, expressionMG, n, true))
        val thread2 = Thread(Thread2(expressionA, expressionMG, n, true))

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        writeJsonFile(
            CalculationResult(expressionA.result, expressionMG.result),
            "data/variant-2-output-$n.json",
        )
    }
}
