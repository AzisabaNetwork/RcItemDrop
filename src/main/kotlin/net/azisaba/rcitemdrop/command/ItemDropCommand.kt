package net.azisaba.rcitemdrop.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import io.lumine.mythic.bukkit.MythicBukkit
import net.azisaba.rcitemdrop.extension.toPlainTextStr
import net.azisaba.rcitemdrop.util.failComponent
import net.azisaba.rcitemlogging.RcItemLogging
import org.bukkit.command.CommandSender

@CommandAlias("itemdrop")
class ItemDropCommand : BaseCommand() {
    @Default
    @CommandPermission("rcitemdrop.cmd.itemdrop")
    @CommandCompletion("@players mmItemId (requireItemId)")
    fun default(
        sender: CommandSender,
        playerId: String,
        mmItemId: String,
        @Default("#none") requireItemId: String,
    ) {
        val player = sender.server.getPlayer(playerId) ?: error("No player matched to $playerId")
        val mm = MythicBukkit.inst()
        if (requireItemId != "#none") {
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
            "${stack.displayName().toPlainTextStr()} +1 from system",
            player.uniqueId,
        )
    }
}
