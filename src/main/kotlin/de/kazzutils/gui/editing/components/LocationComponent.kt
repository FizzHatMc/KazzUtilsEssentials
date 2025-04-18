package de.kazzutils.gui.editing.components

import de.kazzutils.core.structure.GuiElement
import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.MousePositionConstraint
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.utils.withAlpha
import gg.essential.universal.UMatrixStack
import gg.essential.universal.UResolution
import java.awt.Color

class LocationComponent(val element: GuiElement) : UIComponent() {
    init {
        constrain {
            x = RelativeConstraint(element.x)
            y = RelativeConstraint(element.y)
            width = element.scaleWidth.pixels
            height = element.scaleHeight.pixels
        }
        onMouseClick { event ->
            constrain {
                x = MousePositionConstraint() - event.relativeX.pixels
                y = MousePositionConstraint() - event.relativeY.pixels
            }
        }
        onMouseRelease {
            constrain {
                // convert back to relative constraint
                val scaleX = getLeft() / UResolution.scaledWidth
                val scaleY = getTop() / UResolution.scaledHeight
                x = RelativeConstraint(scaleX)
                y = RelativeConstraint(scaleY)
                element.setPos(scaleX, scaleY)
            }
        }
        onMouseScroll { event ->
            element.scale = (element.scale + event.delta).coerceAtLeast(0.01).toFloat()
            constrain {
                width = element.scaleWidth.pixels
                height = element.scaleHeight.pixels
            }
            event.stopImmediatePropagation()
        }
    }

    val background by UIBlock().constrain {
        color = Color.WHITE.withAlpha(40).toConstraint()
        width = RelativeConstraint(1f) + 8.pixels
        height = RelativeConstraint(1f) + 8.pixels
        x = CenterConstraint()
        y = CenterConstraint()
    }.also {
        onMouseEnter {
            it.setColor(Color.WHITE.withAlpha(100))
        }
        onMouseLeave {
            it.setColor(Color.WHITE.withAlpha(40))
        }
    } childOf this

    override fun draw(matrixStack: UMatrixStack) {
        // apply effects
        beforeDraw(matrixStack)
        // draw children
        super.draw(matrixStack)
        matrixStack.push()
        matrixStack.translate(getLeft(), getTop(), 0f)
        matrixStack.scale(element.scale, element.scale, 1f)
        matrixStack.runWithGlobalState(element::demoRender)
        matrixStack.pop()
    }
}