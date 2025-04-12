package de.kazzutils.utils.randomutils

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class TitleUtils {
    private var message = ""
    private var displayTicks = 0

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent.Text?) {
        if (displayTicks > 0 && !message.isEmpty()) {
            val sr = ScaledResolution(Minecraft.getMinecraft())
            val width = sr.scaledWidth
            val height = sr.scaledHeight

            val fontRenderer = Minecraft.getMinecraft().fontRendererObj
            val x = width / 2 - fontRenderer.getStringWidth(message) / 2
            val y = height / 2

            fontRenderer.drawStringWithShadow(message, x.toFloat(), y.toFloat(), 0xFFFFFF)
            displayTicks--
        }
    }

    fun displayMessage(msg: String?, ticks: Int) {
        this.message = msg!!
        this.displayTicks = ticks
    }

}