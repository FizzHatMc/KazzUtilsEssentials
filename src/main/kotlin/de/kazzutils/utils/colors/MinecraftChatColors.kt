/*
 * Skytils - Hypixel Skyblock Quality of Life Mod
 * Copyright (C) 2020-2023 Skytils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.kazzutils.utils.colors

/**
 * Taken from Wynntils under GNU Affero General Public License v3.0
 * https://github.com/Wynntils/Wynntils/blob/development/LICENSE
 * @author Wynntils
 */
class MinecraftChatColors private constructor(rgb: Int) : de.kazzutils.utils.colors.CustomColor.SetBase(rgb) {
    companion object {
        val BLACK = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x000000)
        val DARK_BLUE = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x0000AA)
        val DARK_GREEN = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x00AA00)
        val DARK_AQUA = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x00AAAA)
        val DARK_RED = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xAA0000)
        val DARK_PURPLE = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xAA00AA)
        val GOLD = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xFFAA00)
        val GRAY = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xAAAAAA)
        val DARK_GRAY = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x555555)
        val BLUE = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x5555FF)
        val GREEN = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x55FF55)
        val AQUA = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0x55FFFF)
        val RED = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xFF5555)
        val LIGHT_PURPLE = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xFF55FF)
        val YELLOW = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xFFFF55)
        val WHITE = _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors(0xFFFFFF)
        private val colors = arrayOf(
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.BLACK,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.DARK_BLUE,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.DARK_GREEN,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.DARK_AQUA,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.DARK_RED,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.DARK_PURPLE,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.GOLD,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.GRAY,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.DARK_GRAY,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.BLUE,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.GREEN,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.AQUA,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.RED,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.LIGHT_PURPLE,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.YELLOW,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.WHITE
        )
        private val names = arrayOf(
            "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA",
            "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY",
            "DARK_GRAY", "BLUE", "GREEN", "AQUA",
            "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"
        )
        val set = _root_ide_package_.de.kazzutils.utils.colors.ColorSet(
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.colors,
            _root_ide_package_.de.kazzutils.utils.colors.MinecraftChatColors.Companion.names
        )


    }
}