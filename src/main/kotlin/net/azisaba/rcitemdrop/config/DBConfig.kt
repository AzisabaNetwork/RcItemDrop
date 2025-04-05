package net.azisaba.rcitemdrop.config

import kotlinx.serialization.Serializable

@Serializable
data class DBConfig(
    val host: String = "host",
    val databaseName: String = "rcitemdrop",
    val username: String = "user",
    val password: String = "pass",
)
