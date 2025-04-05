package net.azisaba.rcitemdrop.mythicmobs.listener

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent
import net.azisaba.rcitemdrop.mythicmobs.mechanics.ItemDropMechanic
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class MechanicRegisterListener : Listener {
    @EventHandler
    fun onMechanicLoad(e: MythicMechanicLoadEvent) {
        when (e.mechanicName.lowercase()) {
            "rcitemdrop" -> e.register(ItemDropMechanic(e.config))
        }
    }
}
