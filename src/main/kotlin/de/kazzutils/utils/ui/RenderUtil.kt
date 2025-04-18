package de.kazzutils.utils.ui

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.colors.CommonColors
import de.kazzutils.utils.graphics.ScreenRenderer
import de.kazzutils.utils.graphics.SmartFontRenderer
import gg.essential.elementa.font.DefaultFonts
import gg.essential.elementa.font.ElementaFonts
import gg.essential.elementa.utils.withAlpha
import gg.essential.universal.ChatColor
import gg.essential.universal.UGraphics
import gg.essential.universal.UMatrixStack
import gg.essential.universal.UResolution
import net.minecraft.block.Block
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.util.*
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

object RenderUtil {
    private val beaconBeam = ResourceLocation("textures/entity/beacon_beam.png")


    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     * @author Moulberry
     * @author Mojang
     */
    fun renderBeaconBeam(x: Double, y: Double, z: Double, rgb: Int, alphaMultiplier: Float, partialTicks: Float) {
        val height = 300
        val bottomOffset = 0
        val topOffset = bottomOffset + height
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        mc.textureManager.bindTexture(beaconBeam)
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0f)
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0f)
        GlStateManager.disableLighting()
        GlStateManager.enableCull()
        GlStateManager.enableTexture2D()
        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0)
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        val time = mc.theWorld.totalWorldTime + partialTicks.toDouble()
        val d1 = MathHelper.func_181162_h(
            -time * 0.2 - MathHelper.floor_double(-time * 0.1)
                .toDouble()
        )
        val r = (rgb shr 16 and 0xFF) / 255f
        val g = (rgb shr 8 and 0xFF) / 255f
        val b = (rgb and 0xFF) / 255f
        val d2 = time * 0.025 * -1.5
        val d4 = 0.5 + cos(d2 + 2.356194490192345) * 0.2
        val d5 = 0.5 + sin(d2 + 2.356194490192345) * 0.2
        val d6 = 0.5 + cos(d2 + Math.PI / 4.0) * 0.2
        val d7 = 0.5 + sin(d2 + Math.PI / 4.0) * 0.2
        val d8 = 0.5 + cos(d2 + 3.9269908169872414) * 0.2
        val d9 = 0.5 + sin(d2 + 3.9269908169872414) * 0.2
        val d10 = 0.5 + cos(d2 + 5.497787143782138) * 0.2
        val d11 = 0.5 + sin(d2 + 5.497787143782138) * 0.2
        val d14 = -1.0 + d1
        val d15 = height.toDouble() * 2.5 + d14
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
        worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(1.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(1.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(0.0, d14).color(r, g, b, 1.0f).endVertex()
        worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(0.0, d15).color(r, g, b, 1.0f * alphaMultiplier)
            .endVertex()
        tessellator.draw()
        GlStateManager.disableCull()
        val d12 = -1.0 + d1
        val d13 = height + d12
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.2).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.2).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.2).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.2).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.8).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.8).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.8).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.8).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.2).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.2).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + bottomOffset, z + 0.8).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.8, y + topOffset, z + 0.8).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.8).tex(1.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.8).tex(1.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + bottomOffset, z + 0.2).tex(0.0, d12).color(r, g, b, 0.25f).endVertex()
        worldrenderer.pos(x + 0.2, y + topOffset, z + 0.2).tex(0.0, d13).color(r, g, b, 0.25f * alphaMultiplier)
            .endVertex()
        tessellator.draw()
    }

    internal fun <T> Color.withParts(block: (Int, Int, Int, Int) -> T) =
        block(this.red, this.green, this.blue, this.alpha)

    fun drawFilledBoundingBox(matrixStack: UMatrixStack, aabb: AxisAlignedBB, c: Color, alphaMultiplier: Float = 1f) {
        UGraphics.enableBlend()
        UGraphics.disableLighting()
        UGraphics.tryBlendFuncSeparate(770, 771, 1, 0)
        val wr = UGraphics.getFromTessellator()
        wr.beginWithDefaultShader(UGraphics.DrawMode.QUADS, DefaultVertexFormats.POSITION_COLOR)
        val adjustedAlpha = (c.alpha * alphaMultiplier).toInt().coerceAtMost(255)

        // vertical
        c.withAlpha(adjustedAlpha).withParts { r, g, b, a ->
            // bottom
            wr.pos(matrixStack, aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex()
            // top
            wr.pos(matrixStack, aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex()
        }

        // x axis
        c.withParts { r, g, b, _ ->
            Color(
                (r * 0.8f).toInt(),
                (g * 0.8f).toInt(),
                (b * 0.8f).toInt(),
                adjustedAlpha
            )
        }.withParts { r, g, b, a ->
            // west
            wr.pos(matrixStack, aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex()
            // east
            wr.pos(matrixStack, aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex()
        }

        // z axis
        c.withParts { r, g, b, _ ->
            Color(
                (r * 0.9f).toInt(),
                (g * 0.9f).toInt(),
                (b * 0.9f).toInt(),
                adjustedAlpha
            )
        }.withParts { r, g, b, a ->
            // north
            wr.pos(matrixStack, aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex()
            // south
            wr.pos(matrixStack, aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex()
            wr.pos(matrixStack, aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex()
        }

        wr.drawDirect()
        UGraphics.disableBlend()
        UGraphics.enableLighting()
    }

    /**
     * @author Mojang
     */
    @JvmStatic
    fun drawOutlinedBoundingBox(aabb: AxisAlignedBB?, color: Color, width: Float, partialTicks: Float) {
        val render = mc.renderViewEntity
        val realX = interpolate(render.posX, render.lastTickPosX, partialTicks)
        val realY = interpolate(render.posY, render.lastTickPosY, partialTicks)
        val realZ = interpolate(render.posZ, render.lastTickPosZ, partialTicks)
        GlStateManager.pushMatrix()
        GlStateManager.translate(-realX, -realY, -realZ)
        GlStateManager.disableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.disableAlpha()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GL11.glLineWidth(width)
        RenderGlobal.drawOutlinedBoundingBox(aabb, color.red, color.green, color.blue, color.alpha)
        GlStateManager.translate(realX, realY, realZ)
        GlStateManager.disableBlend()
        GlStateManager.enableAlpha()
        GlStateManager.enableTexture2D()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.popMatrix()
    }

    /**
     * Taken from Skyblockcatia under MIT License
     * Modified
     * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
     *
     * @author SteveKunG
     */
    @JvmStatic
    fun renderItem(itemStack: ItemStack?, x: Int, y: Int) {
        RenderHelper.enableGUIStandardItemLighting()
        GlStateManager.enableDepth()
        mc.renderItem.renderItemAndEffectIntoGUI(itemStack, x, y)
    }

    /**
     * Taken from Skyblockcatia under MIT License
     * Modified
     * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
     *
     * @author SteveKunG
     */
    @JvmStatic
    fun renderTexture(
        texture: ResourceLocation?,
        x: Int,
        y: Int,
        width: Int = 16,
        height: Int = 16,
        enableLighting: Boolean = true
    ) {
        if (enableLighting) RenderHelper.enableGUIStandardItemLighting()
        GlStateManager.enableRescaleNormal()
        GlStateManager.enableBlend()
        GlStateManager.enableDepth()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.pushMatrix()
        mc.textureManager.bindTexture(texture)
        GlStateManager.enableRescaleNormal()
        GlStateManager.enableAlpha()
        GlStateManager.alphaFunc(516, 0.1f)
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(770, 771)
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0f, 0f, width, height, width.toFloat(), height.toFloat())
        GlStateManager.disableAlpha()
        GlStateManager.disableRescaleNormal()
        GlStateManager.disableLighting()
        GlStateManager.popMatrix()
    }

    fun draw3DLine(
        pos1: Vec3,
        pos2: Vec3,
        width: Int,
        color: Color,
        partialTicks: Float,
        matrixStack: UMatrixStack,
        alphaMultiplier: Float = 1f
    ) {
        val render = mc.renderViewEntity
        val worldRenderer = UGraphics.getFromTessellator()
        val realX = interpolate(render.posX, render.lastTickPosX, partialTicks)
        val realY = interpolate(render.posY, render.lastTickPosY, partialTicks)
        val realZ = interpolate(render.posZ, render.lastTickPosZ, partialTicks)
        matrixStack.push()
        matrixStack.translate(-realX, -realY, -realZ)
        UGraphics.enableBlend()
        UGraphics.enableAlpha()
        UGraphics.disableLighting()
        UGraphics.tryBlendFuncSeparate(770, 771, 1, 0)
        GL11.glLineWidth(width.toFloat())
        UGraphics.color4f(
            color.red / 255f,
            color.green / 255f,
            color.blue / 255f,
            (color.alpha * alphaMultiplier / 255f).coerceAtMost(1f)
        )
        worldRenderer.beginWithActiveShader(UGraphics.DrawMode.LINE_STRIP, DefaultVertexFormats.POSITION)
        worldRenderer.pos(matrixStack, pos1.xCoord, pos1.yCoord, pos1.zCoord).endVertex()
        worldRenderer.pos(matrixStack, pos2.xCoord, pos2.yCoord, pos2.zCoord).endVertex()
        worldRenderer.drawDirect()
        matrixStack.pop()
        UGraphics.disableBlend()
        UGraphics.enableAlpha()
        UGraphics.color4f(1f, 1f, 1f, 1f)
    }

    fun drawLabel(
        pos: Vec3,
        text: String,
        color: Color,
        partialTicks: Float,
        matrixStack: UMatrixStack,
        shadow: Boolean = false,
        scale: Float = 1f
    ) = drawNametag(pos.xCoord, pos.yCoord, pos.zCoord, text, color, partialTicks, matrixStack, shadow, scale, false)

    fun renderWaypointText(str: String, loc: BlockPos, partialTicks: Float, matrixStack: UMatrixStack) =
        renderWaypointText(
            str,
            loc.x.toDouble(),
            loc.y.toDouble(),
            loc.z.toDouble(),
            partialTicks,
            matrixStack
        )

    fun renderWaypointText(
        str: String,
        x: Double,
        y: Double,
        z: Double,
        partialTicks: Float,
        matrixStack: UMatrixStack
    ) {
        matrixStack.push()
        GlStateManager.alphaFunc(516, 0.1f)
        val (viewerX, viewerY, viewerZ) = getViewerPos(partialTicks)
        val distX = x - viewerX
        val distY = y - viewerY - mc.renderViewEntity.eyeHeight
        val distZ = z - viewerZ
        val dist = sqrt(distX * distX + distY * distY + distZ * distZ)
        val renderX: Double
        val renderY: Double
        val renderZ: Double
        if (dist > 12) {
            renderX = distX * 12 / dist + viewerX
            renderY = distY * 12 / dist + viewerY + mc.renderViewEntity.eyeHeight
            renderZ = distZ * 12 / dist + viewerZ
        } else {
            renderX = x
            renderY = y
            renderZ = z
        }
        drawNametag(renderX, renderY, renderZ, str, Color.WHITE, partialTicks, matrixStack)
        matrixStack.rotate(-mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        matrixStack.rotate(mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        matrixStack.translate(0.0, -0.25, 0.0)
        matrixStack.rotate(-mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        matrixStack.rotate(mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        drawNametag(
            renderX,
            renderY,
            renderZ,
            "${ChatColor.YELLOW}${dist.roundToInt()}m",
            Color.WHITE,
            partialTicks,
            matrixStack
        )
        matrixStack.pop()
    }

    private fun drawNametag(
        x: Double, y: Double, z: Double,
        str: String, color: Color,
        partialTicks: Float, matrixStack: UMatrixStack,
        shadow: Boolean = true, scale: Float = 1f, background: Boolean = true
    ) {
        val player = mc.thePlayer
        val x1 = x - player.lastTickPosX + (x - player.posX - (x - player.lastTickPosX)) * partialTicks
        val y1 = y - player.lastTickPosY + (y - player.posY - (y - player.lastTickPosY)) * partialTicks
        val z1 = z - player.lastTickPosZ + (z - player.posZ - (z - player.lastTickPosZ)) * partialTicks
        val f1 = 0.0266666688
        val width = mc.fontRendererObj.getStringWidth(str) / 2
        matrixStack.push()
        matrixStack.translate(x1, y1, z1)
        GL11.glNormal3f(0f, 1f, 0f)
        matrixStack.rotate(-mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        matrixStack.rotate(mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        matrixStack.scale(-f1, -f1, -f1)
        UGraphics.disableLighting()
        UGraphics.depthMask(false)
        UGraphics.enableBlend()
        UGraphics.tryBlendFuncSeparate(770, 771, 1, 0)
        if (background) {
            val worldRenderer = UGraphics.getFromTessellator()
            worldRenderer.beginWithDefaultShader(UGraphics.DrawMode.QUADS, DefaultVertexFormats.POSITION_COLOR)
            worldRenderer.pos(matrixStack, (-width - 1.0), -1.0, 0.0).color(0f, 0f, 0f, 0.25f).endVertex()
            worldRenderer.pos(matrixStack, (-width - 1.0), 8.0, 0.0).color(0f, 0f, 0f, 0.25f).endVertex()
            worldRenderer.pos(matrixStack, width + 1.0, 8.0, 0.0).color(0f, 0f, 0f, 0.25f).endVertex()
            worldRenderer.pos(matrixStack, width + 1.0, -1.0, 0.0).color(0f, 0f, 0f, 0.25f).endVertex()
            worldRenderer.drawDirect()
        }
        GlStateManager.enableTexture2D()
        DefaultFonts.VANILLA_FONT_RENDERER.drawString(
            matrixStack,
            str,
            color,
            -width.toFloat(),
            ElementaFonts.MINECRAFT.getBelowLineHeight() * scale,
            width * 2f,
            scale,
            shadow
        )
        UGraphics.depthMask(true)
        matrixStack.pop()
    }



    /**
     * Taken from Skyblockcatia under MIT License
     * https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md
     * @author SteveKunG
     */



    /**
     * Taken from SkyblockAddons under MIT License
     * Modified
     * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     *
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    fun drawRect(left: Double, top: Double, right: Double, bottom: Double, color: Int) {
        var leftModifiable = left
        var topModifiable = top
        var rightModifiable = right
        var bottomModifiable = bottom
        if (leftModifiable < rightModifiable) {
            val i = leftModifiable
            leftModifiable = rightModifiable
            rightModifiable = i
        }
        if (topModifiable < bottomModifiable) {
            val j = topModifiable
            topModifiable = bottomModifiable
            bottomModifiable = j
        }
        val f3 = (color shr 24 and 255).toFloat() / 255.0f
        val f = (color shr 16 and 255).toFloat() / 255.0f
        val f1 = (color shr 8 and 255).toFloat() / 255.0f
        val f2 = (color and 255).toFloat() / 255.0f
        GlStateManager.color(f, f1, f2, f3)
        val tessellator = Tessellator.getInstance()
        val worldRenderer = tessellator.worldRenderer
        GlStateManager.enableBlend()
        GlStateManager.disableTexture2D()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        worldRenderer.begin(7, DefaultVertexFormats.POSITION)
        worldRenderer.pos(leftModifiable, bottomModifiable, 0.0).endVertex()
        worldRenderer.pos(rightModifiable, bottomModifiable, 0.0).endVertex()
        worldRenderer.pos(rightModifiable, topModifiable, 0.0).endVertex()
        worldRenderer.pos(leftModifiable, topModifiable, 0.0).endVertex()
        tessellator.draw()
        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()
    }

    /**
     * Taken from SkyblockAddons under MIT License
     * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     *
     */
    fun drawCylinderInWorld(x: Double, y: Double, z: Double, radius: Float, height: Float, partialTicks: Float) {
        var x1 = x
        var y1 = y
        var z1 = z
        val renderViewEntity = mc.renderViewEntity
        val viewX =
            renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * partialTicks.toDouble()
        val viewY =
            renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * partialTicks.toDouble()
        val viewZ =
            renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * partialTicks.toDouble()
        x1 -= viewX
        y1 -= viewY
        z1 -= viewZ
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        worldrenderer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION)
        var currentAngle = 0f
        val angleStep = 0.1f
        while (currentAngle < 2 * Math.PI) {
            val xOffset = radius * cos(currentAngle.toDouble()).toFloat()
            val zOffset = radius * sin(currentAngle.toDouble()).toFloat()
            worldrenderer.pos(x1 + xOffset, y1 + height, z1 + zOffset).endVertex()
            worldrenderer.pos(x1 + xOffset, y1 + 0, z1 + zOffset).endVertex()
            currentAngle += angleStep
        }
        worldrenderer.pos(x1 + radius, y1 + height, z1).endVertex()
        worldrenderer.pos(x1 + radius, y1 + 0.0, z1).endVertex()
        tessellator.draw()
    }

    // totally not modified Autumn Client's TargetStrafe
    fun drawCircle(entity: Entity, partialTicks: Float, rad: Double, color: Color) {
        var il = 0.0
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        while (il < 0.05) {
            GlStateManager.pushMatrix()
            GlStateManager.disableTexture2D()
            GL11.glLineWidth(2F)
            worldrenderer.begin(1, DefaultVertexFormats.POSITION)
            val x: Double =
                entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.renderManager.viewerPosX
            val y: Double =
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.renderManager.viewerPosY
            val z: Double =
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.renderManager.viewerPosZ
            val pix2 = Math.PI * 2.0
            for (i in 0..90) {
                color.bindColor()
                worldrenderer.pos(x + rad * cos(i * pix2 / 45.0), y + il, z + rad * sin(i * pix2 / 45.0)).endVertex()
            }
            tessellator.draw()
            GlStateManager.enableTexture2D()
            GlStateManager.popMatrix()
            il += 0.0006
        }
    }

    fun drawCircle(matrixStack: UMatrixStack, x: Double, y: Double, z: Double, partialTicks: Float, radius: Double, edges: Int, r: Int, g: Int, b: Int, a: Int = 255) {
        val ug = UGraphics.getFromTessellator()
        val angleDelta = Math.PI * 2 / edges
        GL11.glLineWidth(5f)
        ug.beginWithDefaultShader(UGraphics.DrawMode.LINE_STRIP, UGraphics.CommonVertexFormats.POSITION_COLOR)
        repeat(edges) { idx ->
            ug.pos(matrixStack, x - mc.renderManager.viewerPosX + radius * cos(idx * angleDelta), y - mc.renderManager.viewerPosY, z - mc.renderManager.viewerPosZ + radius * sin(idx * angleDelta)).color(r, g, b, a).endVertex()
        }
        ug.pos(matrixStack, x + radius - mc.renderManager.viewerPosX, y - mc.renderManager.viewerPosY, z - mc.renderManager.viewerPosZ).color(r, g, b, a).endVertex()
        ug.drawDirect()
    }

    fun getViewerPos(partialTicks: Float): Triple<Double, Double, Double> {
        val viewer = mc.renderViewEntity
        val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks
        val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks
        val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks
        return Triple(viewerX, viewerY, viewerZ)
    }







    infix fun Slot.highlight(color: Color) {
        Gui.drawRect(
            this.xDisplayPosition,
            this.yDisplayPosition,
            this.xDisplayPosition + 16,
            this.yDisplayPosition + 16,
            color.rgb
        )
    }

    infix fun Slot.highlight(color: Int) {
        Gui.drawRect(
            this.xDisplayPosition,
            this.yDisplayPosition,
            this.xDisplayPosition + 16,
            this.yDisplayPosition + 16,
            color
        )
    }

    fun drawDurabilityBar(xPos: Int, yPos: Int, durability: Double) {
        val j = (13.0 - durability * 13.0).roundToInt()
        val i = (255.0 - durability * 255.0).roundToInt()
        GlStateManager.disableLighting()
        GlStateManager.disableDepth()
        GlStateManager.disableTexture2D()
        GlStateManager.disableAlpha()
        GlStateManager.disableBlend()
        drawRect(xPos + 2, yPos + 13, 13, 2, 0, 0, 0, 255)
        drawRect(xPos + 2, yPos + 13, 12, 1, (255 - i) / 4, 64, 0, 255)
        drawRect(xPos + 2, yPos + 13, j, 1, 255 - i, i, 0, 255)
        //GlStateManager.enableBlend()
        GlStateManager.enableAlpha()
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
    }

    fun drawRect(x: Number, y: Number, width: Number, height: Number, red: Int, green: Int, blue: Int, alpha: Int) {
        val tesselator = Tessellator.getInstance()
        val renderer = tesselator.worldRenderer
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
        renderer.pos(x.toDouble(), y.toDouble(), 0.0).color(red, green, blue, alpha).endVertex()
        renderer.pos(x.toDouble(), y.toDouble() + height.toDouble(), 0.0).color(red, green, blue, alpha).endVertex()
        renderer.pos(x.toDouble() + width.toDouble(), y.toDouble() + height.toDouble(), 0.0)
            .color(red, green, blue, alpha).endVertex()
        renderer.pos(x.toDouble() + width.toDouble(), y.toDouble(), 0.0).color(red, green, blue, alpha).endVertex()
        tesselator.draw()
    }

    // see GuiIngame
    private val vignetteTexPath = ResourceLocation("textures/misc/vignette.png")

    /**
     * @author Mojang (modified)
     * @see net.minecraft.client.gui.GuiIngame.renderVignette
     */
    fun drawVignette(color: Color) {
        mc.entityRenderer.setupOverlayRendering()
        UGraphics.enableBlend()
        UGraphics.disableDepth()

        UGraphics.depthMask(false)
        UGraphics.tryBlendFuncSeparate(0, 769, 1, 0)

        // Changing the alpha doesn't affect the vignette, so we have to use the alpha to change the color values
        UGraphics.color4f(
            (1f - (color.red / 255f)) * (color.alpha / 255f),
            (1f - (color.green / 255f)) * (color.alpha / 255f),
            (1f - (color.blue / 255f)) * (color.alpha / 255f),
            1f
        )

        val sr = UResolution

        UGraphics.bindTexture(0, vignetteTexPath)
        val tessellator = UGraphics.getTessellator()
        val wr = UGraphics.getFromTessellator()
        val matrixStack = UMatrixStack()
        wr.beginWithDefaultShader(UGraphics.DrawMode.QUADS, DefaultVertexFormats.POSITION_TEX)
        wr.pos(matrixStack, 0.0, sr.scaledHeight.toDouble(), -90.0).tex(0.0, 1.0).endVertex()
        wr.pos(matrixStack, sr.scaledWidth.toDouble(), sr.scaledHeight.toDouble(), -90.0).tex(1.0, 1.0).endVertex()
        wr.pos(matrixStack, sr.scaledWidth.toDouble(), 0.0, -90.0).tex(1.0, 0.0).endVertex()
        wr.pos(matrixStack, 0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex()
        tessellator.draw()

        UGraphics.depthMask(true)
        UGraphics.enableDepth()
        UGraphics.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        UGraphics.tryBlendFuncSeparate(770, 771, 1, 0)
    }

    /*
    * @link https://stackoverflow.com/a/54913292
    */
    fun mixColors(vararg colors: Color): Color {
        val ratio = 1f / colors.size.toFloat()
        var r = 0
        var g = 0
        var b = 0
        var a = 0
        for (color in colors) {
            r += (color.red * ratio).toInt()
            g += (color.green * ratio).toInt()
            b += (color.blue * ratio).toInt()
            a += (color.alpha * ratio).toInt()
        }
        return Color(r, g, b, a)
    }

    fun interpolate(currentValue: Double, lastValue: Double, multiplier: Float): Double {
        return lastValue + (currentValue - lastValue) * multiplier
    }

    /*
    TODO: Update to new UI
    fun drawAllInList(element: GuiElement, lines: Collection<String>) {
        val leftAlign = element.scaleX < UResolution.scaledWidth / 2f
        val alignment =
            if (leftAlign) SmartFontRenderer.TextAlignment.LEFT_RIGHT else SmartFontRenderer.TextAlignment.RIGHT_LEFT
        val xPos = if (leftAlign) 0f else element.width.toFloat()
        for ((i, str) in lines.withIndex()) {
            ScreenRenderer.fontRenderer.drawString(
                str,
                xPos,
                (i * ScreenRenderer.fontRenderer.FONT_HEIGHT).toFloat(),
                CommonColors.WHITE,
                alignment,
                element.textShadow
            )
        }
    }
*/
    fun drawSelectionBox(
        pos: BlockPos,
        block: Block = mc.theWorld.getBlockState(pos).block,
        color: Color,
        partialTicks: Float
    ) {
        val (viewerX, viewerY, viewerZ) = getViewerPos(partialTicks)
        val matrixStack = UMatrixStack()
        GlStateManager.disableCull()
        GlStateManager.disableDepth()
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        block.setBlockBoundsBasedOnState(mc.theWorld, pos)
        drawFilledBoundingBox(
            matrixStack,
            block.getSelectedBoundingBox(mc.theWorld, pos)
                .expandBlock()
                .offset(-viewerX, -viewerY, -viewerZ),
            color
        )
        GlStateManager.disableBlend()
        GlStateManager.enableCull()
        GlStateManager.enableDepth()
    }
}

fun Color.bindColor() = GlStateManager.color(this.red / 255f, this.green / 255f, this.blue / 255f, this.alpha / 255f)
fun Color.withAlpha(alpha: Int): Int = (alpha.coerceIn(0, 255) shl 24) or (this.rgb and 0x00ffffff)

fun Color.multAlpha(mult: Float) = Color(
    red,
    green,
    blue,
    (alpha * mult).toInt().coerceIn(0, 255)
)

fun AxisAlignedBB.expandBlock(): AxisAlignedBB =
    expand(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026)