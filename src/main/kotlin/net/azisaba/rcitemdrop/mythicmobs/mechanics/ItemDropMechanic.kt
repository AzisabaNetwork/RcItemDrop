package net.azisaba.rcitemdrop.mythicmobs.mechanics

import io.lumine.mythic.api.config.MythicLineConfig
import io.lumine.mythic.api.skills.INoTargetSkill
import io.lumine.mythic.api.skills.ISkillMechanic
import io.lumine.mythic.api.skills.SkillMetadata
import io.lumine.mythic.api.skills.SkillResult
import net.azisaba.rcitemdrop.extension.getInt
import net.azisaba.rcitemdrop.extension.getStr
import net.azisaba.rcitemdrop.mythicmobs.MythicApi
import net.azisaba.rcitemdrop.util.addItemLog
import org.bukkit.entity.Player

class ItemDropMechanic(
    config: MythicLineConfig,
) : ISkillMechanic,
    INoTargetSkill {
    val item: String =
        config.getStr(
            "items",
            "item",
            "i",
            "type",
            "t",
            "material",
            "mat",
            "m",
        ) ?: error("Failed to get item type from ${config.line} in ${config.fileName}")

    val amount: Int = config.getInt("amount", "a")

    val stack = MythicApi.itemManager.getItemStack(item, amount) ?: error("Failed to get itemStack -> ${config.line} in ${config.fileName}")

    override fun cast(meta: SkillMetadata): SkillResult {
        // == Get caster
        val player = meta.trigger.bukkitEntity
        // We will need to think again for this error type
        if (player !is Player) return SkillResult.INVALID_TARGET

        // == Give Item
        val remainItem = player.inventory.addItem(stack)
        // TODO: add to stash
        addItemLog(
            LOG_EVENT_TYPE,
            "#server",
            player.name,
            "Add ${stack.type} x ${stack.amount} to ${player.name}",
            player.uniqueId,
        )
        return SkillResult.SUCCESS
    }

    companion object {
        const val LOG_EVENT_TYPE = "rcitemdrop_give"
    }
}
