package org.example

fun main() {
    // ВАРІАНТ 12
    // А=В*МС+D*MZ+E*MM;
    // MG=min(D+E)*MM*MT-MZ*ME

    testExecutionTime(
        initialN = 100,
        step = 100,
        iterationsCount = 10,
        outputFilePath = "data/variant-1.report.json"
    ) { n ->
        println("Computing expressions for N = $n")
        val inputData: InputData = getInputData(n)

        val expressionA = ExpressionA(
            B = inputData.B,
            MC = inputData.MC,
            D = inputData.D.copyOf(),
            E = inputData.E.copyOf(),
            MZ = copyMatrix(inputData.MZ),
            MM = copyMatrix(inputData.MM),
            n = n,
        )

        val expressionMG = ExpressionMG(
            MT = inputData.MT,
            ME = inputData.ME,
            D = inputData.D.copyOf(),
            E = inputData.E.copyOf(),
            MZ = copyMatrix(inputData.MZ),
            MM = copyMatrix(inputData.MM),
            n = n,
        )

        val thread1 = Thread(Thread1(expressionA, true))
        val thread2 = Thread(Thread2(expressionMG, true))

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        writeJsonFile(
            CalculationResult(expressionA.result, expressionMG.result),
            "data/variant-1-output-$n.json",
        )
    }
}
