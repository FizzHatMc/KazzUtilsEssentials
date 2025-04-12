package de.kazzutils.handler.transformers


import de.kazzutils.event.MainreceivePacketEvent
import net.minecraft.network.INetHandler
import net.minecraft.network.Packet
import org.objectweb.asm.Opcodes

object PacketThreadUtilTransformer {
    @JvmStatic
    fun postEvent(netHandler: INetHandler, packet: Packet<INetHandler>): Boolean {
        return MainreceivePacketEvent(
            netHandler,
            packet
        ).postAndCatch()
    }
}