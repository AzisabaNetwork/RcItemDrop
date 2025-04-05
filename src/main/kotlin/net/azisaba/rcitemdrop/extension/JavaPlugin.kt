package net.azisaba.rcitemdrop.extension

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.collections.forEach

fun JavaPlugin.registerEvents(vararg listeners: Listener) = listeners.forEach { server.pluginManager.registerEvents(it, this) }
