package de.kazzutils.features.misc

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.randomutils.ChatUtils
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import org.lwjgl.input.Keyboard

class MiscStuff {

    @SubscribeEvent
    fun onEndermanTeleport(event: EnderTeleportEvent){
        ChatUtils.messageToChat("Test Teleport")
        if(KazzUtils.config.stopEndermanFakeTeleport) event.isCanceled = true
    }


}