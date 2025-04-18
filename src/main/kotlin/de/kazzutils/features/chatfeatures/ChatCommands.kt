package de.kazzutils.features.chatStuff

import de.kazzutils.KazzUtils
import de.kazzutils.features.dungeons.gui.DownTimeObject
import de.kazzutils.utils.randomutils.ChatUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*
import java.util.regex.Pattern

class ChatCommands {

    //  [343] ⚔ [MVP++] RealKazz: test
    //  [343] ⚔ [MVP+] RealKazz: test
    //  [343] ⚔ [VIP+] RealKazz: test
    //  [343] ⚔ [VIP] RealKazz: test
    //  [343] ⚔ RealKazz: test
    //   ⚔ [MVP++] RealKazz: test
    //   ⚔ [MVP+] RealKazz: test
    //   ⚔ [VIP+] RealKazz: test
    //   ⚔ [VIP] RealKazz: test
    //   ⚔ RealKazz: test
    //  [343] [MVP++] RealKazz: test
    //  [343] [MVP+] RealKazz: test
    //  [343] [VIP+] RealKazz: test
    //  [343] [VIP] RealKazz: test
    //  [343] RealKazz: test
    //  [MVP++] RealKazz: test
    //  [MVP+] RealKazz: test
    //  [VIP+] RealKazz: test
    //  [VIP] RealKazz: test
    //  RealKazz: test

    /*

    HP DEFENSE SPEED OVERFLOW MANA ????????????????

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        String UserInput = event.message.getUnformattedText(); //INCLUSIVE USER

        //String user = extractUsername(UserInput);
        ChatUtils.messageToChat(UserInput);


    }
 */
    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type.toInt() == 2) return
        if (!KazzUtils.config.partyCommands) return
        val options = arrayOf("!",".","?",",","-")
        val commandUse = options[KazzUtils.config.partyPrefix]
        val message = event.message.unformattedText

        val regex = "Party > (\\[.+])? ?(.+): \\$commandUse([^\\s]+)( ?: ([^\\s]+))?$"

        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(noColorCodes(message))


        if (matcher.matches()) {
            val sender = matcher.group(2)
            var command = matcher.group(3)
            command = command.lowercase(Locale.getDefault())
            if (command.startsWith("m")) {
                val floor = extractNumberFromCommand(command)
                if (floor != -1) {
                    ChatUtils.userMessage("/joindungeon MASTER_CATACOMBS_FLOOR_" + convertToText(floor))
                    return
                }
            }

            if (command.startsWith("f")) {
                val floor = extractNumberFromCommand(command)
                if (floor != -1) {
                    ChatUtils.userMessage("/joindungeon CATACOMBS_FLOOR_" + convertToText(floor))
                    return
                }
            }

            when (command) {
                "help" -> {
                    ChatUtils.userMessage("Chat Commands: ")
                    ChatUtils.userMessage("help, ptme, warp, inv / invite, f{floor}, m{floor} , allinv/invite, dt/downtime, ndt/r/done")
                }
                "ptme" -> ChatUtils.userMessage("/party transfer $sender")
                "warp" -> ChatUtils.userMessage("/party warp")
                "inv", "invite" -> ChatUtils.userMessage("/party invite ${matcher.group(4)}")
                "allinvite", "allinv" -> ChatUtils.userMessage("/party setting allinvite")
                "dt", "downtime" -> DownTimeObject.addUser(sender)
                "ndt", "r", "done" -> DownTimeObject.removeUser(sender)
            }
        }
    }


    fun noColorCodes(message: String): String {
        val regex = "§[0-9a-fklmnor]"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(message)
        val output = matcher.replaceAll("")
        return output
    }

    private fun extractNumberFromCommand(command: String): Int {
        return if (command.startsWith("m") || command.startsWith("f")) {
            try {
                command.substring(1).toInt()
            } catch (e: NumberFormatException) {
                -1
            }
        } else {
            -1
        }
    }

    private fun convertToText(number: Int): String {
        return when (number) {
            1 -> "one"
            2 -> "two"
            3 -> "three"
            4 -> "four"
            5 -> "five"
            6 -> "six"
            7 -> "seven"
            else -> "Invalid input" // Handle out-of-range numbers
        }
    }

}