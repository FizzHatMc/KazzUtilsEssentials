package de.kazzutils.utils.randomutils

object NumberUtils {

    private val map: Map<Int, String> = mapOf(
        1000 to "M",
        900 to "CM",
        500 to "D",
        400 to "CD",
        100 to "C",
        90 to "XC",
        50 to "L",
        40 to "XL",
        10 to "X",
        9 to "IX",
        5 to "V",
        4 to "IV",
        1 to "I"
    )





    fun intToRoman(number: Int): String {
        var remaining = number
        var romanNumeral = ""

        // Iterate through the map in descending order of keys (largest values first)
        for ((value, symbol) in map.entries) {
            while (remaining >= value) {
                romanNumeral += symbol
                remaining -= value
            }
        }

        return romanNumeral
    }



    fun toInteger(s: String): Int {
        val nums = IntArray(s.length)
        for (i in s.indices) {
            when (s[i]) {
                'M' -> nums[i] = 1000
                'D' -> nums[i] = 500
                'C' -> nums[i] = 100
                'L' -> nums[i] = 50
                'X' -> nums[i] = 10
                'V' -> nums[i] = 5
                'I' -> nums[i] = 1
            }
        }
        var sum = 0
        for (i in 0 until nums.size - 1) {
            if (nums[i] < nums[i + 1]) sum -= nums[i]
            else sum += nums[i]
        }
        return sum + nums[nums.size - 1]
    }

    fun String.getNumber(): Int? {
        return if (this.matches("\\d+".toRegex())) {
            this.toInt()
        } else {
            null
        }
    }

}