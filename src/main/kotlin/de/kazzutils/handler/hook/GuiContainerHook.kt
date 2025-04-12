package de.kazzutils.handler.hook
import de.kazzutils.event.GuiContainerEvent
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Slot
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

class GuiContainerHook(guiAny: Any) {

    val gui: GuiContainer

    init {
        gui = guiAny as GuiContainer
    }

    fun closeWindowPressed(ci: CallbackInfo) {
        if (GuiContainerEvent.CloseWindowEvent(gui, gui.inventorySlots).postAndCatch()) ci.cancel()
    }

    fun backgroundDrawn(mouseX: Int, mouseY: Int, partialTicks: Float, ci: CallbackInfo) {
        GuiContainerEvent.BackgroundDrawnEvent(
            gui,
            gui.inventorySlots,
            mouseX,
            mouseY,
            partialTicks
        ).postAndCatch()
    }

    fun foregroundDrawn(mouseX: Int, mouseY: Int, partialTicks: Float, ci: CallbackInfo) {
        GuiContainerEvent.ForegroundDrawnEvent(gui, gui.inventorySlots, mouseX, mouseY, partialTicks).postAndCatch()
    }

    fun onDrawSlot(slot: Slot, ci: CallbackInfo) {
        if (GuiContainerEvent.DrawSlotEvent.Pre(
                gui,
                gui.inventorySlots,
                slot
            ).postAndCatch()
        ) ci.cancel()
    }

    fun onDrawSlotPost(slot: Slot, ci: CallbackInfo) {
        GuiContainerEvent.DrawSlotEvent.Post(gui, gui.inventorySlots, slot).postAndCatch()
    }

    fun onMouseClick(slot: Slot?, slotId: Int, clickedButton: Int, clickType: Int, ci: CallbackInfo) {
        if (
            GuiContainerEvent.SlotClickEvent(
                gui,
                gui.inventorySlots,
                slot,
                slotId,
                clickedButton,
                clickType
            ).postAndCatch()
        ) ci.cancel()
    }
}