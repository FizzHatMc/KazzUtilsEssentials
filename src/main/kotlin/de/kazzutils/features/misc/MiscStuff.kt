package de.kazzutils.features.misc

import de.kazzutils.KazzUtils
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class MiscStuff {

    @SubscribeEvent
    fun onEndermanTeleport(event: EnderTeleportEvent){
        if(KazzUtils.config.stopEndermanFakeTeleport) event.isCanceled = true
    }


}