package net.azisaba.rcitemdrop.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PlayerItemStorageDataTable : IntIdTable() {
    val uuid = uuid("uuid")
    val itemData = blob("raw_data")
    val amount = integer("amount")
}

class PlayerItemStorageData(
    id: EntityID<Int>,
) : IntEntity(id) {
    companion object : IntEntityClass<PlayerItemStorageData>(PlayerItemStorageDataTable)

    var uuid by PlayerItemStorageDataTable.uuid
    var itemData by PlayerItemStorageDataTable.itemData
    var amount by PlayerItemStorageDataTable.amount
}
