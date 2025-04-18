package de.kazzutils.features.chatfeatures

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.randomutils.ChatUtils
import de.kazzutils.utils.randomutils.ChatUtils.getUserMessageFromUnformatedText
import de.kazzutils.utils.randomutils.ChatUtils.noColorCodes
import de.kazzutils.utils.randomutils.ChatUtils.parseChatMessage
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.regex.Pattern

//TODO: Fix Diffrent Chats being Buggy, "/gc o/" sends the message to gc but doesnt display in users chat
class ChatEmotes {
    val replacements = mapOf(
        "<3" to "❤",
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

    var replaced = false

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type.toInt() == 2) return
        if (!KazzUtils.config.chatEmotes) return
        val message = event.message.unformattedText
        val test = parseChatMessage(message)

        val name = test["name"] ?: return
        val content = test["content"] ?: return

        if(name.equals(mc.thePlayer.displayName)){
            ChatUtils.messageToChat("Message sent by User")
            return
        }

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
    }




}