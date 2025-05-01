package de.kazzutils.features.mining.crystalhollows

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.RenderUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ModToolsWarner {
    /*
    You found Scavenged Emerald Hammer with your Metal Detector!
    You found Scavenged Diamond Axe with your Metal Detector!
    You found Scavenged Lapis Sword with your Metal Detector!
    You found Scavenged Golden Hammer with your Metal Detector!
     */

    var amountOfTools = 0
    private var tools = arrayOf("Scavenged Emerald Hammer","Scavenged Diamond Axe","Scavenged Lapis Sword","Scavenged Golden Hammer")
    private var foundTools = emptyArray<String>()

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type.toInt() != 2 || !KazzUtils.config.modTools) return
        var msg = event.message.unformattedTextForChat


        tools.forEach { tool -> if(msg.contains("found $tool")) foundTools += tool}
        amountOfTools = foundTools.size
        if(foundTools.size == 4){
            mc.ingameGUI.displayTitle("Found 4/4 Tools","",0,2,0)
            foundTools = emptyArray<String>()
        }
    }


    class ModToolsDisplay: GuiElement("Mines of Divan Tools",x=400,y=80){
        override fun render() {
            var displayText = """
                §4 Tools: ${amountOfTools}/4:
                """.trimIndent()
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override fun demoRender() {
            var displayText = """
                §4 Tools: X/4:
                """.trimIndent()
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * 7
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("§4 Tools: X/4")

        override val toggled: Boolean
            get() = KazzUtils.config.downtime

        init {
            KazzUtils.guiManager.registerElement(this)
        }

    }
}