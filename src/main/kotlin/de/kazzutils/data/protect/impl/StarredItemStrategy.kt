package de.kazzutils.data.protect.impl

import de.kazzutils.KazzUtils
import de.kazzutils.data.protect.ItemProtectStrategy
import de.kazzutils.utils.skyblockfeatures.ItemUtil
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object StarredItemStrategy : ItemProtectStrategy() {
    override fun worthProtecting(item: ItemStack, extraAttr: NBTTagCompound?, type: ProtectType): Boolean {
        if (extraAttr == null) return false
        val isStarred = ItemUtil.getStarCount(extraAttr) > 0
        return when (type) {
            ProtectType.HOTBARDROPKEY -> {
                isStarred
            }
            ProtectType.SELLTOAUCTION -> false
            else -> isStarred
        }
    }

    override val isToggled: Boolean
        get() = KazzUtils.config.protectItems
}