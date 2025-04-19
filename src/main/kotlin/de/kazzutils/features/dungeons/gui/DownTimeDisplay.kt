package de.kazzutils.features.dungeons.gui

import de.kazzutils.KazzUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.RenderUtils
import de.kazzutils.utils.randomutils.ChatUtils
import de.kazzutils.utils.randomutils.ChatUtils.noColorCodes
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.regex.Pattern

object DownTimeObject{
    var dtNames : MutableList<String> = mutableListOf()


    fun addUser(name : String){
        dtNames.add(name)
    }

    fun removeUser(name : String){
        dtNames.remove(name)
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type.toInt() == 2) return
        if (!KazzUtils.config.partyCommands) return
        val options = arrayOf("!",".","?",",","-")
        val message = event.message.unformattedText
        val messageList = ChatUtils.parseChatMessage(message)

        val listOfReady = mutableListOf("r","ready","done")

        listOfReady.forEach{if(messageList["content"]!!.contains(it)){
            removeUser(messageList["name"]!!)
        } }

    }

    class DownTimeDisplay : GuiElement("DownTime Display",x=200,y=80){
        override fun render() {
            if(dtNames.isEmpty()) return
            var displayText = """
                §aDowntime:
                """.trimIndent()

            dtNames.forEach{displayText += it + "\n"}
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override fun demoRender() {
            var displayText = """
                §aDowntime:
                """.trimIndent()
            dtNames.forEach{displayText += it + "\n"}
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * 7
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("§aDowntime Users:")

        override val toggled: Boolean
            get() = KazzUtils.config.downtime

        init {
            KazzUtils.guiManager.registerElement(this)
        }

    }
}

