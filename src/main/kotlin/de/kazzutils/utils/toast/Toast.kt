package de.kazzutils.utils.toast

import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIImage
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.*
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.state.BasicState

open class Toast(title: String, image: UIComponent? = null, subtext: String = "") : UIContainer() {
    val titleState = BasicState(title)
    val subtextState = BasicState(subtext)

    val background by UIImage.ofResource("/assets/kazzutils/gui/toast.png").constrain {
        width = ImageAspectConstraint()
        height = 32.pixels
    } childOf this

    init {
        image?.constrain {
            width = 16.pixels
            height = 16.pixels
            y = CenterConstraint()
            x = SiblingConstraint() + 8.pixels
        }?.childOf(background)
    }

    val textContainer by UIContainer().constrain {
        width = (160 - 16 - (if (image != null) 24 else 0)).pixels
        height = 27.pixels
        y = CenterConstraint()
        x = SiblingConstraint() + 8.pixels
    } childOf background

    val title by UIText().bindText(titleState).constrain {
        x = 0.pixels
        y = 0.pixels
    } childOf textContainer

    val subText by UIWrappedText().bindText(subtextState).constrain {
        x = 0.pixels
        y = SiblingConstraint(2f)
        width = RelativeConstraint()
    } childOf textContainer


    init {
        constrain {
            x = 0.pixels(alignOpposite = true, alignOutside = true)
            width = ChildBasedMaxSizeConstraint()
            height = ChildBasedMaxSizeConstraint()
        }
    }

    fun animateIn() {
        animate {
            setXAnimation(
                Animations.OUT_EXP,
                0.6f,
                0.pixels(alignOpposite = true)
            )
            onComplete {
                animateOut()
            }
        }
    }

    fun animateOut() {
        animate {
            setXAnimation(
                Animations.IN_EXP,
                0.6f,
                0.pixels(alignOpposite = true, alignOutside = true),
                2500 / 1000f
            )
            onComplete {
                hide()
            }
        }
    }
}