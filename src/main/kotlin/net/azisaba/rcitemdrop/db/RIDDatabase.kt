package net.azisaba.rcitemdrop.db

import io.lumine.mythic.bukkit.utils.storage.sql.hikari.HikariConfig
import io.lumine.mythic.bukkit.utils.storage.sql.hikari.HikariDataSource
import net.azisaba.rcitemdrop.config.DBConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.statements.expandArgs
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.InputStream
import java.util.UUID
import java.util.function.Consumer
import java.util.logging.Logger

object RIDDatabase {
    private lateinit var database: Database
    private var initialized = false

    fun init(
        dbConfig: DBConfig,
        logger: Logger,
    ) {
        val config =
            HikariConfig().apply {
                jdbcUrl = "jdbc:mariadb://${dbConfig.host}/${dbConfig.databaseName}"
                driverClassName = "org.mariadb.jdbc.Driver"
                username = dbConfig.username
                password = dbConfig.password
                maximumPoolSize = 6
                isReadOnly = false
                transactionIsolation = "TRANSACTION_SERIALIZABLE"
            }

        val dataSource = HikariDataSource(config)
        database =
            Database.connect(
                dataSource,
                databaseConfig =
                    DatabaseConfig {
                        sqlLogger = RIDSqlLogger(logger)
                    },
            )

        transaction {
            SchemaUtils.create(PlayerItemStorageDataTable)
        }

        initialized = true
    }

    fun close() {
        if (initialized) {
            TransactionManager.closeAndUnregister(database)
        }
        initialized = false
    }

    fun isConnectionAlive(): Boolean =
        try {
            transaction {
                exec("SELECT 1") { rs ->
                    rs.next()
                }
                true
            }
        } catch (_: Exception) {
            false
        }

    object Items {
        /**
         * Add Item to a database
         *
         * @param playerUuid
         * @param itemDataStream must be UTF-8
         * @param itemAmount amount of item
         */
        fun add(
            playerUuid: UUID,
            itemDataStream: InputStream,
            itemAmount: Int,
        ) {
            transaction {
                PlayerItemStorageData.new {
                    uuid = playerUuid
                    itemData = ExposedBlob(itemDataStream)
                    amount = itemAmount
                }
            }
        }

        fun getAll(
            limit: Int,
            offset: Long = 0,
        ): List<PlayerItemStorageData> =
            transaction {
                PlayerItemStorageData
                    .all()
                    .limit(limit)
                    .offset(offset)
                    .toList()
            }

        fun get(id: Int): PlayerItemStorageData =
            transaction {
                PlayerItemStorageData[id]
            }

        fun update(
            id: Int,
            updateFunc: Consumer<PlayerItemStorageData>,
        ) {
            transaction {
                PlayerItemStorageData.findByIdAndUpdate(id) { i -> updateFunc.accept(i) }
            }
        }

        // TODO: this one need to remove in production
        fun removeAll() {
            transaction {
                PlayerItemStorageData.all().toList().forEach { i -> i.delete() }
            }
        }

        fun remove(id: Int) {
            transaction {
                PlayerItemStorageData[id].delete()
            }
        }

        fun count(): Long =
            transaction {
                PlayerItemStorageData.count()
            }
    }
}

class RIDSqlLogger(
    private val logger: Logger,
) : SqlLogger {
    override fun log(
        context: StatementContext,
        transaction: Transaction,
    ) {
        logger.info("SQL: ${context.expandArgs(transaction)}")
    }
}
