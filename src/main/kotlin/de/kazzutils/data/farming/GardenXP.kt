package de.kazzutils.data.farming

object GardenXP {

    fun getGardenXp(currentlvl: Int, percent: Double): Int {
        var xp = 0.0
        when (currentlvl) {
            1 -> xp = 70 * (percent / 100)
            2 -> xp = 100 * (percent / 100)
            3 -> xp = 140 * (percent / 100)
            4 -> xp = 240 * (percent / 100)
            5 -> xp = 600 * (percent / 100)
            6 -> xp = 1500 * (percent / 100)
            7 -> xp = 2000 * (percent / 100)
            8 -> xp = 2500 * (percent / 100)
            9 -> xp = 3000 * (percent / 100)
            10, 12, 11, 13, 14 -> xp = 10000 * (percent / 100)
        }
        return xp.toInt()
    }

    fun getMaxXp(currentlvl: Int): Int {
        var xp = 0.0

        when (currentlvl) {
            1 -> xp = 70.0
            2 -> xp = 100.0
            3 -> xp = 140.0
            4 -> xp = 240.0
            5 -> xp = 600.0
            6 -> xp = 1500.0
            7 -> xp = 2000.0
            8 -> xp = 2500.0
            9 -> xp = 3000.0
            10, 12, 11, 13, 14 -> xp = 10000.0
        }
        return xp.toInt()
    }


}