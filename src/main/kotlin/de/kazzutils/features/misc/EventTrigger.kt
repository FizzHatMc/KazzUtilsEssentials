package de.kazzutils.features.misc

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.event.OnWorldChangeEvent
import net.minecraft.world.World
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class EventTrigger {
    private var lastWorld: World? = null

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.END) return

        val currentWorld = mc.theWorld

        if (currentWorld != null && currentWorld !== lastWorld) {
            if (lastWorld != null) {
                OnWorldChangeEvent(lastWorld, currentWorld).postAndCatch()
            }
            lastWorld = currentWorld
        }
    }

}