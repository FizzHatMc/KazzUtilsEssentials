package de.kazzutils.event

import net.minecraft.util.IChatComponent
import net.minecraftforge.fml.common.eventhandler.Cancelable

@Cancelable
data class AddChatMessageEvent(val message: IChatComponent) : KazzUtilsEvent()