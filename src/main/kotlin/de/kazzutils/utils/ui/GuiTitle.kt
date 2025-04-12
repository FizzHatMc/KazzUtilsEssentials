package de.kazzutils.utils.ui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent


class GuiTitle(message: String?, ticks: Int) : GuiScreen(){
    private var message: String? = null
    private var ticks = 0

    init {
        this.message = message
        this.ticks = ticks
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawCenteredString(fontRendererObj, message, width / 2, height / 2, 0xFFFFFF)
        if (ticks <= 0) {
            Minecraft.getMinecraft().displayGuiScreen(null)
        }
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }


    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START || mc.thePlayer == null) return
        ticks--

    }

}