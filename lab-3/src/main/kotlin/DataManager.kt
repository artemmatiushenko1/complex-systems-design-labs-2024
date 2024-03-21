
import com.github.javafaker.Faker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class DataManager(
    private val dataSetFileName: String,
    private val dataSetSize: Int
) {
    private val faker = Faker()

    private fun createPopulatedEntity(id: Int): Homework {
        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

        val dateTime = LocalDateTime.parse(
            faker.date().future(10, 1, TimeUnit.DAYS).toString(),
            formatter
        )

        return Homework(
            id = id,
            studentFullName = faker.name().fullName(),
            dueDate = dateTime.toString(),
            title = faker.lorem().sentence(3),
            maxScore = faker.number().numberBetween(10, 20),
            receivedScore = null,
        )
    }

    private fun generateDataSet(): Set<Homework> {
        val dataSet = mutableSetOf<Homework>()

        for(i in 1..dataSetSize) {
            dataSet.add(createPopulatedEntity(i))
        }

        return dataSet.toSet()
    }

    private fun saveDataSetToJsonFile(dataSet: Set<Homework>) {
        writeJsonFile(dataSet, dataSetFileName)
    }

    fun getDataSet(): Set<Homework> {
        val existingDataSet = readJsonFileOrNull<Set<Homework>>(dataSetFileName)

        if(existingDataSet != null) {
            return existingDataSet
        }

        val newDataSet = generateDataSet()
        saveDataSetToJsonFile(newDataSet)

        return newDataSet
    }
}
