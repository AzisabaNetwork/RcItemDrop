package net.azisaba.rcitemdrop

import co.aikar.commands.PaperCommandManager
import com.charleskorn.kaml.Yaml
import net.azisaba.rcitemdrop.command.ItemDropCommand
import net.azisaba.rcitemdrop.config.RIDConfig
import net.azisaba.rcitemdrop.db.RIDDatabase
import net.azisaba.rcitemdrop.extension.registerEvents
import net.azisaba.rcitemdrop.mythicmobs.listener.MechanicRegisterListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import java.io.File

lateinit var rcLogger: Logger

class RcItemDrop : JavaPlugin() {
    lateinit var config: RIDConfig
    lateinit var commandManager: PaperCommandManager
    var initialized: Boolean = false

    override fun onEnable() {
        rcLogger = slF4JLogger
        dataFolder.mkdirs()

        // if a config file wasn't found
        if (!getConfigFile().exists()) {
            saveDefaultConfig()
            logger.info("Wrote new config file.")
        }

        // get fresh config
        loadConfig()

        // register events
        registerEvents(
            MechanicRegisterListener(),
        )

        // connect database
        rcLogger.info("Initializing database...")
        RIDDatabase.init(config.database, logger)
        rcLogger.info("Database initialized!")

        if (RIDDatabase.isConnectionAlive()) {
            rcLogger.error("Failed to check connection.")

            // disable self
            Bukkit.getPluginManager().disablePlugin(this)
        }

        commandManager =
            PaperCommandManager(this).apply {
                registerCommand(ItemDropCommand())
            }

        initialized = true
    }

    override fun onDisable() {
        // Plugin shutdown logic
        if (initialized) {
            commandManager.unregisterCommands()
            RIDDatabase.close()
        }
        initialized = false
    }

    override fun saveDefaultConfig() {
        saveConfig(RIDConfig())
    }

    override fun saveConfig() {
        saveConfig(config)
    }

    private fun saveConfig(config: RIDConfig) {
        getConfigFile().writeText(
            Yaml.default.encodeToString(RIDConfig.serializer(), config),
        )
    }

    fun updateConfig() {
        reloadConfig()
    }

    private fun loadConfig() {
        config = Yaml.default.decodeFromString(RIDConfig.serializer(), getConfigFile().readText())
    }

    private fun getConfigFile(): File = File(dataFolder, "config.yml")
}
