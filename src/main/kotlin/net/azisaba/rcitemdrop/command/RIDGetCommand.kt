package net.azisaba.rcitemdrop.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import io.lumine.mythic.bukkit.MythicBukkit
import net.azisaba.rcitemdrop.util.failComponent
import net.azisaba.rcitemlogging.RcItemLogging
import org.bukkit.entity.Player

@CommandAlias("ridget")
class RIDGetCommand : BaseCommand() {
    @Default
    fun default(
        player: Player,
        mmItemId: String,
        @Default("none") requireItemId: String?,
    ) {
        val mm = MythicBukkit.inst()
        if (requireItemId != null) {
            if (!player.inventory.contains(mm.itemManager.getItemStack(requireItemId))) {
                player.sendMessage(failComponent("You haven't require item. (itemId:$requireItemId)"))
                return
            }
        }
        val stack = mm.itemManager.getItemStack(mmItemId) ?: error("Failed to get item stack. (itemId:$mmItemId)")
        player.inventory.addItem(stack)
        RcItemLogging.getApi().put(
            "rcitemdrop_give",
            "#system",
            player.name,
            "${stack.displayName()} +1 from system",
            player.uniqueId,
        )
    }
}
