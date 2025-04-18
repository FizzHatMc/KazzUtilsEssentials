package de.kazzutils.gui.editing

import de.kazzutils.KazzUtils
import de.kazzutils.core.GuiManager
import de.kazzutils.core.PersistentSave
import de.kazzutils.gui.ReopenableGUI
import de.kazzutils.gui.editing.components.LocationComponent
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.dsl.childOf


class ElementaEditingGui : WindowScreen(ElementaVersion.V2), ReopenableGUI {
    override fun initScreen(width: Int, height: Int) {
        super.initScreen(width, height)
        KazzUtils.guiManager.elements.forEach { (_, element) ->
            if (!element.toggled) return@forEach
            LocationComponent(element) childOf window
        }
    }

    override fun onScreenClose() {
        super.onScreenClose()
        PersistentSave.markDirty<GuiManager>()
    }
}