package de.kazzutils.features.dungeons.gui

import de.kazzutils.KazzUtils
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.features.dungeons.gui.DownTimeObject.dtNames
import de.kazzutils.utils.NewTabUtils
import de.kazzutils.utils.RenderUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import de.kazzutils.utils.skyblockfeatures.CatacombsUtils

object HighlightHealerRoute {

    class HighlightHealerRouteDisplay : GuiElement("Healer Display",x=400,y=80){
        override fun render() {
            val test = (CatacombsUtils.floor.contains("F7",true) || CatacombsUtils.inM7) && NewTabUtils.playerClass.contains("Healer",true) && KazzUtils.config.healerRoute && CatacombsUtils.inBossRoom
            var displayText = """
                §aif statement -> $test 
                §aSeperate Statements:
                    §a Floor: ${CatacombsUtils.floor.contains("F7",true)} : ${CatacombsUtils.floor}
                    §a inM7: ${CatacombsUtils.inM7}}
                    §a PlayerClass: ${NewTabUtils.playerClass.contains("Healer",true)} : ${NewTabUtils.playerClass}
                    §a Config: ${KazzUtils.config.healerRoute}
                    §a InBoss: ${CatacombsUtils.inBossRoom}
                """.trimIndent()

            dtNames.forEach{displayText += it + "\n"}
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override fun demoRender() {
            val test = (CatacombsUtils.floor.contains("F7",true) || CatacombsUtils.inM7) && NewTabUtils.playerClass.contains("Healer",true) && KazzUtils.config.healerRoute && CatacombsUtils.inBossRoom
            var displayText = """
                §aif statement -> $test 
                §aSeperate Statements:
                    §a Floor: ${CatacombsUtils.floor.contains("F7",true)} : ${CatacombsUtils.floor}
                    §a inM7: ${CatacombsUtils.inM7}
                    §a PlayerClass: ${NewTabUtils.playerClass.contains("Healer",true)} : ${NewTabUtils.playerClass}
                    §a Config: ${KazzUtils.config.healerRoute}
                    §a InBoss: ${CatacombsUtils.inBossRoom}
                """.trimIndent()
            dtNames.forEach{displayText += it + "\n"}
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * 7
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("§aif statement ->")

        override val toggled: Boolean
            get() = KazzUtils.config.healerRoute

        init {
            KazzUtils.guiManager.registerElement(this)
        }

    }
}