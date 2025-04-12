package de.kazzutils.data.enumClass

import de.kazzutils.data.enumClass.ItemRarity.entries
import java.awt.Color

enum class ItemRarity(val baseColor: ChatColor, val color: Color = baseColor.color!!) {
    NONE(ChatColor.GRAY),
    COMMON(ChatColor.WHITE, Color(255, 255, 255)),
    UNCOMMON(ChatColor.GREEN, Color(77, 231, 77)),
    RARE(ChatColor.BLUE, Color(85, 85, 255)),
    EPIC(ChatColor.DARK_PURPLE, Color(151, 0, 151)),
    LEGENDARY(ChatColor.GOLD, Color(255, 170, 0)),
    MYTHIC(ChatColor.LIGHT_PURPLE, Color(255, 85, 255)),
    DIVINE(ChatColor.AQUA, Color(85, 255, 255)),
    SUPREME(ChatColor.DARK_RED, Color(170, 0, 0)),
    ULTIMATE(ChatColor.DARK_RED, Color(170, 0, 0)),
    SPECIAL(ChatColor.RED, Color(255, 85, 85)),
    VERY_SPECIAL(ChatColor.RED, Color(170, 0, 0));

    val rarityName by lazy {
        name.replace("_", " ").uppercase()
    }

    companion object {
        val RARITY_PATTERN by lazy {
            Regex("(?:§[\\da-f]§l§ka§r )?(?<rarity>${entries.joinToString("|") { "(?:${it.baseColor}§l)+(?:SHINY )?${it.rarityName}" }})")
        }

        fun byBaseColor(color: String) = entries.find { rarity -> rarity.baseColor.toString() == color }

    }

    val nextRarity: ItemRarity
        get() = entries[(ordinal + 1) % entries.size]
}