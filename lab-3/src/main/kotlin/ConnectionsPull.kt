import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

class ConnectionsPull {
    private var dataSource: HikariDataSource

    init {
        val config = HikariConfig()

        config.jdbcUrl = "jdbc:postgresql://localhost:5432/complex-systems-design"
        config.username = "artemmatiushenko"
        config.password = "postgres"
        config.driverClassName = "org.postgresql.Driver"
        config.addDataSourceProperty("minimumIdle", "5")
        config.addDataSourceProperty("maximumPoolSize", "5")

        dataSource = HikariDataSource(config)
    }

    fun getConnection(): Connection = dataSource.connection
}
