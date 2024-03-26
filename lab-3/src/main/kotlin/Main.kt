const val IS_STM = false
const val DATASET_SIZE = 5000

fun main() {
    val connectionsPull = ConnectionsPull()

    val homeworksDataSet = DataSetGenerator(DATASET_SIZE).getDataSet()

    val globalHomeworksRepository = HomeworksRepository(connectionsPull.getConnection(), IS_STM)

    testExecutionTime(
        iterationsCount = 50,
        outputFilePath = "data/execution-time.report.json",
        step = 1,
        initialCounter = 1
    ) {
        globalHomeworksRepository.createTable()

        val thread1 = Thread(
            ThreadCreator(
                connectionsPull.getConnection(),
                homeworksDataSet.slice(0..<(DATASET_SIZE / 2)),
                IS_STM,
            )
        )

        val thread2 = Thread(
            ThreadCreator(
                connectionsPull.getConnection(),
                homeworksDataSet.slice(DATASET_SIZE / 2..<DATASET_SIZE),
                IS_STM,
            )
        )

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        globalHomeworksRepository.dropTable()
    }
}
