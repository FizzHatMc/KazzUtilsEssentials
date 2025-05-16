package de.kazzutils.utils

import de.kazzutils.KazzUtils.Companion.mc
import net.minecraft.entity.Entity
import net.minecraft.util.BlockPos
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.RenderWorldLastEvent
import java.awt.Color
import kotlin.collections.forEach

object SimpleRender {

    private fun getblockPos(pos: BlockPos, event: RenderWorldLastEvent): BlockPos {
        val viewer: Entity = mc.renderViewEntity

        val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * event.partialTicks
        val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * event.partialTicks
        val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * event.partialTicks

        val x = pos.x - viewerX
        val y = pos.y - viewerY
        val z = pos.z - viewerZ

        return BlockPos(x, y, z)

    }

    //TODO: Add Beacon and Text

    /**
     * Highlights a [blockPos] in Game.
     * @param[blockPos] Highlight this Block
     * @param[color] Choose a Color
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlock(blockPos: BlockPos, color: Color, event: RenderWorldLastEvent) {
        RenderUtils.drawCustomBox(blockPos.x.toDouble(),1.0,blockPos.y.toDouble()+1.0,1.0,blockPos.z.toDouble(),1.0,color,3f, true)
    }



    /**
     * Highlights a [blockPos] and Renders a Beacon in Game.
     * @param[blockPos] Highlight this Block
     * @param[color] Choose a Color
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlockWithBeacon(blockPos: BlockPos, color: Color, event: RenderWorldLastEvent) {
        val pos = getblockPos(blockPos, event)

        highlightBlock(blockPos, color, event)
        RenderUtils.renderBeaconBeam(pos.x.toDouble(),pos.y.toDouble(),pos.z.toDouble(),color.rgb,1.0f,event.partialTicks)
    }

    fun renderLine(pos1: BlockPos, pos2: BlockPos, color: Color, event: RenderWorldLastEvent) {
        RenderUtils.drawLine(
            Vec3(pos1.x.toDouble() + 0.5, pos1.y.toDouble() + 1.5, pos1.z.toDouble() + 0.5),
            Vec3(pos2.x.toDouble() + 0.5, pos2.y.toDouble() + 1.5, pos2.z.toDouble() + 0.5),
            color,
            event.partialTicks
        )
    }

    /**
     * Highlights a [blockPos] and Renders a Tag in Game.
     * @param[blockPos] Highlight this Block
     * @param[color] Choose a Color
     * @param[text] What Tag/Text to display
     * @param[textScale] The Size of said Tag/Text
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlockWithTag(blockPos: BlockPos, color: Color, text: String, textScale : Float, event: RenderWorldLastEvent) {
        highlightBlock(blockPos, color, event)
        RenderUtils.renderWaypointText(text,blockPos, event.partialTicks,textScale)
    }


    /**
     * Highlights a list of [blockPos] in Game.
     * @param[blockPos] List of BlockPos
     * @param[color] Choose a Color
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlockList(blockPos: List<BlockPos>, color: Color, event: RenderWorldLastEvent) {
        blockPos.forEach { block -> highlightBlock(block,color,event) }
    }

    /**
     * Highlights a list of [BlockPos] in Game.
     * @param[blocksColors] Map of <BlockPos, Color>, Each Block needs a Color to be rendered in
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlockListColors(blocksColors: Map<BlockPos, Color>, event: RenderWorldLastEvent) {
        blocksColors.forEach { block, color -> highlightBlock(block,color,event) }
    }

    /**
     * Highlights a list of [BlockPos] and Renders a Beacon in Game.
     * @param[blockPos] List of BlockPos
     * @param[color] Choose a Color
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlockListWithBeacon(blockPos: List<BlockPos>, color: Color, event: RenderWorldLastEvent) {
        blockPos.forEach { block -> highlightBlockWithBeacon(block,color,event) }
    }


    /**
     * Highlights a list of [BlockPos] and Renders a Tag in Game.
     * @param[blockAndNames] Map of <BlockPos, String>, Each Block needs a String that will be Rendered
     * @param[color] Choose a Color
     * @param[scale] The Size of said Tag/Text
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlockListWithTagList(blockAndNames : Map<BlockPos,String>, color: Color, event: RenderWorldLastEvent, scale: Float) {
        blockAndNames.forEach { block, string ->  highlightBlockWithTag(block,color,string,scale,event) }
    }


    fun renderLinesList(linePos: Map<BlockPos, BlockPos>, color: Color,event: RenderWorldLastEvent) {
        linePos.forEach { pos1,pos2 -> renderLine(pos1,pos2,color,event) }
    }








}