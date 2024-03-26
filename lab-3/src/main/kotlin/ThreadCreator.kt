import Constants.INSERT_SYNC_BARRIER
import java.sql.Connection

class ThreadCreator(
    private val connection: Connection,
    private val homeworksToInsert: List<Homework>,
    isSTM: Boolean,
) : Runnable {
    private val homeworksRepository = HomeworksRepository(connection, isSTM)

    override fun run() {
        val homeworkIds = homeworksToInsert.map { it.id }

        homeworksRepository.addHomeworks(homeworksToInsert)

        INSERT_SYNC_BARRIER.await()

        homeworksRepository.getHomeworksById(homeworkIds)

        homeworksRepository.toggleAllHomeworksIsDone()

        homeworksRepository.deleteHomeworks(homeworkIds)

        connection.close()
    }
}
