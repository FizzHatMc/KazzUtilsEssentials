package de.kazzutils.utils.skyblockfeatures

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.data.enumClass.WitherKingDragons
import de.kazzutils.event.DungeonBossRoomEnterEvent
import de.kazzutils.handler.ScoreboardHandler

import de.kazzutils.utils.chat.ChatUtils
import de.kazzutils.utils.randomutils.StringUtils.removeColor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiPlayerTabOverlay
import net.minecraft.init.Blocks
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.util.BlockPos
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent

class CatacombsUtils {

    private var lastmsg: String = ""
    companion object {
        var inM7: Boolean = false
        var floor: String = "n"
        var inBossRoom = false

        fun inDungeon() : Boolean {
            val scoreboard: List<String> = ScoreboardHandler.getSidebarLines()
            for (s in scoreboard) {
                val sCleaned: String = ScoreboardHandler.cleanSB(s)
                if (sCleaned.contains("The Catacombs")) {
                    return true
                }
            }
            return false
        }

        fun checkCata() {
            val scoreboard: List<String> = ScoreboardHandler.getSidebarLines()
            for (s in scoreboard) {
                val sCleaned: String = ScoreboardHandler.cleanSB(s)
                if (sCleaned.contains("The Catacombs")) {
                    floor = sCleaned.substring(sCleaned.indexOf("(") + 1, sCleaned.indexOf(")"))
                    if (floor == "M7") inM7 = true
                }
            }
        }

        fun handleBossMessage(rawMessage: String) {
            if (!inDungeon()) return

            val message = rawMessage.removeColor()
            val bossName = message.substringAfter("[BOSS] ").substringBefore(":").trim()

            if ((bossName != "The Watcher") && floor != "n" && checkBossName(bossName) && !inBossRoom) {
                DungeonBossRoomEnterEvent.postAndCatch() //IMPORTANT -> Triggers DungeonBossRoomEnterEvent for other classes
                inBossRoom = true
            }else{
                inBossRoom = false
            }
        }

        private fun checkBossName(bossName: String): Boolean {
            val correctBoss = when (floor) {
                "E" -> "The Watcher"
                "F1", "M1" -> "Bonzo"
                "F2", "M2" -> "Scarf"
                "F3", "M3" -> "The Professor"
                "F4", "M4" -> "Thorn"
                "F5", "M5" -> "Livid"
                "F6", "M6" -> "Sadan"
                "F7", "M7" -> "Maxor"
                else -> null
            } ?: return false

            // Livid has a prefix in front of the name, so we check ends with to cover all the livids
            return bossName.endsWith(correctBoss)
        }

    }

    val red: Array<BlockPos> = arrayOf(BlockPos(27, 14, 58), BlockPos(40, 20, 45))
    val orange: Array<BlockPos> = arrayOf(BlockPos(84, 14, 55), BlockPos(77, 18, 59), BlockPos(81, 19, 68))
    val green: Array<BlockPos> = arrayOf(BlockPos(27, 15, 94), BlockPos(24, 19, 82))
    val blue: Array<BlockPos> = arrayOf(BlockPos(84, 14, 94), BlockPos(80, 18, 104))
    val purp: Array<BlockPos> = arrayOf(BlockPos(56, 14, 125), BlockPos(56, 14, 125))



    private fun stripColorCodes(string: String): String {
        return string.replace("§.".toRegex(), "")
    }




    fun getLines(): List<String> {
        val scoreboard: Scoreboard = mc.thePlayer.worldScoreboard
        val sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1)
        val scores: List<Score> = ArrayList(scoreboard.getSortedScores(sidebarObjective))
        val lines: MutableList<String> = ArrayList()
        for (i in scores.indices.reversed()) {
            val score = scores[i]
            val scoreplayerteam1 = scoreboard.getPlayersTeam(score.playerName)
            val line = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.playerName)
            lines.add(line)
        }
        return lines
    }

    fun inM7(): Boolean {
        val lines = getLines()
        if (lines.size < 4) {
            inM7 = false
            return false
        }
        val line = lines[3]
        var unformattedText: String?
        unformattedText = line.replace("\\p{So}|\\p{Sk}".toRegex(), "")
        unformattedText = stripColorCodes(unformattedText)
        if ("  The Catacombs (M7)" == unformattedText) {
            inM7 = true
            return true
        }
        inM7 = false
        return false
    }


    //
    @SubscribeEvent
    fun onTick(event: ClientTickEvent?) {
        if (Minecraft.getMinecraft().theWorld != null) {
            for (drag in WitherKingDragons.entries) {
                val block = Minecraft.getMinecraft().theWorld.getBlockState(drag.detectBlock).block
                //RenderUtils.renderBoxOutline(drag.getBlockPos(),1,1,1,event.partialTicks,1,Color.RED,1F);
                if (block == Blocks.cobblestone) {
                    drag.isDestroyed = false
                } else drag.isDestroyed = true
            }

            val tabOverlay: GuiPlayerTabOverlay = mc.ingameGUI.tabList
        }
    }

    @SubscribeEvent
    fun onChatReceive(event: ClientChatReceivedEvent) {
        lastmsg = event.message.formattedText

        for (drag in WitherKingDragons.entries) {
            if (lastmsg.contains(drag.itemName)) {
                ChatUtils.messageToChat("Recieved " + drag.itemName)
                drag.picked = true
            }
        }
    }




}