package de.kazzutils.data.m7.coords

import net.minecraft.util.BlockPos

object RelicCoords {

    fun getRelic(clas: Int): BlockPos? {
        var re: BlockPos? = null

        when (clas) {
            0 -> re = BlockPos(20, 6, 94)
            1 -> re = BlockPos(91, 6, 94)
            2 -> re = BlockPos(56, 8, 132)
            3 -> re = BlockPos(92, 6, 56)
            4 -> re = BlockPos(20, 6, 59)
        }
        return re
    }
}