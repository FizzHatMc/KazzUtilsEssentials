package de.kazzutils.features.chatfeatures

import de.kazzutils.handler.ChatHandler
import net.minecraft.client.gui.GuiChat
import net.minecraft.client.gui.GuiTextField
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.input.Keyboard

class ChatEmotes {
    private val replacements = mapOf(
        "<3" to "❤ ",
        "o/" to "( ﾟ◡ﾟ)/",
        ":star:" to "✮",
        ":yes:" to "✔",
        ":no:" to "✖",
        ":java:" to "☕",
        ":arrow:" to "➜",
        ":shrug:" to "¯\\_(ツ)_/¯",
        ":tableflip:" to "(╯°□°）╯︵ ┻━┻",
        ":totem:" to "☉_☉",
        ":typing:" to "✎...",
        ":maths:" to "√(π+x)=L",
        ":snail:" to "@'-'",
        ":thinking:" to "(0.o?)",
        ":gimme:" to "༼つ◕_◕༽つ",
        ":wizard:" to "('-')⊃━☆ﾟ.*･｡ﾟ",
        ":pvp:" to "⚔",
        ":peace:" to "✌",
        ":puffer:" to "<('O')>",
        "h/" to "ヽ(^◇^*)/",
        ":sloth:" to "(・⊝・)",
        ":dog:" to "(ᵔᴥᵔ)",
        ":dj:" to "ヽ(⌐■_■)ノ♬",
        ":yey:" to "ヽ (◕◡◕) ﾉ",
        ":snow:" to "☃",
        ":dab:" to "<o/",
        ":cat:" to "= ＾● ⋏ ●＾ =",
        ":cute:" to "(✿◠‿◠)",
        ":skull:" to "☠"
    )


    @SubscribeEvent
    fun onGuiInput(event: KeyboardInputEvent) {
        if (event.gui is GuiChat && Keyboard.getEventKeyState() && event.isCancelable)
            if (runReplacement(event.gui as GuiChat)){
                event.isCanceled = true
            }
    }

    private fun runReplacement(chat: GuiChat) : Boolean{
        try {
            val inputField : GuiTextField = chat.inputField


            val msg = inputField.text
            val cursor: Int = inputField.cursorPosition
            val word = ChatHandler.getWord(msg,cursor)
            if(replacements.containsKey(word)){
                replacements[word]?.let { ChatHandler.replaceWord(inputField, it) }
                return true

            }
        }catch (e:Exception){
            println ("Caught exception. ${e.message}")
        }

        return false
    }

}


/*
@SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type.toInt() == 2) return
        if (!KazzUtils.config.chatEmotes) return
        val message = event.message.unformattedText
        val test = parseChatMessage(message)


        val name = test.getValue("name")

        val content = test.getValue("content")


        if(name == mc.thePlayer.name){
            ChatUtils.messageToChat("Name: $name -> $content")

            val filter = listOf("/pc", "/ac", "/gc", "/msg", "/w", "/r")
            if (content.startsWith("/") && filter.none { content.startsWith(it) }) return

            replaced = false
            val words = content.split(" ").toMutableList()

            for (i in words.indices) {
                if (replacements.containsKey(words[i])) {
                    replaced = true
                    words[i] = replacements[words[i]].orEmpty()
                }
            }

            if (!replaced) return
            val newMessage = words.joinToString(" ")
            event.isCanceled = true
            ChatUtils.userMessage(newMessage)
            return
        }


    }
 */