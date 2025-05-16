package de.kazzutils.utils

import de.kazzutils.KazzUtils.Companion.mc
import net.minecraft.entity.Entity
import net.minecraft.util.BlockPos
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

    /**
     * Highlights a [blockPos] in Game.
     * @param[blockPos] Highlight this Block
     * @param[color] Choose a Color
     * @param[event] RenderWorldLastEven > for partialTicks
     * @return N/A.
     */
    fun highlightBlock(blockPos: BlockPos, color: Color, event: RenderWorldLastEvent) {
       val pos = getblockPos(blockPos, event)

        RenderUtils.drawCustomBox(pos.x.toDouble(),1.0,pos.y+1.0,2.0,pos.z.toDouble(),1.0,color,3f, true)
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
        RenderUtils.drawCustomBox(pos.x.toDouble(),1.0,pos.y+1.0,2.0,pos.z.toDouble(),1.0,color,3f, true)
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




    


}