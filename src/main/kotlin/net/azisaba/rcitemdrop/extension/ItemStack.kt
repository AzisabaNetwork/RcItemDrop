package net.azisaba.rcitemdrop.extension

import com.tksimeji.visualkit.element.VisualkitElement
import org.bukkit.inventory.ItemStack

fun ItemStack.toElement() = VisualkitElement.item(this)
