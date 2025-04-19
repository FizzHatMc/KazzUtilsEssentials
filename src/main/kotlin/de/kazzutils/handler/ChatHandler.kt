package de.kazzutils.handler


import net.minecraft.client.gui.GuiTextField

object ChatHandler {
    fun getWord(input: String, cursor: Int): String {
        return input.substring(getStartOfWord(input, cursor), getEndOfWord(input, cursor))
    }

    fun replaceWord(field: GuiTextField, replacement: String): Boolean {
        val input = field.text
        val cursor = field.cursorPosition
        val start = getStartOfWord(input, cursor)
        val end = getEndOfWord(input, cursor)
        val output = input.substring(0, start) + replacement + input.substring(end)
        if (output.length > field.maxStringLength) return false
        field.text = output
        return true
    }

    private fun getStartOfWord(input: String, cursor: Int): Int {
        if (cursor == 0) return 0
        if (input[cursor - 1] == ' ') return cursor
        for (i in cursor - 1 downTo 1) {
            if (input[i - 1] == ' ') return i
        }
        return 0
    }

    private fun getEndOfWord(input: String, cursor: Int): Int {
        if (cursor == input.length - 1) return cursor
        for (i in cursor..<input.length) {
            if (input[i] == ' ') return i
        }
        return input.length
    }

}