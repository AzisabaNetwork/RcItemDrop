package net.azisaba.rcitemdrop

import com.charleskorn.kaml.Yaml
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class RcItemDrop : JavaPlugin() {
    lateinit var config: RIDConfig

    override fun onEnable() {
        dataFolder.mkdirs()

        // if a config file wasn't found
        if (!getConfigFile().exists()) {
            saveDefaultConfig()
            logger.info("Wrote new config file.")
        }

        // get fresh config
        loadConfig()
    }

    override fun onDisable() {
        // Plugin shutdown logic
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
