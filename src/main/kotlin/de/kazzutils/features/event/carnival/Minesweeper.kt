package de.kazzutils.features.event.carnival

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.MouseEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.opengl.GL11


class Minesweeper {

    // There is X bomb hidden nearby.   // 0-X
    //Here's yer shovel, then.


    // -112 72 -11 | -106 72 -11
    // -112 72 -5  | -105 72 -5

    private val bombCounts = HashMap<BlockPos, Int>()
    private val possibleBombs = HashSet<BlockPos>()
    private var lastClickedBlock: BlockPos? = null
    private var fruitDiggingActive = false

    @SubscribeEvent
    fun onMouseEvent(event: MouseEvent) {
        if(!KazzUtils.config.minesweeper) return
        if (event.button == 0 && event.buttonstate) {
            val mop = mc.objectMouseOver
            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                val clickedPos = mop.blockPos
                val world = mc.theWorld
                if (world != null && world.getBlockState(clickedPos).block === Blocks.sand) {
                    lastClickedBlock = clickedPos
                }
            }
        }
    }

    @SubscribeEvent
    fun onClientChatReceived(event: ClientChatReceivedEvent) {
        if(!KazzUtils.config.minesweeper) return
        val message = event.message.unformattedText
        if (message.contains("Fruit Digging") && !fruitDiggingActive) {
            fruitDiggingActive = true
            bombCounts.clear() // Clear any old data
            possibleBombs.clear()
        }

        if (message.startsWith("MINES! There ")) {
            try {
                val number = message.replace(Regex("[^0-9]"), "").toInt()
                lastClickedBlock?.let {
                    bombCounts[it] = number
                    updateHighlights(mc.theWorld)
                    lastClickedBlock = null
                }
            } catch (e: NumberFormatException) {
                System.err.println("Error parsing bomb count: ${e.message}")
            }
        }

        if (message.contains("Fruits Collected") && fruitDiggingActive || message.contains("You earned") && fruitDiggingActive) {
            fruitDiggingActive = false
            bombCounts.clear()
            possibleBombs.clear()
        }
    }

    private fun updateHighlights(world: World?) {
        if (world == null) return

        possibleBombs.clear()

        for ((minedPos, bombCount) in bombCounts) {
            if (bombCount > 0) {
                for (x in -1..1) {
                    for (z in -1..1) {
                        if (x == 0 && z == 0) continue
                        val neighborPos = minedPos.add(x, 0, z)
                        if (isSandBlock(world, neighborPos)) { // Use the isSandBlock function
                            possibleBombs.add(neighborPos)
                        }
                    }
                }
            }
        }

        // Corrected removal logic
        val bombsToRemove = HashSet<BlockPos>()
        for (possibleBomb in possibleBombs) {
            var possibleCount = 0
            for ((minedPos, bombCount) in bombCounts) {
                for (x in -1..1) {
                    for (z in -1..1) {
                        if (x == 0 && z == 0) continue
                        val neighborPos = minedPos.add(x, 0, z)
                        if (neighborPos == possibleBomb && bombCount == 0) {
                            possibleCount++
                        }
                    }
                }
            }
            if (possibleCount > 0) {
                bombsToRemove.add(possibleBomb)
            }
        }
        possibleBombs.removeAll(bombsToRemove)
    }

    private fun isSandBlock(world: World, pos: BlockPos): Boolean {
        return world.getBlockState(pos).block === Blocks.sand
    }

    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if(!KazzUtils.config.minesweeper) return
        if (possibleBombs.isNotEmpty() && fruitDiggingActive) {
            GlStateManager.pushMatrix()
            GlStateManager.enableBlend()
            GlStateManager.disableDepth()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            GlStateManager.disableTexture2D()
            GlStateManager.disableLighting()
            GL11.glLineWidth(2.0f)

            val tessellator = Tessellator.getInstance()
            val worldRenderer = tessellator.worldRenderer

            worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR) // Correct way to begin

            for (pos in possibleBombs) {
                val x = pos.x - mc.renderManager.viewerPosX
                val y = pos.y - mc.renderManager.viewerPosY
                val z = pos.z - mc.renderManager.viewerPosZ

                // Draw cube outline (using worldRenderer.pos and worldRenderer.color)
                val red = 1f
                val green = 0f
                val blue = 0f
                val alpha = 0.5f

                // Bottom face
                worldRenderer.pos(x, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y, z).color(red, green, blue, alpha).endVertex()

                // Top face
                worldRenderer.pos(x, y + 1.0, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y + 1.0, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                // Sides
                worldRenderer.pos(x, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()
            }

            tessellator.draw() // Call draw() after adding all vertices

            GlStateManager.enableTexture2D()
            GlStateManager.enableDepth()
            GlStateManager.disableBlend()
            GlStateManager.popMatrix()
        }
    }
}