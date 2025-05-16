package de.kazzutils.data.m7.coords

import net.minecraft.util.BlockPos
import java.awt.Color

object HealerRoute {

    fun route() : Map<BlockPos, Color> {
        val route = mapOf(
            BlockPos(68,220,35) to Color.RED,
            BlockPos(69,221,35) to Color.GREEN,
            BlockPos(88,166,41) to Color.RED,
            BlockPos(89,168,41) to Color.RED,
            BlockPos(90,168,41) to Color.RED,
            BlockPos(91,168,41) to Color.RED,
            BlockPos(92,168,41) to Color.RED,
            BlockPos(93,168,41) to Color.RED,
            BlockPos(94,168,41) to Color.RED,
            BlockPos(95,168,41) to Color.RED,
            BlockPos(91,167,41) to Color.RED,
            BlockPos(92,167,41) to Color.RED,
            BlockPos(93,167,41) to Color.RED,
            BlockPos(94,167,41) to Color.RED,
            BlockPos(95,167,41) to Color.RED,
            BlockPos(95,168,41) to Color.RED,
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

            )
        return route
    }
}