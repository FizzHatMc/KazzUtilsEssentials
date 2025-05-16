package de.kazzutils.utils

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.colors.CommonColors
import de.kazzutils.utils.graphics.ScreenRenderer
import de.kazzutils.utils.graphics.SmartFontRenderer
import gg.essential.universal.UResolution
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.util.*
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object RenderUtils {

    private val beaconBeam = ResourceLocation("textures/entity/beacon_beam.png")

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

    fun drawCustomBox(
        x: Double,
        xwidth: Double,
        y: Double,
        ywidth: Double,
        z: Double,
        zwidth: Double,
        color: Color,
        thickness: Float,
        phase: Boolean
    ) {
        val renderManager: RenderManager = KazzUtils.Companion.mc.renderManager
        val tessellator = Tessellator.getInstance()
        val worldRenderer = Tessellator.getInstance().worldRenderer

        GlStateManager.pushMatrix()
        GlStateManager.color(color.red / 255f, color.green / 255f, color.blue / 255f, 1f)
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ)
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        if (phase) GlStateManager.disableDepth()
        GlStateManager.disableTexture2D()
        GlStateManager.disableLighting()
        GlStateManager.enableBlend()

        val x1 = x + xwidth
        val y1 = y + ywidth
        val z1 = z + zwidth

        worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION)
        worldRenderer.pos(x1, y1, z1).endVertex()
        worldRenderer.pos(x1, y1, z).endVertex()
        worldRenderer.pos(x, y1, z).endVertex()
        worldRenderer.pos(x, y1, z1).endVertex()
        worldRenderer.pos(x1, y1, z1).endVertex()
        worldRenderer.pos(x1, y, z1).endVertex()
        worldRenderer.pos(x1, y, z).endVertex()
        worldRenderer.pos(x, y, z).endVertex()
        worldRenderer.pos(x, y, z1).endVertex()
        worldRenderer.pos(x, y, z).endVertex()
        worldRenderer.pos(x, y1, z).endVertex()
        worldRenderer.pos(x, y, z).endVertex()
        worldRenderer.pos(x1, y, z).endVertex()
        worldRenderer.pos(x1, y1, z).endVertex()
        worldRenderer.pos(x1, y, z).endVertex()
        worldRenderer.pos(x1, y, z1).endVertex()
        worldRenderer.pos(x, y, z1).endVertex()
        worldRenderer.pos(x, y1, z1).endVertex()
        worldRenderer.pos(x1, y1, z1).endVertex()

        tessellator.draw()

        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()
        if (phase) GlStateManager.disableBlend()
        GlStateManager.popMatrix()
    }

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     *
     * @author Moulberry
     */
    fun renderBeaconBeam(x: Double, y: Double, z: Double, rgb: Int, alphaMultiplier: Float, partialTicks: Float) {
        val height = 300
        val bottomOffset = 0
        val topOffset = bottomOffset + height
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        KazzUtils.Companion.mc.textureManager.bindTexture(beaconBeam)
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0f)
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0f)
        GlStateManager.disableLighting()
        GlStateManager.enableCull()
        GlStateManager.enableTexture2D()
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, 1, 1, 0)
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0)
        val time = KazzUtils.Companion.mc.theWorld.totalWorldTime + partialTicks.toDouble()
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

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     *
     * @author Moulberry
     */
    fun drawFilledBoundingBox(aabb: AxisAlignedBB, c: Color, alphaMultiplier: Float) {
        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.disableTexture2D()

        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer

        GlStateManager.color(c.red / 255f, c.green / 255f, c.blue / 255f, c.alpha / 255f * alphaMultiplier)

        //vertical
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex()
        tessellator.draw()
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex()
        tessellator.draw()


        GlStateManager.color(
            c.red / 255f * 0.8f,
            c.green / 255f * 0.8f,
            c.blue / 255f * 0.8f,
            c.alpha / 255f * alphaMultiplier
        )

        //x
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex()
        tessellator.draw()
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex()
        tessellator.draw()


        GlStateManager.color(
            c.red / 255f * 0.9f,
            c.green / 255f * 0.9f,
            c.blue / 255f * 0.9f,
            c.alpha / 255f * alphaMultiplier
        )
        //z
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex()
        tessellator.draw()
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex()
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex()
        tessellator.draw()
        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()
    }

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     *
     * @author Moulberry
     */
    fun renderWaypointText(str: String?, loc: BlockPos, partialTicks: Float) {
        GlStateManager.alphaFunc(516, 0.1f)

        GlStateManager.pushMatrix()

        val viewer: Entity = KazzUtils.Companion.mc.renderViewEntity
        val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks
        val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks
        val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks

        var x = loc.x + 0.5 - viewerX
        var y = loc.y - viewerY - viewer.eyeHeight
        var z = loc.z + 0.5 - viewerZ

        val distSq = x * x + y * y + z * z
        val dist = sqrt(distSq)
        if (distSq > 144) {
            x *= 12 / dist
            y *= 12 / dist
            z *= 12 / dist
        }
        GlStateManager.translate(x, y, z)
        GlStateManager.translate(0f, viewer.eyeHeight, 0f)

        drawNametag(str)

        GlStateManager.rotate(-KazzUtils.Companion.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(KazzUtils.Companion.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)

        GlStateManager.translate(0f, -0.25f, 0f)
        GlStateManager.rotate(-KazzUtils.Companion.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(KazzUtils.Companion.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)

        drawNametag(EnumChatFormatting.YELLOW.toString() + Math.round(dist) + "m")

        GlStateManager.popMatrix()

        GlStateManager.disableLighting()
    }

    fun renderWaypointText(str: String?, loc: BlockPos, partialTicks: Float, scale: Float) {
        GlStateManager.alphaFunc(516, 0.1f)

        GlStateManager.pushMatrix()

        val viewer: Entity = KazzUtils.Companion.mc.renderViewEntity
        val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks
        val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks
        val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks

        var x = loc.x + 0.5 - viewerX
        var y = loc.y - viewerY - viewer.eyeHeight
        var z = loc.z + 0.5 - viewerZ

        val distSq = x * x + y * y + z * z
        val dist = sqrt(distSq)
        if (distSq > 144) {
            x *= 12 / dist
            y *= 12 / dist
            z *= 12 / dist
        }
        GlStateManager.translate(x, y, z)
        GlStateManager.translate(0f, viewer.eyeHeight, 0f)
        GlStateManager.scale(scale, scale, scale)
        drawNametag(str)

        GlStateManager.rotate(-KazzUtils.Companion.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(KazzUtils.Companion.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)

        GlStateManager.translate(0f, -0.25f, 0f)
        GlStateManager.rotate(-KazzUtils.Companion.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(KazzUtils.Companion.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)


        //drawNametag(EnumChatFormatting.YELLOW.toString()+Math.round(dist)+"m");
        GlStateManager.popMatrix()

        GlStateManager.disableLighting()
    }

    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     *
     * @author Moulberry
     */
    fun drawNametag(str: String?) {
        val fontrenderer: FontRenderer = KazzUtils.Companion.mc.fontRendererObj
        val f = 1.6f
        val f1 = 0.016666668f * f
        GlStateManager.pushMatrix()
        GL11.glNormal3f(0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(-KazzUtils.Companion.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(KazzUtils.Companion.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        GlStateManager.scale(-f1, -f1, f1)
        GlStateManager.disableLighting()
        GlStateManager.depthMask(false)
        GlStateManager.disableDepth()
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        val i = 0

        val j = fontrenderer.getStringWidth(str) / 2
        GlStateManager.disableTexture2D()
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR)
        worldrenderer.pos((-j - 1).toDouble(), (-1 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        worldrenderer.pos((-j - 1).toDouble(), (8 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        worldrenderer.pos((j + 1).toDouble(), (8 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        worldrenderer.pos((j + 1).toDouble(), (-1 + i).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
        tessellator.draw()
        GlStateManager.enableTexture2D()
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127)
        GlStateManager.depthMask(true)

        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1)

        GlStateManager.enableDepth()
        GlStateManager.enableBlend()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.popMatrix()
    }

    /**
     * Taken from WaterSolver
     * https://github.com/Desco1/WaterSolver
     *
     * @author Desco
     */
    fun drawOutlinedBoundingBox(aabb: AxisAlignedBB?, color: Color, width: Float, partialTicks: Float) {
        val render: Entity = KazzUtils.Companion.mc.renderViewEntity
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

    // Assuming that 'interpolate' is a method with the following signature
    private fun interpolate(current: Double, previous: Double, partialTicks: Float): Double {
        return previous + (current - previous) * partialTicks
    }

    fun drawLineToEye(location: Vec3, color: Color, partialTicks : Float) {
        drawLine(exactPlayerEyeLocation(partialTicks), location, color, partialTicks)
    }

    fun exactPlayerEyeLocation(partialTicks : Float): Vec3 {
        val player = mc.thePlayer
        val add = if (player.isSneaking) Vec3(0.0, 1.54, 0.0) else Vec3(0.0, 1.62, 0.0)
//        PatcherFixes.onPlayerEyeLine()
        return exactLocation(player,partialTicks).add(add)
    }

//    fun exactLocation(entity: Entity) = exactLocation(entity, partialTicks)

    fun exactLocation(entity: Entity, partialTicks: Float): Vec3 {
        if (entity.isDead) return Vec3(entity.posX, entity.posY, entity.posZ)
        val x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks
        val y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks
        val z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks
        return Vec3(x, y, z)
    }

    fun drawLine(pos1: Vec3, pos2: Vec3, color: Color, partialTicks: Float) {
        val render: Entity = mc.renderViewEntity
        val realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks
        val realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks
        val realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks

        val red = color.red
        val green = color.green
        val blue = color.blue
        val alpha = color.alpha


        GlStateManager.pushMatrix()
        GlStateManager.translate(-realX, -realY, -realZ)
        GlStateManager.disableTexture2D()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GL11.glLineWidth(2f)

        val tes = Tessellator.getInstance()
        val wr = tes.worldRenderer

        wr.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR)
        wr.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).color(red, green, blue, alpha).endVertex()
        wr.pos(pos2.xCoord, pos2.yCoord, pos2.zCoord).color(red, green, blue, alpha).endVertex()
        tes.draw()
        GlStateManager.popMatrix()
        GlStateManager.enableTexture2D()
    }

    fun renderBoxOutline(
        pos: BlockPos,
        width: Int,
        height: Int,
        depth: Int,
        partialTicks: Float,
        lineWidth: Float,
        c: Color,
        alphaMultiplier: Float
    ) {
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer

        val player: Entity = mc.renderViewEntity
        val playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks
        val playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks
        val playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks

        val x = (pos.x + 0.5 - playerX)
        val y = (pos.y - playerY - player.eyeHeight)
        val z = (pos.z + 0.5 - playerZ)

        GL11.glPushMatrix()
        GL11.glTranslated(-playerX, -playerY, -playerZ)

        GL11.glLineWidth(lineWidth)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glDisable(GL11.GL_DEPTH_TEST)

        GlStateManager.color(c.red / 255f, c.green / 255f, c.blue / 255f, c.alpha / 255f * alphaMultiplier)

        worldrenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION)

        // Bottom Rectangle
        worldrenderer.pos(x, y, z).endVertex()
        worldrenderer.pos(x + width, y, z).endVertex()

        worldrenderer.pos(x + width, y, z).endVertex()
        worldrenderer.pos(x + width, y, z + depth).endVertex()

        worldrenderer.pos(x + width, y, z + depth).endVertex()
        worldrenderer.pos(x, y, z + depth).endVertex()

        worldrenderer.pos(x, y, z + depth).endVertex()
        worldrenderer.pos(x, y, z).endVertex()

        // Top Rectangle
        worldrenderer.pos(x, y + height, z).endVertex()
        worldrenderer.pos(x + width, y + height, z).endVertex()

        worldrenderer.pos(x + width, y + height, z).endVertex()
        worldrenderer.pos(x + width, y + height, z + depth).endVertex()

        worldrenderer.pos(x + width, y + height, z + depth).endVertex()
        worldrenderer.pos(x, y + height, z + depth).endVertex()

        worldrenderer.pos(x, y + height, z + depth).endVertex()
        worldrenderer.pos(x, y + height, z).endVertex()

        // Connecting Lines
        worldrenderer.pos(x, y, z).endVertex()
        worldrenderer.pos(x, y + height, z).endVertex()

        worldrenderer.pos(x + width, y, z).endVertex()
        worldrenderer.pos(x + width, y + height, z).endVertex()

        worldrenderer.pos(x + width, y, z + depth).endVertex()
        worldrenderer.pos(x + width, y + height, z + depth).endVertex()

        worldrenderer.pos(x, y, z + depth).endVertex()
        worldrenderer.pos(x, y + height, z + depth).endVertex()

        tessellator.draw()

        GL11.glPopMatrix()
        GL11.glEnable(GL11.GL_LIGHTING)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
    }

    private fun getRenderPos(vec: Vec3): Vec3 {
        val renderPosX: Double = KazzUtils.Companion.mc.renderManager.viewerPosX
        val renderPosY: Double = KazzUtils.Companion.mc.renderManager.viewerPosY
        val renderPosZ: Double = KazzUtils.Companion.mc.renderManager.viewerPosZ
        return Vec3(vec.xCoord - renderPosX, vec.yCoord - renderPosY, vec.zCoord - renderPosZ)
    }

    fun drawCylinder(
        pos: Vec3, baseRadius: Float, topRadius: Float, height: Float,
        slices: Int, stacks: Int, rot1: Float, rot2: Float, rot3: Float,
        r: Float, g: Float, b: Float, a: Float, phase: Boolean, linemode: Boolean
    ) {
        val renderPos = getRenderPos(pos)
        val x = renderPos.xCoord.toFloat()
        val y = renderPos.yCoord.toFloat()
        val z = renderPos.zCoord.toFloat()

        GlStateManager.pushMatrix()
        GL11.glLineWidth(2.0f)
        GlStateManager.disableCull()
        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.blendFunc(770, 771)
        GlStateManager.depthMask(false)
        GlStateManager.disableTexture2D()

        if (phase) GlStateManager.disableDepth()

        GlStateManager.color(r, g, b, a)
        GlStateManager.translate(x, y, z)
        GlStateManager.rotate(rot1, 1f, 0f, 0f)
        GlStateManager.rotate(rot2, 0f, 0f, 1f)
        GlStateManager.rotate(rot3, 0f, 1f, 0f)

        GL11.glPushMatrix()
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP)
        for (i in 0..slices) {
            val angle = (i * 2 * Math.PI / slices).toFloat()
            val x1 = (cos(angle.toDouble()) * baseRadius).toFloat()
            val z1 = (sin(angle.toDouble()) * baseRadius).toFloat()
            val x2 = (cos(angle.toDouble()) * topRadius).toFloat()
            val z2 = (sin(angle.toDouble()) * topRadius).toFloat()

            GL11.glVertex3f(x1, -height / 2, z1)
            GL11.glVertex3f(x2, height / 2, z2)
        }
        GL11.glEnd()
        GL11.glPopMatrix()

        GlStateManager.enableCull()
        GlStateManager.disableBlend()
        GlStateManager.enableLighting()
        GlStateManager.depthMask(true)
        GlStateManager.enableTexture2D()
        if (phase) GlStateManager.enableDepth()
        GlStateManager.popMatrix()
    }

    fun drawCircle(
        pos: Vec3,
        radius: Float,
        thickness: Float,
        slices: Int,
        rot1: Float,
        rot2: Float,
        rot3: Float,
        r: Float,
        g: Float,
        b: Float,
        a: Float,
        phase: Boolean,
        filled: Boolean
    ) {
        val renderPos = getRenderPos(pos)
        val x = renderPos.xCoord.toFloat()
        val y = renderPos.yCoord.toFloat()
        val z = renderPos.zCoord.toFloat()

        GlStateManager.pushMatrix()
        GlStateManager.disableCull()
        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GlStateManager.depthMask(false)
        GlStateManager.disableTexture2D()
        if (phase) GlStateManager.disableDepth()

        GlStateManager.color(r, g, b, a)
        GlStateManager.translate(x, y, z)
        GlStateManager.rotate(rot1, 1f, 0f, 0f)
        GlStateManager.rotate(rot2, 0f, 0f, 1f)
        GlStateManager.rotate(rot3, 0f, 1f, 0f)

        val mode = if (filled) GL11.GL_TRIANGLE_FAN else GL11.GL_LINE_LOOP

        if (!filled) {
            GL11.glLineWidth(thickness) // <--- set your desired thickness here
        }

        GL11.glBegin(mode)

        for (i in 0..slices) {
            val angle = (i * 2.0 * Math.PI / slices).toFloat()
            val dx = (cos(angle.toDouble()) * radius).toFloat()
            val dz = (sin(angle.toDouble()) * radius).toFloat()
            GL11.glVertex3f(dx, 0f, dz)
        }

        GL11.glEnd()

        if (phase) GlStateManager.enableDepth()
        GlStateManager.enableCull()
        GlStateManager.disableBlend()
        GlStateManager.enableLighting()
        GlStateManager.depthMask(true)
        GlStateManager.enableTexture2D()
        GlStateManager.popMatrix()
    }




    fun drawTitle(title: String, subtitle: String, color: EnumChatFormatting){
        mc.ingameGUI.displayTitle(color.toString() + title, color.toString()+subtitle, 0,3,0)
    }
    fun drawTitle(title: String, color: EnumChatFormatting){
        mc.ingameGUI.displayTitle(color.toString() + title, "", 0,3,0)
    }








}