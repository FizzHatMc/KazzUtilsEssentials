package de.kazzutils.data.protect

import de.kazzutils.data.protect.impl.FavoriteStrategy
import de.kazzutils.data.protect.impl.MuseumStrategy
import de.kazzutils.data.protect.impl.StarredItemStrategy
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

abstract class ItemProtectStrategy {

    val name = this::class.simpleName

    abstract fun worthProtecting(item: ItemStack, extraAttr: NBTTagCompound?, type: ProtectType): Boolean
    abstract val isToggled: Boolean

    companion object {
        private val STRATEGIES by lazy {
            arrayOf(FavoriteStrategy, StarredItemStrategy, MuseumStrategy)
        }

        fun isAnyToggled(): Boolean {
            return STRATEGIES.any { it.isToggled }
        }

        fun findValidStrategy(item: ItemStack, extraAttr: NBTTagCompound?, type: ProtectType): ItemProtectStrategy? {
            return STRATEGIES.find { it.isToggled && it.worthProtecting(item, extraAttr, type) }
        }
    }

    enum class ProtectType {
        USERCLOSEWINDOW,
        SELLTONPC,
        SALVAGE,
        CLICKOUTOFWINDOW,
        DROPKEYININVENTORY,
        HOTBARDROPKEY,
        SELLTOAUCTION
    }
}