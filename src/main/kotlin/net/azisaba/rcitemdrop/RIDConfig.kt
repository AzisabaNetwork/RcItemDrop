package net.azisaba.rcitemdrop

import kotlinx.serialization.Serializable

@Serializable
data class RIDConfig(
    val version: Int = 1,
)
