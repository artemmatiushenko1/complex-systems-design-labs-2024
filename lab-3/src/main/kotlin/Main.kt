fun main() {
    val connectionsPull = ConnectionsPull()

    val dataSetSize = 1000
    val homeworksDataSet = DataSetGenerator(dataSetSize).getDataSet()

    val globalHomeworksRepository = HomeworksRepository(connectionsPull.getConnection())

    testExecutionTime(
        iterationsCount = 50,
        outputFilePath = "data/execution-time.report.json",
        step = 1,
        initialN = 1
    ) {
        globalHomeworksRepository.createTable()

        val thread1 = Thread(
            ThreadCreator(
                connectionsPull.getConnection(),
                homeworksDataSet.slice(0..<(dataSetSize / 2))
            )
        )

        val thread2 = Thread(
            ThreadCreator(
                connectionsPull.getConnection(),
                homeworksDataSet.slice(dataSetSize / 2..<dataSetSize)
            )
        )

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()

        globalHomeworksRepository.dropTable()
    }
}
