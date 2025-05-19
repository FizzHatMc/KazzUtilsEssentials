package de.kazzutils.gui

import de.kazzutils.KazzUtils
import de.kazzutils.gui.components.SimpleButton
import de.kazzutils.gui.editing.ElementaEditingGui
import de.kazzutils.gui.editing.VanillaEditingGui
import de.kazzutils.utils.Utils.openGUI
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.RainbowColorConstraint
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.universal.UDesktop
import gg.essential.universal.UKeyboard
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import java.net.URI

class OptionsGui(val parent: GuiScreen? = null) :
    WindowScreen(ElementaVersion.V2) {
//newGuiScale = EssentialAPI.getGuiUtil().getGuiScale()


    private val kazzText: UIText =
        UIText("KazzUtils", shadow = false).childOf(window).constrain {
            x = CenterConstraint()
            y = RelativeConstraint(0.075f)
            textScale = basicTextScaleConstraint { window.getHeight() / 40 }
        }

    private var orderIndex = 0

    init {
        SimpleButton("Config").childOf(window).constrain {
            x = CenterConstraint()
            y = SiblingConstraint() + RelativeConstraint(0.075f)
            width = 200.pixels()
            height = 20.pixels()
        }.onMouseClick {
            KazzUtils.config.openGUI()
        }
        SimpleButton("Edit Aliases").childOf(window).constrain {
            x = CenterConstraint()
            y = SiblingConstraint() + 2.pixels()
            width = 200.pixels()
            height = 20.pixels()
        }.onMouseClick {
            //TODO ALIAS
        }
        SimpleButton("Edit Locations").childOf(window).constrain {
            x = CenterConstraint()
            y = SiblingConstraint() + 2.pixels()
            width = 200.pixels()
            height = 20.pixels()
        }.onMouseClick {
            mc.displayGuiScreen(
                if (it.mouseButton == 1) ElementaEditingGui()
                else VanillaEditingGui()
            )
        }
        SimpleButton("Edit Key Shortcuts").childOf(window).constrain {
            x = CenterConstraint()
            y = SiblingConstraint() + 2.pixels()
            width = 200.pixels()
            height = 20.pixels()
        }.onMouseClick {
            mc.displayGuiScreen(KeyShortcutsGui())
        }


        SimpleButton("Open Config Folder").childOf(window).constrain {
            x = CenterConstraint()
            y = SiblingConstraint() + 2.pixels()
            width = 200.pixels()
            height = 20.pixels()
        }.onMouseClick {
            UDesktop.open(KazzUtils.modDir)
        }

        SimpleButton("Discord").childOf(window).constrain {
            x = basicXConstraint { window.getWidth() - this.getWidth() - 3 }
            y = basicYConstraint { window.getHeight() - this.getHeight() - 3 }
            width = RelativeConstraint(0.1f)
            height = RelativeConstraint(0.05f)
        }.onMouseClick {
            runCatching {
                UDesktop.browse(URI("https://discord.gg/b9jVA6aHCP"))
            }
        }
        SimpleButton("GitHub").childOf(window).constrain {
            x = basicXConstraint { window.getWidth() - this.getWidth() - 3 }
            y = basicYConstraint { window.getHeight() - this.getHeight() * 2 - 6 }
            width = RelativeConstraint(0.1f)
            height = RelativeConstraint(0.05f)
        }.onMouseClick {
            runCatching {
                UDesktop.browse(URI("https://github.com/FizzHatMc/KazzUtilsEssentials"))
            }
        }

    }



    private fun animate() {
        kazzText.animate {
            setColorAnimation(Animations.IN_OUT_SIN, 1f, RainbowColorConstraint())
                .onComplete {
                    animate()
                }
        }
    }

    override fun setWorldAndResolution(mc: Minecraft, width: Int, height: Int) {
        window.onWindowResize()
        kazzText.constrain {
            textScale = basicTextScaleConstraint { window.getHeight() / 40 }
        }
        super.setWorldAndResolution(mc, width, height)
    }

    companion object {
        private val order = arrayOf(
            UKeyboard.KEY_UP,
            UKeyboard.KEY_UP,
            UKeyboard.KEY_DOWN,
            UKeyboard.KEY_DOWN,
            UKeyboard.KEY_LEFT,
            UKeyboard.KEY_RIGHT,
            UKeyboard.KEY_LEFT,
            UKeyboard.KEY_RIGHT,
            UKeyboard.KEY_B,
            UKeyboard.KEY_A
        )
        private val gamerOrder = arrayOf(
            UKeyboard.KEY_W,
            UKeyboard.KEY_W,
            UKeyboard.KEY_S,
            UKeyboard.KEY_S,
            UKeyboard.KEY_A,
            UKeyboard.KEY_D,
            UKeyboard.KEY_A,
            UKeyboard.KEY_D,
            UKeyboard.KEY_B,
            UKeyboard.KEY_A
        )
    }

    }