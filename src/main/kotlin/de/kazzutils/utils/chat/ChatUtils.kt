package de.kazzutils.utils.chat

import de.kazzutils.KazzUtils
import de.kazzutils.utils.Utils.removeMinecraftColorCodes
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.text.DecimalFormat
import java.util.regex.Pattern

object ChatUtils {

    private val hpRegex = Regex("""(?<health>[0-9,.]+)/(?<maxHealth>[0-9,.]+)❤(?<wand>\\+(?<wandHeal>[0-9,.]+)[▆▅▄▃▂▁])?""")
    private val defenseRegex = Regex("""(\d+)❈ Defense""")
    private val manaUseRegex = Regex("""-(\d+) Mana""")
    private val manaRegex = Regex("""(?<num>[0-9,.]+)/(?<den>[0-9,.]+)✎(| Mana| (?<overflow>-?[0-9,.]+)ʬ)""")
    private val skillRegex = Regex("\\+(?<gained>[0-9,.]+) (?<skillName>[A-Za-z]+) (?<progress>\\((((?<current>[0-9.,kM]+)/(?<total>[0-9.,kM]+))|((?<percent>[0-9.,]+)%))\\))")
    private val messageRegex = Regex("""\s?\[(?:.*?)(\w+)\]:\s(.*)""")

    var health: String? = null
    var manaUse: String? = null
    var mana: String = ""
    var skill: String? = null
    var defense: String? = null

    fun parseChatMessage(message: String): Map<String, String> {
        val regex = Regex(""".*?(\b\w+):\s(.*)""")

        val matchResult = regex.find(message)
        return if (matchResult != null && matchResult.groupValues.size >= 3) {
            val name = matchResult.groupValues[1]
            val content = matchResult.groupValues[2]
            mapOf("name" to name, "content" to content)
        } else {
            mapOf("name" to "null", "content" to "null")
        }
    }





    fun getUserMessageFromUnformatedText(message: String): String {
        val regex = "(?<=: ).*"
        var returnMessage = ""
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(noColorCodes(message))

        if (matcher.find()) {
            returnMessage = matcher.group()
        }

        return returnMessage
    }

    fun messageToChat(message: String) {
        KazzUtils.Companion.mc.thePlayer.addChatMessage(ChatComponentText(message))
    }

    fun messageToChat(message: ChatComponentText) {
        KazzUtils.Companion.mc.thePlayer.addChatMessage(message)
    }

    fun messageToChatColored(message: String, color : String) {
        KazzUtils.Companion.mc.thePlayer.addChatMessage(ChatComponentText(getColorCode(color) + message))
    }

    fun userMessage(message: String){
        KazzUtils.Companion.mc.thePlayer.sendChatMessage(message)
    }

    fun error(message: String){
        KazzUtils.Companion.mc.thePlayer.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString() + message))
    }

    private fun getColorCode(colorName: String): String? {
        return when (colorName.lowercase()) {
            "black" -> "§0"
            "dark_blue" -> "§1"
            "dark_green" -> "§2"
            "dark_aqua" -> "§3"
            "dark_red" -> "§4"
            "dark_purple" -> "§5"
            "gold" -> "§6"
            "gray" -> "§7"
            "dark_gray" -> "§8"
            "blue" -> "§9"
            "green" -> "§a"
            "aqua" -> "§b"
            "red" -> "§c"
            "light_purple" -> "§d"
            "yellow" -> "§e"
            "white" -> "§f"
            else -> null // Return null if the color name is invalid
        }

    }

    fun addColorCodeReturnComponent(msg: String, color: String) : ChatComponentText {
        return ChatComponentText(getColorCode(color) + msg)
    }

    fun formatNumber(number: Number): String {
        val formatter = DecimalFormat("#,###.##") // Adjust pattern for decimal places
        return formatter.format(number)
    }

    fun noColorCodes(message: String): String {
        val regex = "§[0-9a-fklmnor]"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(message)
        val output = matcher.replaceAll("")
        return output
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent){
        if (event.type.toInt() != 2) return
        event.isCanceled = true
        val text = event.message.unformattedTextForChat.removeMinecraftColorCodes()

        health = hpRegex.find(text)?.groupValues?.let { "${it[1]} ${it[2]}" }.toString()
        defense = defenseRegex.find(text)?.groupValues?.get(1)
        manaUse = manaUseRegex.find(text)?.groupValues?.get(1)
        mana = manaRegex.find(text)?.groupValues?.let { Pair(it[1], it[2]) }.toString()
        skill =  skillRegex.find(text)?.groupValues?.let { "+${it[1]} ${it[2]} ${it[3]}" }




    }




}