package de.kazzutils.data.m7.coords

import gg.essential.elementa.state.map
import net.minecraft.util.BlockPos
import java.awt.Color

object HealerRoute {

    fun route() : Map<BlockPos, Color> {
        return mapOf(
            BlockPos(68,219,35) to Color.RED,
            BlockPos(69,219,35) to Color.RED,
            BlockPos(69,220,35) to Color.GREEN,

            BlockPos(88,166,41) to Color.RED,
            BlockPos(89,166,41) to Color.RED,
            BlockPos(90,166,41) to Color.RED,
            BlockPos(91,166,41) to Color.RED,
            BlockPos(92,166,41) to Color.RED,
            BlockPos(93,166,41) to Color.RED,
            BlockPos(94,166,41) to Color.RED,
            BlockPos(95,166,41) to Color.RED,
            BlockPos(91,165,41) to Color.RED,
            BlockPos(92,165,41) to Color.RED,
            BlockPos(93,165,41) to Color.RED,
            BlockPos(94,165,41) to Color.RED,
            BlockPos(95,165,41) to Color.RED,
            BlockPos(95,164,41) to Color.RED,

            BlockPos(96,119,121) to Color.RED,
            BlockPos(96,119,122) to Color.RED,
            BlockPos(96,120,122) to Color.RED,
            BlockPos(96,119,123) to Color.RED,
            BlockPos(96,120,123) to Color.RED,

            BlockPos(19,128,136) to Color.RED,
            BlockPos(18,128,136) to Color.RED,
            BlockPos(18,129,136) to Color.RED,
            BlockPos(17,128,136) to Color.RED,
            BlockPos(17,129,136) to Color.RED,

            BlockPos(52,112,110) to Color.RED,
            BlockPos(52,112,111) to Color.RED,
            BlockPos(52,113,111) to Color.GREEN,

            BlockPos(58,63,76) to Color.RED,
            BlockPos(57,62,76) to Color.RED,
            BlockPos(57,63,76) to Color.RED,


            )
    }

    //TODO: Rest of the Route. Currently only until P3A1
    fun walkRoute() : Map<BlockPos, BlockPos>{
        return mapOf(
            BlockPos(73,220,15) to BlockPos(68,220,35),
            BlockPos(68,220,35) to BlockPos(77,164,41),
            BlockPos(77,164,41) to BlockPos(91,165,41),
            BlockPos(91,165,41) to BlockPos(95,165,41),
            BlockPos(95,165,41) to BlockPos(100,114,51),
            BlockPos(100,114,51) to BlockPos(94,114,61),
            BlockPos(94,114,61) to BlockPos(94,118,70),
            BlockPos(94,118,70) to BlockPos(94,120,83),
            BlockPos(94,120,83) to BlockPos(96,121,100),
            BlockPos(96,121,100) to BlockPos(96,120,121),
            BlockPos(96,120,121) to BlockPos(96,113,133),
            BlockPos(96,113,133) to BlockPos(66,106,134),
            BlockPos(66,106,134) to BlockPos(64,129,135),
            BlockPos(64,129,135) to BlockPos(61,131,140),
            BlockPos(61,131,140) to BlockPos(52,131,138),
            BlockPos(52,131,138) to BlockPos(36,130,138),
            BlockPos(36,130,138) to BlockPos(22,130,137),
            BlockPos(22,130,137) to BlockPos(19,129,136),
            BlockPos(19,129,136) to BlockPos(7,114,123),
            BlockPos(7,114,123) to BlockPos(7,112,103),
            BlockPos(7,112,103) to BlockPos(7,112,85),
            BlockPos(7,112,85) to BlockPos(7,106,81),
            BlockPos(7,106,81) to BlockPos(3,119,78),
        )
    }
}