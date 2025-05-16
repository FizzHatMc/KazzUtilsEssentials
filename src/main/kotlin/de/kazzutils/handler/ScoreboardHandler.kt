package de.kazzutils.handler

import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import net.minecraft.client.Minecraft
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScoreObjective
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.util.StringUtils

object ScoreboardHandler {

    fun cleanSB(scoreboard: String): String {
        val nvString = StringUtils.stripControlCodes(scoreboard).toCharArray()
        val cleaned = StringBuilder()

        for (c in nvString) {
            if (c.code in 21..126) {
                cleaned.append(c)
            }
        }

        return cleaned.toString()
    }

    fun getSidebarLines(): List<String> {
        val lines = mutableListOf<String>()
        val mc = Minecraft.getMinecraft()
        val world = mc.theWorld ?: return lines
        val scoreboard = world.scoreboard ?: return lines

        val objective = scoreboard.getObjectiveInDisplaySlot(1) ?: return lines

        var scores: Collection<Score> = scoreboard.getSortedScores(objective)
        val list = scores.filter {
            it != null && it.playerName != null && !it.playerName.startsWith("#")
        }

        scores = if (list.size > 15) {
            Lists.newArrayList(Iterables.skip(list, list.size - 15))
        } else {
            list
        }

        for (score in scores) {
            val team = scoreboard.getPlayersTeam(score.playerName)
            lines.add(ScorePlayerTeam.formatPlayerName(team, score.playerName))
        }

        return lines
    }
}
