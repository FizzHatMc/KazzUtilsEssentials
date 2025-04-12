package de.kazzutils.data.m7.coords

import net.minecraft.util.BlockPos

object TermCoords {

    var list: Array<BlockPos?> = arrayOfNulls(5)
    fun getTankCoords(area: Int): BlockPos? {
        list[0] = BlockPos(110, 114, 73)
        list[1] = BlockPos(68, 109, 122)
        list[2] = BlockPos(-1, 109, 112)
        list[3] = BlockPos(41, 109, 30)

        return list[area]
    }

    fun getMageCoords(area: Int): BlockPos? {
        list[0] = BlockPos(110, 119, 79)
        list[1] = BlockPos(59, 120, 123)
        list[2] = BlockPos(-1, 119, 93)
        list[3] = BlockPos(44, 121, 30)

        return list[area]
    }

    fun getBersCoords(area: Int): BlockPos? {
        list[0] = BlockPos(90, 112, 92)
        list[1] = BlockPos(47, 109, 122)
        list[2] = BlockPos(40, 124, 123)
        list[3] = BlockPos(18, 123, 93)
        list[4] = BlockPos(67, 109, 30)

        return list[area]
    }

    fun getArchCoords(area: Int): BlockPos? {
        list[0] = BlockPos(90, 122, 101)
        list[1] = BlockPos(39, 108, 140)
        list[2] = BlockPos(-1, 109, 77)
        list[3] = BlockPos(72, 115, 47)

        return list[area]
    }

    fun getHealerCoords(area: Int): BlockPos? {
        list[0] = BlockPos(110, 120, 91)
        list[1] = BlockPos(63, 131, 142)
        list[2] = BlockPos(-1, 119, 74)
        list[3] = BlockPos(63, 127, 34)

        return list[area]
    }
}