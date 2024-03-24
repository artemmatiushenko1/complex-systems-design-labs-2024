import Constants.INSERT_SYNC_BARRIER
import java.sql.Connection

class Thread1(
    private val connection: Connection,
    private val homeworksToInsert: List<Homework>,
) : Runnable {
    private val homeworksRepository = HomeworksRepository(connection)

    override fun run() {
        val homeworkIds = homeworksToInsert.map { it.id }

        homeworksRepository.addHomeworks(homeworksToInsert)

        INSERT_SYNC_BARRIER.await()

        homeworksRepository.getHomeworksById(homeworkIds)

        homeworksRepository.markHomeworksAsDone(homeworkIds)

        homeworksRepository.deleteHomeworks(homeworkIds)

        connection.close()
    }
}
