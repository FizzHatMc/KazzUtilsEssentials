package de.kazzutils.gui.editing

import de.kazzutils.KazzUtils
import de.kazzutils.core.GuiManager
import de.kazzutils.core.PersistentSave
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.core.structure.LocationButton
import de.kazzutils.core.structure.ResizeButton
import de.kazzutils.core.structure.ResizeButton.Corner
import de.kazzutils.gui.ReopenableGUI
import de.kazzutils.utils.graphics.SmartFontRenderer
import gg.essential.universal.UResolution
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display
import java.awt.Color

open class VanillaEditingGui : GuiScreen(), ReopenableGUI {
    private var xOffset = 0f
    private var yOffset = 0f
    private var resizing = false
    private var resizingCorner: ResizeButton.Corner? = null
    private var dragging: GuiElement? = null
    private val locationButtons: MutableMap<GuiElement?, LocationButton> = HashMap()
    private var scaleCache = 0f
    override fun doesGuiPauseGame() = false

    override fun initGui() {
        for ((_, value) in KazzUtils.guiManager.elements) {
            val lb = LocationButton(value)
            buttonList.add(lb)
            locationButtons[value] = lb
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        onMouseMove(mouseX, mouseY)
        recalculateResizeButtons()
        drawGradientRect(0, 0, width, height, Color(0, 0, 0, 50).rgb, Color(0, 0, 0, 200).rgb)
        for (button in buttonList) {
            if (button is LocationButton) {
                if (button.element.toggled) {
                    GlStateManager.pushMatrix()
                    val scale = button.element.scale
                    GlStateManager.translate(button.x, button.y, 0f)
                    GlStateManager.scale(scale.toDouble(), scale.toDouble(), 1.0)
                    button.drawButton(mc, mouseX, mouseY)
                    GlStateManager.popMatrix()
                    if (button.isMouseOver) {
                        GlStateManager.translate(0f, 0f, 100f)
                        drawHoveringText(listOf(button.element.name), mouseX, mouseY)
                        GlStateManager.translate(0f, 0f, -100f)
                    }
                }
            } else if (button is ResizeButton) {
                val element = button.element
                GlStateManager.pushMatrix()
                val scale = element.scale
                GlStateManager.translate(button.x, button.y, 0f)
                GlStateManager.scale(scale.toDouble(), scale.toDouble(), 1.0)
                button.drawButton(mc, mouseX, mouseY)
                GlStateManager.popMatrix()
            } else {
                button.drawButton(mc, mouseX, mouseY)
            }
        }
    }

    public override fun actionPerformed(button: GuiButton) {
        val sr = UResolution
        val minecraftScale = sr.scaleFactor.toFloat()
        val floatMouseX = Mouse.getX() / minecraftScale
        val floatMouseY = (mc.displayHeight - Mouse.getY()) / minecraftScale
        if (button is LocationButton) {
            dragging = button.element
            xOffset = floatMouseX - dragging!!.scaleX
            yOffset = floatMouseY - dragging!!.scaleY
        } else if (button is ResizeButton) {
            dragging = button.element
            resizing = true
            scaleCache = button.element.scale
            xOffset = floatMouseX - button.x
            yOffset = floatMouseY - button.y
            resizingCorner = button.corner
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        when (mouseButton) {
            1 -> buttonList.filterIsInstance<LocationButton>().filter { it.mousePressed(mc, mouseX, mouseY) }.forEach {
                it.element.setPos(10, 10)
                it.element.scale = 1f
            }
            2 -> buttonList.filterIsInstance<LocationButton>().filter { it.mousePressed(mc, mouseX, mouseY) }.forEach {
                it.element.textShadow = SmartFontRenderer.TextShadow.entries[(it.element.textShadow.ordinal + 1) % SmartFontRenderer.TextShadow.entries.size]
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    /**
     * Set the coordinates when the mouse moves.
     */
    private fun onMouseMove(mouseX: Int, mouseY: Int) {
        val sr = UResolution
        val minecraftScale = sr.scaleFactor.toFloat()
        val floatMouseX = Mouse.getX() / minecraftScale
        val floatMouseY = (Display.getHeight() - Mouse.getY()) / minecraftScale
        if (resizing) { //TODO Fix rescaling for top right, top left, and bottom right corners
            val locationButton = locationButtons[dragging] ?: return
            when (resizingCorner) {
                Corner.BOTTOM_RIGHT -> {
                    val scaledX1 = locationButton.x
                    val scaledY1 = locationButton.y
                    val width = locationButton.x2 - locationButton.x
                    val height = locationButton.y2 - locationButton.y
                    val newWidth = floatMouseX - scaledX1
                    val newHeight = floatMouseY - scaledY1
                    val scaleX = newWidth / width
                    val scaleY = newHeight / height
                    val newScale = scaleX.coerceAtLeast(scaleY / 2).coerceAtLeast(0.01f)
                    locationButton.element.scale *= newScale
                }

                Corner.TOP_LEFT -> {
                }

                Corner.TOP_RIGHT -> {
                    val scaledX = locationButton.x
                    val scaledY = locationButton.y2
                    val width = locationButton.x2 - locationButton.x
                    val height = locationButton.y2 - locationButton.y
                    val newWidth = floatMouseX - scaledX
                    val newHeight = scaledY - floatMouseY
                    val scaleX = newWidth / width
                    val scaleY = newHeight / height
                    val newScale = scaleX.coerceAtLeast(scaleY).coerceAtLeast(0.01f)
                    locationButton.element.scale *= newScale
                    locationButton.element.setPos(locationButton.element.x, (scaledY - newHeight) / sr.scaledHeight)
                }

                Corner.BOTTOM_LEFT -> {
                }

                null -> {}
            }

            locationButton.drawButton(mc, mouseX, mouseY)
            recalculateResizeButtons()
        } else if (dragging != null) {
            val x = (floatMouseX - xOffset) / sr.scaledWidth.toFloat()
            val y = (floatMouseY - yOffset) / sr.scaledHeight.toFloat()
            dragging!!.setPos(x, y)
            addResizeCorners(dragging!!)
        }
    }

    private fun addResizeCorners(element: GuiElement) {
        buttonList.removeIf { button: GuiButton? -> button is ResizeButton && button.element === element }
        buttonList.removeIf { button: GuiButton? -> button is ResizeButton && button.element !== element }
        val locationButton = locationButtons[element] ?: return
        val boxXOne = locationButton.x - ResizeButton.SIZE * element.scale
        val boxXTwo = locationButton.x + element.scaleWidth + ResizeButton.SIZE * 2 * element.scale
        val boxYOne = locationButton.y - ResizeButton.SIZE * element.scale
        val boxYTwo = locationButton.y + element.scaleHeight + ResizeButton.SIZE * 2 * element.scale
        buttonList.add(ResizeButton(boxXOne, boxYOne, element, Corner.TOP_LEFT))
        buttonList.add(ResizeButton(boxXTwo, boxYOne, element, Corner.TOP_RIGHT))
        buttonList.add(ResizeButton(boxXOne, boxYTwo, element, Corner.BOTTOM_LEFT))
        buttonList.add(ResizeButton(boxXTwo, boxYTwo, element, Corner.BOTTOM_RIGHT))
    }

    private fun recalculateResizeButtons() {
        for (button in buttonList) {
            if (button is ResizeButton) {
                val corner = button.corner
                val element = button.element
                val locationButton = locationButtons[element] ?: continue
                val boxXOne = locationButton.x - ResizeButton.SIZE * element.scale
                val boxXTwo = locationButton.x + element.scaleWidth + ResizeButton.SIZE * element.scale
                val boxYOne = locationButton.y - ResizeButton.SIZE * element.scale
                val boxYTwo = locationButton.y + element.scaleHeight + ResizeButton.SIZE * element.scale
                when (corner) {
                    Corner.TOP_LEFT -> {
                        button.x = boxXOne
                        button.y = boxYOne
                    }

                    Corner.TOP_RIGHT -> {
                        button.x = boxXTwo
                        button.y = boxYOne
                    }

                    Corner.BOTTOM_LEFT -> {
                        button.x = boxXOne
                        button.y = boxYTwo
                    }

                    Corner.BOTTOM_RIGHT -> {
                        button.x = boxXTwo
                        button.y = boxYTwo
                    }
                }
            }
        }
    }


    override fun handleMouseInput() {
        super.handleMouseInput()
        val hovered = LocationButton.lastHoveredElement
        if (hovered != null) {
            hovered.scale = (hovered.scale + Mouse.getEventDWheel() / 1000f).coerceAtLeast(0.01f)
        }
    }

    /**
     * Reset the dragged feature when the mouse is released.
     */
    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
        dragging = null
        resizing = false
        scaleCache = 0f
    }

    /**
     * Saves the positions when the gui is closed
     */
    override fun onGuiClosed() {
        PersistentSave.markDirty<GuiManager>()
    }
}