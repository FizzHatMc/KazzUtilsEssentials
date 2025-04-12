package de.kazzutils.handler.hook

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.event.AddChatMessageEvent
import de.kazzutils.event.ItemTossEvent
import de.kazzutils.event.PacketEvent
import de.kazzutils.utils.Utils
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.item.EntityItem
import net.minecraft.network.play.client.C09PacketHeldItemChange
import net.minecraft.network.play.server.S09PacketHeldItemChange
import net.minecraft.util.IChatComponent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

var currentItem: Int? = null


object EntityPlayerSPHook {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onItemChange(event: PacketEvent) {
        if (event.packet is S09PacketHeldItemChange) {
            currentItem = event.packet.heldItemHotbarIndex
        } else if (event.packet is C09PacketHeldItemChange) {
            currentItem = event.packet.slotId
        }
//        ChatUtils.messageToChat("CurrentItem: $currentItem")
    }

}

fun onAddChatMessage(message: IChatComponent, ci: CallbackInfo) {
    if (AddChatMessageEvent(message).postAndCatch()) ci.cancel()
}

fun onDropItem(dropAll: Boolean, cir: CallbackInfoReturnable<EntityItem?>) {
    val stack = mc.thePlayer.inventory.mainInventory[currentItem ?: mc.thePlayer.inventory.currentItem]
    if (stack != null && ItemTossEvent(stack, dropAll).postAndCatch()) cir.returnValue = null
}

fun onKeybindCheck(keyBinding: KeyBinding): Boolean {
    return keyBinding === mc.gameSettings.keyBindSprint && Utils.inSkyblock
}