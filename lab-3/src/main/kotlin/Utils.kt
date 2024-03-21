import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

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
