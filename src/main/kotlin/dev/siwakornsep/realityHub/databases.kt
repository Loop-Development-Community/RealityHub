package dev.siwakornsep.realityHub

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.UUID

class DatabasesManager (private val host: String,
                 private val port: String,
                 private val database: String,
                 private val username: String,
                 private val password: String
) {
        private var connection: Connection? = null

        fun openConnection(): Boolean {
            return try {
                if (connection != null && !connection!!.isClosed) {
                    return true
                }
                Class.forName("com.mysql.cj.jdbc.Driver")
                val url = "jdbc:mysql://$host:$port/$database?useSSL=false"
                connection = DriverManager.getConnection(url, username, password)
                true
            } catch (e: SQLException) {
                e.printStackTrace()
                false
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                false
            }
        }

        fun getConnection(): Connection? {
            return connection
        }

        fun closeConnection() {
            try {
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

    fun createPlayerMainData(uuid: UUID, playerName: String) {
        if (openConnection()) {
            try {
                val ps = connection?.prepareStatement("INSERT INTO main VALUES (1, ?), (4, ?)")
                ps?.setString(1, uuid.toString())
                ps?.setString(2, playerName)
                ps?.executeQuery()
                ps?.close()

            } catch (e: SQLException) {
                e.printStackTrace()
            }
            closeConnection()
        }
    }

    fun findPlayerMainData(uuid: UUID): Boolean {
        var result = false
        if (openConnection()) {
            try {
                val ps = connection?.prepareStatement("SELECT EXISTS(SELECT 1 FROM main WHERE UniqueID = ?);")
                ps?.setString(1, uuid.toString())
                val pseq: ResultSet? = ps?.executeQuery()

                if (pseq?.next() == true) {
                    result = pseq.getInt(1) == 1
                }
                pseq?.close()
                ps?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        return result
    }
}
