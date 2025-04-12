package de.kazzutils.event

import net.minecraft.network.INetHandler
import net.minecraft.network.Packet
import net.minecraftforge.fml.common.eventhandler.Cancelable

@Cancelable
data class MainreceivePacketEvent<T : Packet<U>, U : INetHandler>(val handler : U, val packet : T) : KazzUtilsEvent()