package de.kazzutils.features.dungeons.render

import de.kazzutils.KazzUtils
import de.kazzutils.data.m7.coords.HealerRoute
import de.kazzutils.utils.NewTabUtils
import de.kazzutils.utils.SimpleRender
import de.kazzutils.utils.skyblockfeatures.CatacombsUtils
import net.minecraft.util.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class HighlightHealerRoute {
    @SubscribeEvent
    fun onWorldRender(event: RenderWorldLastEvent) {
        if((CatacombsUtils.floor.contains("F7",true) || CatacombsUtils.inM7) && NewTabUtils.playerClass.contains("Healer",true) && KazzUtils.config.healerRoute && CatacombsUtils.inBossRoom){
            val healerRoute : Map<BlockPos, Color> = HealerRoute.route()
            val healerLines = HealerRoute.walkRoute()
            SimpleRender.highlightBlockListColors(healerRoute,event)
            SimpleRender.renderLinesList(healerLines, Color.cyan,event)
        }
    }

}