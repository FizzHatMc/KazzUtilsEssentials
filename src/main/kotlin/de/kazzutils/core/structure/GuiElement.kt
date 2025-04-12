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
package de.kazzutils.core.structure

import de.kazzutils.utils.graphics.ScreenRenderer
import de.kazzutils.utils.graphics.SmartFontRenderer
import gg.essential.universal.UResolution

abstract class GuiElement(var name: String, var scale: Float = 1f, var x: Float, var y: Float, var textShadow: SmartFontRenderer.TextShadow = SmartFontRenderer.TextShadow.NORMAL) {
    constructor(name: String, scale: Float = 1f, x: Int, y: Int, textShadow: SmartFontRenderer.TextShadow = SmartFontRenderer.TextShadow.NORMAL) : this(
        name,
        scale,
        (x / UResolution.scaledWidth).toFloat(),
        (y / UResolution.scaledHeight).toFloat(),
        textShadow
    )

    abstract fun render()
    abstract fun demoRender()
    abstract val toggled: Boolean

    fun setPos(x: Int, y: Int) {
        val fX = x / sr.scaledWidth.toFloat()
        val fY = y / sr.scaledHeight.toFloat()
        setPos(fX, fY)
    }

    fun setPos(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    val scaleX: Float
        get() {
            val maxX = UResolution.scaledWidth
            return maxX * x
        }
    val scaleY: Float
        get() {
            val maxY = UResolution.scaledHeight
            return maxY * y
        }
    abstract val height: Int
    abstract val width: Int
    val scaleHeight: Float
        get() = height * scale
    val scaleWidth: Float
        get() = width * scale

    companion object {
        val sr = UResolution
        val fr by lazy {
            ScreenRenderer.fontRenderer
        }
    }


}