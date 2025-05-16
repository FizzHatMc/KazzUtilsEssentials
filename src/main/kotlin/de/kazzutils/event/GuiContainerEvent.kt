package de.kazzutils.event


import de.kazzutils.KazzUtils.Companion.mc
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container
import net.minecraft.inventory.ContainerChest
import net.minecraft.inventory.Slot
import net.minecraftforge.fml.common.eventhandler.Cancelable

abstract class GuiContainerEvent(open val gui: GuiContainer, open val container: Container) : KazzUtilsEvent() {

    val chestName: String by lazy {
        if (container !is ContainerChest) error("Container is not a chest")
        return@lazy (container as ContainerChest).lowerChestInventory.displayName.unformattedText.trim()
    }

    data class BackgroundDrawnEvent(
        override val gui: GuiContainer,
        override val container: Container,
        val mouseX: Int,
        val mouseY: Int,
        val partialTicks: Float,
    ) : GuiContainerEvent(gui, container)



    /*
    @Cancelable
    data class PreDraw(
        override val gui: GuiContainer,
        override val container: Container,
        val mouseX: Int,
        val mouseY: Int,
        val partialTicks: Float,
    ) : GuiContainerEvent(gui, container) {
        fun drawDefaultBackground() =
            GuiRenderUtils.drawGradientRect(0, 0, gui.width, gui.height, -1072689136, -804253680, 0.0)
    }
     */

    data class PostDraw(
        override val gui: GuiContainer,
        override val container: Container,
        val mouseX: Int,
        val mouseY: Int,
        val partialTicks: Float,
    ) : GuiContainerEvent(gui, container)

    @Cancelable
    data class CloseWindowEvent(override val gui: GuiContainer, override val container: Container) :
        GuiContainerEvent(gui, container)

    abstract class DrawSlotEvent(gui: GuiContainer, container: Container, open val slot: Slot) :
        GuiContainerEvent(gui, container) {

        @Cancelable
        data class Pre(
            override val gui: GuiContainer,
            override val container: Container,
            override val slot: Slot,
        ) :
            DrawSlotEvent(gui, container, slot)

        data class Post(
            override val gui: GuiContainer,
            override val container: Container,
            override val slot: Slot,
        ) :
            DrawSlotEvent(gui, container, slot)
    }

    data class ForegroundDrawnEvent(
        override val gui: GuiContainer,
        override val container: Container,
        val mouseX: Int,
        val mouseY: Int,
        val partialTicks: Float,
    ) : GuiContainerEvent(gui, container)


    @Cancelable
    data class SlotClickEvent(
        override val gui: GuiContainer,
        override val container: Container,
        val slot: Slot?,
        val slotId: Int,
        val clickedButton: Int,
        val clickType: Int
    ) : GuiContainerEvent(gui, container)

    fun clickSlot(slot: Int, windowId: Int? = getWindowId(), mouseButton: Int = 0, mode: Int = 0) {
        windowId ?: return
        val controller = mc.playerController
        //#if MC < 1.12
        controller.windowClick(windowId, slot, mouseButton, mode, mc.thePlayer)
        //#else
        //$$ controller.windowClick(windowId, slot, mouseButton, ClickType.entries[mode], Minecraft.getMinecraft().player)
        //#endif
    }

    fun getWindowId(): Int? = (mc.currentScreen as? GuiChest)?.inventorySlots?.windowId

    enum class ClickType(val id: Int) {
        NORMAL(0),
        SHIFT(1),
        HOTBAR(2),
        MIDDLE(3),
        DROP(4),
        ;

        companion object {
            fun getTypeById(id: Int) = entries.firstOrNull { it.id == id }
        }
    }
}