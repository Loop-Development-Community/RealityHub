package dev.siwakornsep.realityHub

import org.bukkit.plugin.java.JavaPlugin

class RealityHub : JavaPlugin() {

    private lateinit var databaseManager: DatabasesManager

    override fun onEnable() {
        logger.info("RealityHub Start Correctly")
        databaseManager = DatabasesManager("localhost", "3306", "minecraft", "username", "password")
        databaseManager.openConnection()

    }

    override fun onDisable() {
        logger.info("RealityHub Stop Correctly")
        databaseManager.closeConnection()
    }
}
