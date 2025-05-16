package de.kazzutils.features.dungeons.render

import de.kazzutils.data.m7.coords.HealerRoute
import de.kazzutils.utils.SimpleRender
import de.kazzutils.utils.skyblockfeatures.CatacombsUtils
import net.minecraft.util.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class HighlightHealerRoute {

    @SubscribeEvent
    fun onWorldRender(event: RenderWorldLastEvent) {
        //CatacombsUtils.floor.contains("F7",true)
        if(true){
            val healerRoute : Map<BlockPos, Color> = HealerRoute.route()
            SimpleRender.highlightBlockListColors(healerRoute,event)
        }
    }

}