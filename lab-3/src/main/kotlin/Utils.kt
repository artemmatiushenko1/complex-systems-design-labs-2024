import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.system.measureTimeMillis

inline fun <reified T> writeJsonFile(data: T, fileName: String): File {
    val outputFile = File(fileName)

    outputFile.bufferedWriter().use { writer ->
        writer.write(Json.encodeToString(data))
    }

    return outputFile
}

inline fun <reified T> readJsonFileOrNull(fileName: String): T? {
    val file = File(fileName)
    var fileContent: T? = null

    if (file.exists()) {
        val fileContentJson = File(fileName).readText()
        fileContent = Json.decodeFromString<T>(fileContentJson)
    }

    return fileContent
}

fun testExecutionTime(
    iterationsCount: Int,
    step: Int,
    initialCounter: Int,
    outputFilePath: String,
    testingCode: (n: Int) -> Unit
) {
    val statistics = mutableMapOf<Int, Long>()

    for (n in initialCounter..(iterationsCount * initialCounter) step step) {
        val executionTime = measureTimeMillis {
            testingCode(n)
        }

        statistics[n] = executionTime
        println("Completed in ${executionTime}ms.")
        println()
    }

    val reportFile = writeJsonFile(statistics, outputFilePath)
    println("Execution time report saved to: ${reportFile.absolutePath}")
}
