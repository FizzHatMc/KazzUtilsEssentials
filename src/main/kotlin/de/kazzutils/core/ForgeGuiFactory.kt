package de.kazzutils.core

import de.kazzutils.gui.OptionsGui
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.IModGuiFactory.RuntimeOptionGuiHandler

class ForgeGuiFactory : IModGuiFactory {
    override fun initialize(minecraft: Minecraft) {
    }

    override fun mainConfigGuiClass(): Class<out GuiScreen> = OptionsGui::class.java

    override fun runtimeGuiCategories(): Set<IModGuiFactory.RuntimeOptionCategoryElement> = emptySet()

    override fun getHandlerFor(runtimeOptionCategoryElement: IModGuiFactory.RuntimeOptionCategoryElement): RuntimeOptionGuiHandler =
        object : RuntimeOptionGuiHandler {
            override fun addWidgets(list: MutableList<Gui>?, i: Int, j: Int, k: Int, l: Int) {}

            override fun paint(i: Int, j: Int, k: Int, l: Int) {}

            override fun actionCallback(i: Int) {}

            override fun close() {}
        }

}