package de.kazzutils.utils

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.chat.ChatUtils
import de.kazzutils.utils.colors.ColorFactory.web
import de.kazzutils.utils.colors.CustomColor
import de.kazzutils.utils.colors.CyclingTwoColorGradient
import de.kazzutils.utils.colors.RainbowColor
import gg.essential.vigilance.Vigilant
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.minecraft.client.settings.GameSettings
import net.minecraft.entity.boss.BossStatus
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color
import java.io.File


object Utils {

    var inSkyblock = false
    var bossName = ""

    fun getKeyDisplayStringSafe(keyCode: Int): String =
        runCatching { GameSettings.getKeyDisplayString(keyCode) }.getOrNull() ?: "Key $keyCode"

    fun File.ensureFile() = (parentFile.exists() || parentFile.mkdirs()) && createNewFile()

    private fun getCustomColorFromColor(color: Color) = CustomColor.fromInt(color.rgb)

    fun String.removeMinecraftColorCodes() : String{
        return Regex("§[0-9A-FK-ORa-fk-or]").replace(this, "")
    }

    @SubscribeEvent
    fun onRenderOverlay(event: RenderGameOverlayEvent.Text?) {
        if (BossStatus.hasColorModifier && BossStatus.bossName != null) {
            bossName = BossStatus.bossName
            val health = BossStatus.healthScale

            // Do something with the boss bar name
            ChatUtils.messageToChat("Bossbar Name: $bossName | Health: $health")
        }
    }




    fun checkSkyblock() {
        val player = mc.thePlayer ?: return

        val scoreboard = player.worldScoreboard
        val objective = scoreboard.getObjectiveInDisplaySlot(1) // Get the sidebar objective

        if (objective != null) {
            val displayName = objective.displayName.toString()
            inSkyblock = displayName.contains("SKYBLOCK", true)
        }
    }

    fun customColorFromString(string: String?): CustomColor {
        if (string == null) throw NullPointerException("Argument cannot be null!")
        return if (string.startsWith("rainbow(")) {
            RainbowColor.fromString(string)
        } else if (string.startsWith("cyclingtwocolorgradient(")) {
            CyclingTwoColorGradient.fromString(string)
        } else try {
            getCustomColorFromColor(web(string))
        } catch (e: IllegalArgumentException) {
            try {
                CustomColor.fromInt(string.toInt())
            } catch (ignored: NumberFormatException) {
                throw e
            }
        }
    }


    fun Vigilant.openGUI(): Job = KazzUtils.launch {
        KazzUtils.displayScreen = this@openGUI.gui()
    }


    fun colorFromString(string: String): Color {
        return try {
            web(string)
        } catch (e: IllegalArgumentException) {
            try {
                Color(string.toInt(), true)
            } catch (ignored: NumberFormatException) {
                throw e
            }
        }
    }

}