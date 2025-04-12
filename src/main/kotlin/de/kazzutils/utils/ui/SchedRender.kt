package de.kazzutils.utils.ui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.ScaledResolution
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class SchedRender {
    private var message = ""
    private var displayTicks = 0

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent.Text?) {
        if (displayTicks > 0 && message.isNotEmpty()) {
            val sr = ScaledResolution(Minecraft.getMinecraft())
            val width = sr.scaledWidth
            val height = sr.scaledHeight

            val fontRenderer: FontRenderer = Minecraft.getMinecraft().fontRendererObj
            val x: Int = width / 2 - fontRenderer.getStringWidth(message) / 2
            val y = height / 2

            fontRenderer.drawStringWithShadow(message, x.toFloat(), y.toFloat(), 0xFFFFFF)
        }
    }

    fun displayMessage(msg: String, ticks: Int) {
        this.message = msg
        this.displayTicks = ticks
        Thread {
            try {
                Thread.sleep((ticks * 50).toLong()) // 50ms per tick
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            this.message = ""
            this.displayTicks = 0
        }.start()
    }
}