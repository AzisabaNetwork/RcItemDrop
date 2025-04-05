package net.azisaba.rcitemdrop.extension

import com.tksimeji.visualkit.element.VisualkitElement
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun Material.getStack(amount: Int = 1) = ItemStack.of(this, amount)

fun Material.toElement() = VisualkitElement.create(this)
