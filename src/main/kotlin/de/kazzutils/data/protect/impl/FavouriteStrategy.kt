package de.kazzutils.data.protect.impl

import de.kazzutils.KazzUtils
import de.kazzutils.core.PersistentSave
import de.kazzutils.data.protect.ItemProtectStrategy
import de.kazzutils.utils.skyblockfeatures.ItemUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import java.io.File
import java.io.Reader
import java.io.Writer

object FavoriteStrategy : ItemProtectStrategy() {
    val favoriteUUIDs = hashSetOf<String>()
    val favoriteItemIds = hashSetOf<String>()
    val save = FavoriteStrategySave

    override fun worthProtecting(item: ItemStack, extraAttr: NBTTagCompound?, type: ProtectType): Boolean {
        if (type == ProtectType.HOTBARDROPKEY) return false
        return favoriteUUIDs.contains(extraAttr?.getString("uuid")) || favoriteItemIds.contains(
            ItemUtil.getSkyBlockItemID(
                extraAttr
            )
        )
    }

    override val isToggled: Boolean = true

    object FavoriteStrategySave : PersistentSave(File(KazzUtils.modDir, "favoriteitems.json")) {
        override fun read(reader: Reader) {
            val data = json.decodeFromString<JsonElement>(reader.readText())
            if (data is JsonObject) {
                json.decodeFromJsonElement<Schema>(data).also {
                    favoriteUUIDs.addAll(it.favoriteUUIDs)
                    favoriteItemIds.addAll(it.favoriteItemIds)
                }
            } else if (data is JsonArray) {
                favoriteUUIDs.addAll(json.decodeFromJsonElement<Set<String>>(data))
            }
        }

        override fun write(writer: Writer) {
            writer.write(json.encodeToString(Schema(favoriteUUIDs, favoriteItemIds)))
        }

        override fun setDefault(writer: Writer) {
            writer.write(json.encodeToString(Schema()))
        }

        @Serializable
        data class Schema(val favoriteUUIDs: Set<String> = emptySet(), val favoriteItemIds: Set<String> = emptySet())
    }
}