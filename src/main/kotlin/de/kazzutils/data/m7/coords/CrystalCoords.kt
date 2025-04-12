package de.kazzutils.data.m7.coords

import net.minecraft.util.BlockPos

object CrystalCoords {

    fun getCrystal(clas: Int): BlockPos? {
        var re: BlockPos? = null
        if (clas == 1) {
            re = BlockPos(64, 238, 50)
        } else if (clas == 3) {
            re = BlockPos(82, 238, 50)
        }
        return re
    }
}