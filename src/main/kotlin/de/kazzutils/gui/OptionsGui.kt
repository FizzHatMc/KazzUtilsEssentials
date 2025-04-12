package de.kazzutils.gui

//import gg.essential.api.EssentialAPId
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import net.minecraft.client.gui.GuiScreen

class OptionsGui(val parent: GuiScreen? = null) :
    WindowScreen(ElementaVersion.V2, newGuiScale = EssentialAPI.getGuiUtil().getGuiScale()) {

    }