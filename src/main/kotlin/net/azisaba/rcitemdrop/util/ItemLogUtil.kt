package net.azisaba.rcitemdrop.util

import net.azisaba.rcitemlogging.RcItemLogging
import java.util.UUID

fun addItemLog(
    eventType: String,
    nameFrom: String,
    nameTo: String,
    message: String,
    vararg targets: UUID,
) = RcItemLogging.getApi().put(
    eventType,
    nameFrom,
    nameTo,
    message,
    *targets,
)
