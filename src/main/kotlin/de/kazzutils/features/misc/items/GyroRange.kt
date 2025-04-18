package de.kazzutils.features.misc.items

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.RenderUtils
import de.kazzutils.utils.skyblockfeatures.ItemUtils
import net.minecraft.item.ItemStack
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class GyroRange {

    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if (!KazzUtils.config.gyroRange) return
        val heldItem: ItemStack? = ItemUtils.heldItem
        val itemId: String? = if (heldItem != null) ItemUtils.itemId(heldItem) else null
        val color: Color = KazzUtils.config.gyroColor
        if (itemId != "GYROKINETIC_WAND") return
        val pos = mc.thePlayer.rayTrace(25.0, event.partialTicks).blockPos
        val block = mc.theWorld.getBlockState(pos).block
        if (block.isAir(mc.theWorld, pos)) return

        RenderUtils.drawCircle(
            Vec3(pos).addVector(0.5, 1.2, 0.5),
            10f,
            2.5f+KazzUtils.config.gyroSize,
            40,
            0f, 0f, 0f,
            color.red / 255f,
            color.green / 255f,
            color.blue / 255f,
            color.alpha / 255f,
            KazzUtils.config.gyroPhased,
            KazzUtils.config.gyroFilled
        )

    }

}