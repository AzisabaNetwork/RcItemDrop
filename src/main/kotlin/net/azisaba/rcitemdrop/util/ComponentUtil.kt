package net.azisaba.rcitemdrop.util

import net.azisaba.rcitemdrop.extension.addPrefix
import net.azisaba.rcitemdrop.extension.fail
import net.azisaba.rcitemdrop.extension.success
import net.azisaba.rcitemdrop.extension.toComponent
import net.azisaba.rcitemdrop.extension.warn
import net.kyori.adventure.text.Component

val prefixComponent = Component.text("[RcItemDrop]")

fun successComponent(message: String) = message.toComponent().success()

fun warnComponent(message: String) = message.toComponent().warn()

fun failComponent(message: String) = message.toComponent().fail()

fun prefixed(message: String) = message.toComponent().addPrefix()

fun prefixedSuccess(message: String) = successComponent(message).addPrefix()

fun prefixedWarn(message: String) = warnComponent(message).addPrefix()

fun prefixedFail(message: String) = failComponent(message).addPrefix()
