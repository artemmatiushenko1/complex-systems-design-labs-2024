import Constants.GET_HOMEWORKS_BY_ID_LOCK
import Constants.TRANSACTION_LOCK
import Constants.UPDATE_LOCK
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import kotlin.concurrent.withLock

class HomeworksRepository(private val connection: Connection) {
    companion object {
        const val TABLE_NAME = "homeworks"
    }

    private fun transaction(transactionCode: () -> Unit) {
        TRANSACTION_LOCK.withLock {
            try {
                connection.autoCommit = false

                transactionCode()

                connection.commit()
            } catch (e: Exception) {
                connection.rollback()

                println("❌ Transaction failed!")
                println(e)
            } finally {
                connection.autoCommit = true
            }
        }
    }

    private fun runUpdate(getSql: () -> String) {
        println(getSql())

        try {
            UPDATE_LOCK.withLock {
                val statement = connection.createStatement()
                statement.executeUpdate(getSql().trimIndent())
            }
        } catch (e: Exception) {
            println("❌ Failed to execute update!")
            println(e)
        }
    }

    private fun runQuery(getSql: () -> String): ResultSet? {
        println(getSql())

        try {
            val statement = connection.createStatement()
            return statement.executeQuery(getSql().trimIndent())
        } catch (e: Exception) {
            println("❌ Failed to execute update!")
            println(e)
        }

        return null
    }

    fun createTable() {
        runUpdate {
            """ 
              CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                id INT PRIMARY KEY,
                studentFullName VARCHAR(255) NOT NULL,
                title VARCHAR(255) NOT NULL,
                dueDate VARCHAR(50) NOT NULL,
                maxScore INT NOT NULL,
                receivedScore INT,
                isDone BOOLEAN DEFAULT FALSE
              )
            """
        }
    }

    fun dropTable() {
        runUpdate { "DROP TABLE IF EXISTS $TABLE_NAME" }
    }

    fun addHomeworks(homeworks: List<Homework>) {
        homeworks.forEach {
            runUpdate {
                """INSERT INTO $TABLE_NAME (id, studentFullName, title, dueDate, maxScore, receivedScore, isDone) VALUES (${it.id}, '${
                    it.studentFullName.replace(
                        "'",
                        "''"
                    )
                }', '${it.title}', '${it.dueDate}', ${it.maxScore}, NULL, false)"""
            }
        }
    }

    fun markHomeworksAsDone(homeworkIds: List<Int>) {
        runUpdate {
            "UPDATE $TABLE_NAME SET isDone = true WHERE id IN (${homeworkIds.joinToString(", ")})"
        }
    }

    fun updateReceivedScore(homeworkIds: List<Int>) {
        transaction {
            for (id in homeworkIds) {
                val maxScoreResultSet = runQuery { "SELECT maxScore FROM $TABLE_NAME WHERE id = $id" }
                var maxScore: Int? = null

                if (maxScoreResultSet != null) {
                    if (maxScoreResultSet.next()) {
                        maxScore = maxScoreResultSet.getInt("maxScore")
                    } else {
                        println("No maxScore found for homework id $id")
                    }
                }

                if (maxScore != null) {
                    runUpdate {
                        "UPDATE $TABLE_NAME SET receivedScore = $maxScore WHERE id = $id"
                    }
                }
            }
        }
    }

    fun deleteHomeworks(homeworkIds: List<Int>) {
        runUpdate {
            "DELETE FROM $TABLE_NAME WHERE id IN (${homeworkIds.joinToString(", ")})"
        }
    }

    fun getHomeworksById(homeworkIds: List<Int>) {
        val resultSet = runQuery { "SELECT * FROM $TABLE_NAME WHERE id IN (${homeworkIds.joinToString(", ")})" }

        while (resultSet != null && resultSet.next()) {
            val id = resultSet.getInt("id")
            val studentFullName = resultSet.getString("studentFullName")
            val title = resultSet.getString("title")
            val dueDate = resultSet.getString("dueDate")
            val maxScore = resultSet.getInt("maxScore")
            val receivedScore = resultSet.getInt("receivedScore")
            val isDone = resultSet.getBoolean("isDone")

            GET_HOMEWORKS_BY_ID_LOCK.withLock {
                println("ID: $id, Student: $studentFullName, Homework: $title, Due Date: $dueDate, Max Score: $maxScore, Received Score: $receivedScore, Is Done: $isDone")
            }
        }
    }
}
