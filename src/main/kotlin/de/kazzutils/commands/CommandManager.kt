package de.kazzutils.commands

import com.google.gson.Gson
import de.kazzutils.KazzUtils.Companion.displayScreen
import de.kazzutils.commands.SimpleCommand.ProcessCommandRunnable
import de.kazzutils.data.enumClass.PetRarity
import de.kazzutils.gui.KeyShortcutsGui
import de.kazzutils.gui.OptionsGui
import de.kazzutils.gui.editing.ElementaEditingGui
import de.kazzutils.utils.randomutils.ChatUtils
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.ClientCommandHandler

class CommandManager {

    init {

        registerCommandWithAliases(
            name = "KazzUtils",
            aliases = listOf("kazz","kazzutils","kaz","hs"),
            function = { args ->
                val arg = args.firstOrNull()
                if (arg != null) {
                    if(arg.contains("gui",true)){
                        displayScreen = ElementaEditingGui()
                        return@registerCommandWithAliases
                    }else if(arg.contains("hotkeys",true)){
                        displayScreen = KeyShortcutsGui()
                        return@registerCommandWithAliases
                    }else if(arg.contains("help",true)){
                        printHelp()
                    }else{
                        ChatUtils.messageToChat("Error: Invalid usage: /kazz <gui|hotkeys|help>")
                    }
                }
                if(args.isEmpty()) displayScreen = OptionsGui()
            },
            autoComplete = { args ->
                when (args.size) {
                    1 -> listOf("gui","hotkeys","help")
                    else -> emptyList()
                }
            }
        )
        registerCommand0(
            name = "calcpet",
            function = { args ->
                if (args.isEmpty()) {
                    ChatUtils.messageToChat(ChatComponentText("Usage: /calcpet <rarity> <endLvl> or /calcpet <rarity> <startLvl> <endLvl>"))
                    return@registerCommand0
                }

                val rarityInput = args[0]
                val petRarity = PetRarity.fromInput(rarityInput)

                if (petRarity == null) {
                    ChatUtils.messageToChat(ChatComponentText("Invalid rarity: $rarityInput"))
                    return@registerCommand0
                }

                try {
                    val xp = when (args.size) {
                        2 -> {
                            val endLvl = args[1].toInt()
                            petRarity.getXpBetweenLevels(0, endLvl)
                        }
                        3 -> {
                            val startLvl = args[1].toInt()
                            val endLvl = args[2].toInt()
                            petRarity.getXpBetweenLevels(startLvl, endLvl)
                        }
                        else -> {
                            ChatUtils.messageToChat(ChatComponentText("Invalid usage."))
                            return@registerCommand0
                        }
                    }

                    val label = ChatUtils.addColorCodeReturnComponent("Pet XP Required for ${petRarity.name.lowercase().replaceFirstChar { it.uppercaseChar() }}: ", "aqua")
                    val value = ChatUtils.addColorCodeReturnComponent(ChatUtils.formatNumber(xp), "gold")
                    ChatUtils.messageToChat(ChatComponentText(label.unformattedText + value.unformattedText))

                } catch (e: NumberFormatException) {
                    ChatUtils.messageToChat(ChatComponentText("Level inputs must be numbers."))
                }
            },
            autoComplete = { args ->
                when (args.size) {
                    1 -> PetRarity.suggestions()
                    else -> emptyList()
                }
            }
        )

        registerCommand("testApi"){
            //val apiAnswer = HypixelAPI.get("skyblock/profiles", mapOf("uuid" to "85de5df5-3960-428b-bca4-7dce9766ff8f"))

            val playerJson = HypixelAPI.getAsync("skyblock/profiles", mapOf("uuid" to "85de5df5-3960-428b-bca4-7dce9766ff8f")) { result ->
                if (result.isSuccess) {
                    val playerJson = result.getOrNull() // Safely get the JsonObject
                    val test = playerJson!!.getAsJsonArray("profiles")[0].asJsonObject.get("profile_id").asString
                    ChatUtils.messageToChat("Test -> $test")
                } else {
                    val error = result.exceptionOrNull()
                    if (error != null) {
                        println("Error fetching player data: ${error.message}") // Log the error
                        // Handle the error (e.g., show an error message to the player).  Make sure this is done on the main thread if it updates the UI.
                    }
                }
            }


            //val firesaleJsonSimulated = Gson().fromJson(jsonString, JsonObject::class.java)


            // Get the "sales" array.
            //val salesArray: JsonArray = firesaleJsonSimulated.getAsJsonArray("sales")





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

    private fun printHelp() {
        val helpMessage = """
        §b§lKazzUtils
        §6Current commands:
        
        §a/protect <clearall> §7-> §fProtects the item the player holds.
        
        §a/KazzUtils <hotkeys, gui, help> §7-> §
        fHotkeys: Opens hotkey menu. 
        §f-> Gui: Opens Gui Editor. 
        §f-> Help: Prints this message.
        §7Aliases: §e"kazz", "kazzutils", "kaz", "hs"
        
        §a/calcpet <rarity> <endLvl> §7-> §fCalculates the XP needed for the given rarity and end level.
        §a/calcpet <rarity> <startLvl> <endLvl> §7-> §fCalculates the XP needed for the given rarity, start level, and end level.
        """.trimIndent()

        // Send the message to the player's chat
        ChatUtils.messageToChat(ChatComponentText(helpMessage))
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

    private fun registerCommandWithAliases(
        name: String,
        aliases: List<String> = listOf(),
        function: (Array<String>) -> Unit,
        autoComplete: ((Array<String>) -> List<String>) = { listOf() } // Default to no autocomplete
    ) {
        // Create the main command
        val command = SimpleCommand(name, createCommand(function), object : SimpleCommand.TabCompleteRunnable {
            override fun tabComplete(sender: ICommandSender?, args: Array<String>?, pos: BlockPos?): List<String> {
                return autoComplete(args ?: emptyArray())
            }
        })

        // Register the main command
        ClientCommandHandler.instance.registerCommand(command)

        // Register the aliases as commands
        for (alias in aliases) {
            ClientCommandHandler.instance.registerCommand(SimpleCommand(alias, createCommand(function)))
        }
    }


    private fun createCommand(function: (Array<String>) -> Unit) = object : ProcessCommandRunnable() {
        override fun processCommand(sender: ICommandSender?, args: Array<String>?) {
            if (args != null) function(args.asList().toTypedArray())
        }
    }
}