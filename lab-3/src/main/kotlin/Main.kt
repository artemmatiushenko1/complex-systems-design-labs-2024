import java.sql.Connection
import java.sql.DriverManager

fun main() {
    val dataManager = DataManager(2000)
    dataManager.getDataSet()

//    val url = "jdbc:postgresql://localhost:5432/complex-systems-design"
//    val user = "artemmatiushenko"
//    val password = "postgres"
//
//    var connection: Connection? = null
//
//    try {
//        Class.forName("org.postgresql.Driver")
//
//        connection = DriverManager.getConnection(url, user, password)
//
//        val statement = connection.createStatement()
//
//        // SQL statement to create a table
//        val sql = """
//            CREATE TABLE IF NOT EXISTS my_table (
//                id SERIAL PRIMARY KEY,
//                name VARCHAR(100) NOT NULL,
//                age INT
//            )
//        """.trimIndent()
//
//        // Execute the SQL statement
//        statement.execute(sql)
//
//        println("Table created successfully.")
//
//        println("Connected to the PostgreSQL server successfully.")
//    } catch (e: Exception) {
//        e.printStackTrace()
//    } finally {
//        connection?.close()
//    }
}
