package de.kazzutils.event

import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.Cancelable

@Cancelable
data class ItemTossEvent(val item: ItemStack, val dropStack: Boolean) : KazzUtilsEvent()