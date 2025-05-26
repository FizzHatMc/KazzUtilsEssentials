package de.kazzutils.utils.skyblockfeatures

import de.kazzutils.event.BossbarUpdateEvent
import de.kazzutils.event.WorldChangeEvent
import net.minecraft.entity.boss.BossStatus
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object BossbarData {
    private var bossbar: String? = null
    private var previousServerBossbar = ""

    fun getBossbar() = bossbar.orEmpty()

    @SubscribeEvent
    fun onWorldChange(event: WorldChangeEvent) {
        val oldBossbar = bossbar ?: return
        previousServerBossbar = oldBossbar
        bossbar = null
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onTick(event: TickEvent.PlayerTickEvent) {
        val bossbarLine = BossStatus.bossName ?: return
        if (bossbarLine.isBlank() || bossbarLine.isEmpty()) return
        if (bossbarLine == bossbar) return
        if (bossbarLine == previousServerBossbar) return
        if (previousServerBossbar.isNotEmpty()) previousServerBossbar = ""

        bossbar = bossbarLine
        BossbarUpdateEvent(bossbarLine).postAndCatch()
    }
}