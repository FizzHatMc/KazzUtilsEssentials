package de.kazzutils

import de.kazzutils.core.GuiManager
import de.kazzutils.utils.colors.CustomColor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.init.Blocks
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import java.io.File
import java.util.*

@Mod(modid = "kazzutils", useMetadata = true, version = "0.0.2")
class KazzUtils {
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        print("Dirt: " + Blocks.dirt.unlocalizedName)
        // Below is a demonstration of an access-transformed class access.
        print("Color State: " + GlStateManager.Color())
    }

    companion object {
        const val MOD_ID = "kazzutils"
        val mc: Minecraft = Minecraft.getMinecraft()
        val modDir by lazy {
            File(File(mc.mcDataDir, "config"), "kazzutils").also {
                it.mkdirs()
                File(it, "trackers").mkdirs()
            }
        }

        @JvmField
        var displayScreen: GuiScreen? = null

        @JvmStatic
        lateinit var guiManager: GuiManager

        @JvmStatic
        val version: String
            get() = Loader.instance().indexedModList[MOD_ID]!!.version

        val json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                include(serializersModule)
                contextual(CustomColor::class, CustomColor.Serializer)
                contextual(Regex::class, RegexAsString)
                contextual(UUID::class, UUIDAsString)
            }
        }
    }

    object RegexAsString : KSerializer<Regex> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Regex", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): Regex = Regex(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: Regex) = encoder.encodeString(value.pattern)
    }

    object UUIDAsString : KSerializer<UUID> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: UUID) = encoder.encodeString(value.toString())
    }
}
