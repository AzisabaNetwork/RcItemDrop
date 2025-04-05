package net.azisaba.rcitemdrop.extension

import net.azisaba.rcitemdrop.util.prefixComponent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun Component.success() = color(NamedTextColor.GREEN)

fun Component.warn() = color(NamedTextColor.YELLOW)

fun Component.fail() = color(NamedTextColor.RED)

fun Component.addPrefix() = Component.join(JoinConfiguration.spaces(), prefixComponent, this)

/**
 * Convert component to non-decorated string
 *
 * @return plain text
 */
fun Component.toPlainTextStr(): String = PlainTextComponentSerializer.plainText().serialize(this)
