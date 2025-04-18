package de.kazzutils.utils

import com.google.common.collect.ComparisonChain
import com.google.common.collect.Ordering
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.randomutils.ChatUtils
import de.kazzutils.utils.randomutils.NumberUtils
import de.kazzutils.utils.randomutils.NumberUtils.getNumber
import de.kazzutils.utils.randomutils.TabUtils.PlayerInfoOrdering
import de.kazzutils.utils.randomutils.TabUtils.gardenLevel
import de.kazzutils.utils.randomutils.TabUtils.gardenPercent
import de.kazzutils.utils.randomutils.TabUtils.playerClass
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.WorldSettings.GameType
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.regex.Pattern
import java.util.stream.Collectors

object NewTabUtils {
    private val areaPattern: Pattern = Pattern.compile("Area: (.+)")
    private var trim: String = ""
    private var p: PlayerInfoOrdering = PlayerInfoOrdering()
    var area: String = ""

    fun parseTabEntries() {
        if (mc.thePlayer == null) return
        if (mc.theWorld == null) return

        val scoreboardList = mapNotNull(fetchTabEntries())

        for (line in scoreboardList) {
            trim = line!!.trim { it <= ' ' }
            val matcher = areaPattern.matcher(trim)
            if (matcher.find()) {
                area = matcher.group(1)
            }

            val classMap = mapOf(
                "(Archer" to "Archer",
                "(Tank" to "Tank",
                "(Mage" to "Mage",
                "(Healer" to "Healer",
                "(Berserk" to "Berserk"
            )

            when{
                trim.contains("Dungeon: Catacombs") -> {
                    area = "Catacombs"
                }

                /*
                TODO -> Garden ill skip for now add later
                trim.contains("Garden Level:") -> {
                    val lvlAndPercent = trim.split(':')[1]
                    val lvl = lvlAndPercent.split("(")[0].trim()
                    val hasPercent = lvlAndPercent.contains("(")
                    val percent = if(hasPercent) lvlAndPercent.trim().substring(1,lvlAndPercent.length-2) else "N/A"
                    val lvlAsInt = lvl.getNumber() ?: run {
                        if(!lvl.contains("XV")){
                            gardenLevel = NumberUtils.toInteger(lvl)
                            gardenPercent = trim.substring(trim.indexOf("(") + 1, trim.indexOf(")") - 1).toDouble()
                        }else{
                            gardenLevel = NumberUtils.toInteger("XV")
                        }
                    }


                    ChatUtils.messageToChat("Split: $lvlAndPercent | $lvl | $percent")
                }
                 */


                classMap.keys.any { line.contains(it) } -> {
                    val roleKey = classMap.keys.firstOrNull { line.contains(it) }
                    val playerName = line.split(" ").getOrNull(1)
                    if (roleKey != null && playerName != null && playerName.contains(mc.thePlayer.name)) {
                        ChatUtils.messageToChat("Users Dungeon class -> ${classMap[roleKey]}")
                        playerClass = classMap[roleKey] ?: ""
                    }
                }
            }

        }
    }

    @SideOnly(Side.CLIENT)
    class PlayerInfoOrdering : Ordering<NetworkPlayerInfo?>() {
        override fun compare(info1: NetworkPlayerInfo?, info2: NetworkPlayerInfo?): Int {
            if (info1 == null) return -1
            if (info2 == null) return 0
            return ComparisonChain.start()
                .compareTrueFirst(!isSpectator(info1.gameType), !isSpectator(info2.gameType))
                .compare(
                    if (info1.playerTeam != null) info1.playerTeam.registeredName else "",
                    if (info2.playerTeam != null) info2.playerTeam.registeredName else ""
                )
                .compare(info1.gameProfile.name, info2.gameProfile.name)
                .result()
        }
    }

    private fun isSpectator(gameType: GameType): Boolean {
        return gameType == GameType.SPECTATOR
    }

    private fun stripVanillaMessage(orignalMessage: String): String {
        var message = orignalMessage
        while (message.startsWith("§r")) {
            message = message.substring(2)
        }
        while (message.endsWith("§r")) {
            message = message.substring(0, message.length - 2)
        }
        return message
    }

    private fun fetchTabEntries(): List<NetworkPlayerInfo> {
        var entries = emptyList<NetworkPlayerInfo>()
        if (mc.thePlayer != null) {
            entries = p.sortedCopy(mc.thePlayer.sendQueue.playerInfoMap)
        }
        return entries
    }

    private fun mapNotNull(tabEntries: List<NetworkPlayerInfo>): List<String?> {
        return tabEntries.stream()
            .map { info: NetworkPlayerInfo ->
                if (info.displayName != null) info.displayName.unformattedText else null
            }
            .filter { displayName: String? -> displayName != null }
            .collect(Collectors.toList())
    }


}