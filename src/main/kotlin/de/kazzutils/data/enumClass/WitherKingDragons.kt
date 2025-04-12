package de.kazzutils.data.enumClass

import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumChatFormatting
import java.awt.Color
import java.util.*

enum class WitherKingDragons(
    val textColor: String, // Getter and setter for blockPos
    var blockPos: BlockPos,
    color: Color,
    chatColor: ChatColor,
    lineCoords: Array<BlockPos>,
    relic: BlockPos,
    enumChatFormatting: EnumChatFormatting,
    cauldron: BlockPos
) {
    POWER(
        "Red",
        BlockPos(27, 14, 59),
        Color.RED,
        ChatColor.RED,
        arrayOf(BlockPos(27, 14, 58), BlockPos(40, 20, 45)),
        BlockPos(20, 6, 59),
        EnumChatFormatting.RED,
        BlockPos(51.5, 6.5, 42.5)
    ),
    APEX(
        "Green",
        BlockPos(27, 14, 94),
        Color.GREEN,
        ChatColor.GREEN,
        arrayOf(BlockPos(27, 15, 94), BlockPos(24, 19, 82)),
        BlockPos(20, 6, 94),
        EnumChatFormatting.GREEN,
        BlockPos(49.5, 6.5, 44.5)
    ),
    SOUL(
        "Purple",
        BlockPos(56, 14, 125),
        Color.MAGENTA,
        ChatColor.DARK_PURPLE,
        arrayOf(BlockPos(56, 14, 125), BlockPos(56, 14, 125)),
        BlockPos(56, 8, 132),
        EnumChatFormatting.LIGHT_PURPLE,
        BlockPos(54.5, 6.5, 41.5)
    ),
    ICE(
        "Blue",
        BlockPos(84, 14, 94),
        Color.CYAN,
        ChatColor.AQUA,
        arrayOf(BlockPos(84, 14, 94), BlockPos(80, 18, 104)),
        BlockPos(91, 6, 94),
        EnumChatFormatting.BLUE,
        BlockPos(59.5, 6.5, 44.5)
    ),
    FLAME(
        "Orange",
        BlockPos(85, 14, 56),
        Color.ORANGE.brighter(),
        ChatColor.GOLD,
        arrayOf(BlockPos(84, 14, 55), BlockPos(77, 18, 59), BlockPos(81, 19, 68)),
        BlockPos(92, 6, 56),
        EnumChatFormatting.GOLD,
        BlockPos(57.5, 6.5, 42.5)
    );


    // Getter and setter for color
    val color: Color
    private val chatColor: ChatColor

    // Getter and setter for detectBlock
    val detectBlock: BlockPos

    // Getter and setter for isDestroyed
    var isDestroyed: Boolean

    // Getter and setter for itemName
    val itemName: String

    // Getter and setter for itemId
    val itemId: String

    // Getter and setter for bb
    // private final ResourceLocation texture;
    val bb: AxisAlignedBB

    // Getter and setter for particleLocation
    val particleLocation: BlockPos
    val lineCoords: Array<BlockPos>
    val relic: BlockPos
    val dragonText: BlockPos
    val enumChatFormatting: EnumChatFormatting
    var picked: Boolean = false
    val cauldron: BlockPos

    companion object {
        const val particleYConstant: Double = 19.0
        private const val a : Double = 13.5
    }

    init {

        this.blockPos = blockPos
        this.color = color
        this.chatColor = chatColor
        this.detectBlock = blockPos.up(13)
        this.isDestroyed = false
        this.itemName = "§cCorrupted " + this.textColor + " Relic"
        this.itemId = textColor.uppercase(Locale.getDefault()) + "_KING_RELIC"
        // this.texture = new ResourceLocation("skytils", "textures/dungeons/m7/dragon_" + this.name().toLowerCase() + ".png");
        this.bb = AxisAlignedBB(
            blockPos.x - 13.5,
            blockPos.y - 8.0,
            blockPos.z - 13.5,
            blockPos.x + 13.5,
            blockPos.y + 13.5 + 2,
            blockPos.z + 13.5
        )
        this.particleLocation = blockPos.up(5)
        this.lineCoords = lineCoords
        this.relic = relic
        this.dragonText = relic.up(4)
        this.enumChatFormatting = enumChatFormatting
        this.cauldron = cauldron
    }



}