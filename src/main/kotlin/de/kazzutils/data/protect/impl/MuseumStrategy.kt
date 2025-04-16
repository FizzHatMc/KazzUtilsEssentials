package de.kazzutils.data.protect.impl

import de.kazzutils.KazzUtils
import de.kazzutils.data.protect.ItemProtectStrategy
import de.kazzutils.utils.skyblockfeatures.MuseumUtils
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object MuseumStrategy : ItemProtectStrategy() {
    override fun worthProtecting(item: ItemStack, extraAttr: NBTTagCompound?, type: ProtectType): Boolean {
        val museumMissingNames = MuseumUtils.getMissingItems().keys.toList()
        museumMissingNames.forEach { name ->
            if(item.displayName.contains(name, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    override val isToggled: Boolean //TODO: Change Config
        get() = KazzUtils.config.test
}