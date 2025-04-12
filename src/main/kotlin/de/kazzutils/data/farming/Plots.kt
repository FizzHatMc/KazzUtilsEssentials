package de.kazzutils.data.farming

import net.minecraft.util.BlockPos

class Plots {

    var infectedAmount: Int = 0
    var plotNumber: Int = 0
    var plotPos: BlockPos? = null
    var plotName: String = ""

    fun Plots(){}

    fun Plots(infected: Int, plotNumber: Int, plotName: String) {
        this.infectedAmount = infected
        this.plotNumber = plotNumber
        this.plotPos = getBlockPosFromPlotNumber(plotNumber)
        this.plotName = plotName
    }

    fun Plots(infected: Int, plotName: String) {
        this.infectedAmount = infected
        this.plotName = plotName
    }


    /*

-192,-192   -96,-192    0,-192   96,-192    192,-192
-192,-96    -96,-96     0,-96    96,-96     192,-96
-192,0      -96,0       0,0      96,0       192,0
-192,96     -96,96      0,96     96,96      192,96

-192,192    -96,192     0,192    96,192     192,192

     21 13 9 14  22
     15 5  1  6  16
     10 2  x  3  11
     17 7  4  8  18
     23 19 12 20 24

     2  3   4  5  6
     11 12 13 14 15
     20 21 22 24 24
     29 30 31 32 33
     38 39 40 41 42

     */

    fun slotNumberToPlotNumber(slotNumber: Int): Int {
        return when (slotNumber) {
            2 -> 21
            3 -> 13
            4 -> 9
            5 -> 14
            6 -> 22
            11 -> 15
            12 -> 5
            13 -> 1
            14 -> 6
            15 -> 16
            20 -> 10
            21 -> 2
            23 -> 4
            24 -> 3
            29 -> 11
            30 -> 12
            31 -> 4
            32 -> 8
            33 -> 18
            38 -> 20
            39 -> 19
            40 -> 12
            41 -> 24
            42 -> 24

            else -> 0
        }
    }


    fun getBlockPosFromPlotNumber(PlotNumber: Int): BlockPos {
        var pos: BlockPos? = null

        return when (PlotNumber) {
            1 -> BlockPos(0, 75, -96).also { pos = it }
            2 -> BlockPos(-96, 75, 0).also { pos = it }
            3 -> BlockPos(96, 75, 0).also { pos = it }
            4 -> BlockPos(0, 75, 96).also { pos = it }
            5 -> BlockPos(-96, 75, -96).also { pos = it }
            6 -> BlockPos(96, 75, -96).also { pos = it }
            7 -> BlockPos(-96, 75, 96).also { pos = it }
            8 -> BlockPos(96, 75, 96).also { pos = it }

            9 -> BlockPos(0, 75, -192).also { pos = it }
            10 -> BlockPos(-192, 75, 0).also { pos = it }
            11 -> BlockPos(192, 75, 0).also { pos = it }
            12 -> BlockPos(0, 75, 192).also { pos = it }

            13 -> BlockPos(-96, 75, -192).also { pos = it }
            14 -> BlockPos(96, 75, -192).also { pos = it }
            15 -> BlockPos(-192, 75, -96).also { pos = it }
            16 -> BlockPos(192, 75, -96).also { pos = it }
            17 -> BlockPos(-192, 75, 96).also { pos = it }
            18 -> BlockPos(192, 75, 96).also { pos = it }
            19 -> BlockPos(-96, 75, 192).also { pos = it }
            20 -> BlockPos(96, 75, 192).also { pos = it }

            21 -> BlockPos(-192, 75, -192).also { pos = it }
            22 -> BlockPos(192, 75, -192).also { pos = it }
            23 -> BlockPos(-192, 75, 192).also { pos = it }
            24 -> BlockPos(192, 75, 192).also { pos = it }

            else -> BlockPos(0, 0, 0).also { pos = it }
        }
    }

}