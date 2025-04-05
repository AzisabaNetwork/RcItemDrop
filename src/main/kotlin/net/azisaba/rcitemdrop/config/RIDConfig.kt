package net.azisaba.rcitemdrop.config

import kotlinx.serialization.Serializable

@Serializable
data class RIDConfig(
    val version: Int = 2,
    val database: DBConfig = DBConfig(),
)
