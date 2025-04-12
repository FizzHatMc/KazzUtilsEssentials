package de.kazzutils.data.m7.coords

import net.minecraft.util.BlockPos

object CauldronCoords {

    fun getRelic(clas: Int): BlockPos? {
        var re: BlockPos? = null

        when (clas) {
            0 -> re = BlockPos(49.5, 6.5, 44.5)
            1 -> re = BlockPos(59.5, 6.5, 44.5)
            2 -> re = BlockPos(54.5, 6.5, 41.5)
            3 -> re = BlockPos(57.5, 6.5, 42.5)
            4 -> re = BlockPos(51.5, 6.5, 42.5)
        }
        return re
    }

}