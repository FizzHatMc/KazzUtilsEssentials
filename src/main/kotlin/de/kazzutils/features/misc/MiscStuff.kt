package de.kazzutils.features.misc

import de.kazzutils.KazzUtils
import de.kazzutils.utils.randomutils.ChatUtils
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class MiscStuff {

    @SubscribeEvent
    fun onEndermanTeleport(event: EnderTeleportEvent){
        ChatUtils.messageToChat("Test Teleport")
        if(KazzUtils.config.stopEndermanFakeTeleport) event.isCanceled = true
    }

}