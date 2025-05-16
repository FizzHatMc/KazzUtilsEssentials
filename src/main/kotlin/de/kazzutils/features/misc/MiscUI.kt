package de.kazzutils.features.misc

import de.kazzutils.KazzUtils
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object MiscUI {


    @SubscribeEvent
    fun onRenderGameOverlayEvent(event: RenderGameOverlayEvent.Pre){
        val conf = KazzUtils.config
        if(conf.hideArmor && event.type ==  RenderGameOverlayEvent.ElementType.ARMOR) event.isCanceled = true
        if(conf.hideHP && event.type ==  RenderGameOverlayEvent.ElementType.HEALTH) event.isCanceled = true
        if(conf.hideFood && event.type ==  RenderGameOverlayEvent.ElementType.FOOD) event.isCanceled = true


    }



}