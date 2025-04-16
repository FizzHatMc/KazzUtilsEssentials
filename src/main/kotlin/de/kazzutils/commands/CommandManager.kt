package de.kazzutils.commands


import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.displayScreen
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.commands.SimpleCommand.ProcessCommandRunnable
import de.kazzutils.core.Config

import de.kazzutils.gui.KeyShortcutsGui
import de.kazzutils.gui.OptionsGui
import de.kazzutils.gui.editing.ElementaEditingGui
import de.kazzutils.utils.Utils.openGUI
import de.kazzutils.utils.chat.RequiredPetXp
import de.kazzutils.utils.randomutils.ChatUtils
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.ClientCommandHandler

class CommandManager {

    init {

        registerCommand("Test") {ChatUtils.messageToChat("Test")}

        registerCommand("kazzutils") {
            ChatUtils.messageToChat("Kazzutils command")
            displayScreen = OptionsGui()
            return@registerCommand

        }
        registerCommand("kazz"){ args ->
            val arg = args.firstOrNull()
            if (arg != null) {
                if(arg.contains("gui",false)){
                    KazzUtils.displayScreen = ElementaEditingGui()
                    return@registerCommand
                }else if(arg.contains("hotkeys",false)){
                    KazzUtils.displayScreen = KeyShortcutsGui()
                    return@registerCommand
                }else if(arg.contains("help",false)){
                    ChatUtils.messageToChat("Possible Options: gui, hotkeys")
                }else{
                    ChatUtils.messageToChat("Error: Invalid usage: /kazz <gui|hotkeys>")
                }


            }
            if(args.isEmpty()) displayScreen = OptionsGui()
        }

        //TODO: Learn how to add Forced Args, example: /calcpet rarity (options being leg, epic, rare, uncommon, common) endlvl
        registerCommand("calcpet"){ args ->
            val rarity = args.firstOrNull().toString()
            var rar = ""

            when (rarity) {
                "leg" -> rar = "Legendary"
                "com" -> rar = "Common"
                "uncom" -> rar = "Uncommon"
                else -> rar = rarity.replace(rarity[0], rarity[0].uppercaseChar())
            }

            if(args.size == 3) {
                val startLvl = args[1].toInt()
                val endLvl = args[2].toInt()

                val reqXp =  RequiredPetXp.getRequiredPetLvl(rar, startLvl, endLvl)

                val test = ChatUtils.addColorCodeReturnComponent("Pet XP Required for $rar: ", "aqua")
                val test2 = ChatUtils.addColorCodeReturnComponent(ChatUtils.formatNumber(reqXp), "gold")
                val test3 = ChatComponentText(test.unformattedText + test2.unformattedText)



                ChatUtils.messageToChat(test3)
            }else if(args.size == 2){
                val endLvl = args[1].toInt()
                val reqXp = RequiredPetXp.getRequiredPetLvl(rar, endLvl)

                val test = ChatUtils.addColorCodeReturnComponent("Pet XP Required for $rar: ", "aqua")
                val test2 = ChatUtils.addColorCodeReturnComponent(ChatUtils.formatNumber(reqXp), "gold")
                val test3 = ChatComponentText(test.unformattedText + test2.unformattedText)



                ChatUtils.messageToChat(test3)

            }


            //if(args.isEmpty())KazzUtils.configManager.openConfigGui()
        }
        /*
        registerCommand("formatmessage") { args ->
            val colorName = args.firstOrNull()
            if (colorName == null) {
                ChatUtils.messageToChat("Error: Invalid usage: /formatmessage <color> <message>")
            }
            val colorCode = when (colorName) {
                "red" -> "§c"
                "blue" -> "§9"
                "green" -> "§a"
                "yellow" -> "§e"
                "pink" -> "§d"
                else -> {
                    ChatUtils.messageToChat("Error: Invalid color '$colorName'!")
                    ChatUtils.messageToChat("Possible Colors are red, blue, green, yellow and pink")
                    return@registerCommand
                }
            }

            val rest = args.drop(1)
            if (rest.isEmpty()) {
                ChatUtils.messageToChat("Error: Message can not be empty!")
                return@registerCommand
            }

            ChatUtils.messageToChat(colorCode + rest.joinToString(" "))
        }*/
    }



    private fun registerCommand(name: String, function: (Array<String>) -> Unit) {
        ClientCommandHandler.instance.registerCommand(SimpleCommand(name, createCommand(function)))
    }

    private fun registerCommand0(
        name: String,
        function: (Array<String>) -> Unit,
        autoComplete: ((Array<String>) -> List<String>) = { listOf() }
    ) {
        val command = SimpleCommand(
            name,
            createCommand(function),
            object : SimpleCommand.TabCompleteRunnable {
                override fun tabComplete(sender: ICommandSender?, args: Array<String>?, pos: BlockPos?): List<String> {
                    return autoComplete(args ?: emptyArray())
                }
            }
        )
        ClientCommandHandler.instance.registerCommand(command)
    }

    private fun createCommand(function: (Array<String>) -> Unit) = object : ProcessCommandRunnable() {
        override fun processCommand(sender: ICommandSender?, args: Array<String>?) {
            if (args != null) function(args.asList().toTypedArray())
        }
    }
}