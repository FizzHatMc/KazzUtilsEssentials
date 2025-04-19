package de.kazzutils


import HypixelAPI
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import de.kazzutils.commands.CommandManager
import de.kazzutils.core.Config
import de.kazzutils.core.GuiManager
import de.kazzutils.core.PersistentSave
import de.kazzutils.gui.OptionsGui
import de.kazzutils.gui.ReopenableGUI
import de.kazzutils.transformers.AccessorGuiStreamUnavailable
import de.kazzutils.transformers.AccessorSettingsGui
import de.kazzutils.utils.Utils
import de.kazzutils.utils.colors.CustomColor
import de.kazzutils.utils.graphics.ScreenRenderer
import de.kazzutils.core.tickTimer
import de.kazzutils.features.chatStuff.ChatCommands
import de.kazzutils.features.chatfeatures.ChatEmotes
import de.kazzutils.features.dungeons.gui.DownTimeObject
import de.kazzutils.features.event.carnival.Minesweeper
import de.kazzutils.features.misc.KeyShortcuts
import de.kazzutils.features.misc.MiscStuff
import de.kazzutils.features.misc.items.GyroRange
import de.kazzutils.handler.EventHandler
import de.kazzutils.handler.hook.EntityPlayerSPHook
import de.kazzutils.handler.transformers.PacketThreadUtilTransformer
import de.kazzutils.utils.BrotliEncoder
import de.kazzutils.utils.NewTabUtils
import de.kazzutils.utils.UnionX509TrustManager
import de.kazzutils.utils.randomutils.ChatUtils
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiGameOver
import net.minecraft.client.gui.GuiIngameMenu
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent
import java.io.File
import java.security.KeyStore
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import kotlin.coroutines.CoroutineContext

@Mod(modid = "kazzutils",
    useMetadata = true,
    version = "0.0.1",
    clientSideOnly = true,
    guiFactory = "de.kazzutils.core.ForgeGuiFactory")
class KazzUtils {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        CommandManager()
        guiManager = GuiManager

        reg(ChatCommands())
        reg(ChatEmotes())
        reg(DownTimeObject.DownTimeDisplay())
        reg(GyroRange())
        reg(MiscStuff())
        reg(Minesweeper())

    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent?) {
        config.initialize()
        arrayOf(
            this,
            guiManager,
            KeyShortcuts,
            ScreenRenderer,
            EntityPlayerSPHook,
            PacketThreadUtilTransformer,
            EventHandler,
            ChatUtils,

        ).forEach(MinecraftForge.EVENT_BUS::register)
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        PersistentSave.loadData()
        ScreenRenderer.init()
        HypixelAPI.initialize(API_KEY)
    }

    private var ticks = 0L
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (config.noSelfie && mc.gameSettings.thirdPersonView == 2)
            mc.gameSettings.thirdPersonView = 0

        if (event.phase != TickEvent.Phase.START || mc.thePlayer == null) return
        ticks++

        if(ticks % 2 == 0L) {
            NewTabUtils.parseTabEntries()
        }//each 1/10th second
        if(ticks % 20 == 0L) {
            Utils.checkSkyblock()
        }//each second

        if (displayScreen != null) {
            if (mc.thePlayer?.openContainer == mc.thePlayer?.inventoryContainer) {
                mc.displayGuiScreen(displayScreen)
                displayScreen = null
            }
        }
    }


    @SubscribeEvent
    fun onConnect(event: FMLNetworkEvent.ClientConnectedToServerEvent) {

    }


    @SubscribeEvent
    fun onDisconnect(event: FMLNetworkEvent.ClientDisconnectionFromServerEvent) {

    }

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent) {
        if (mc.currentScreen is OptionsGui && event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onGuiInitPost(event: GuiScreenEvent.InitGuiEvent.Post) {
        if (config.configButtonOnPause && event.gui is GuiIngameMenu) {
            val x = event.gui.width - 105
            val x2 = x + 100
            var y = event.gui.height - 22
            var y2 = y + 20
            val sorted = event.buttonList.sortedWith { a, b -> b.yPosition + b.height - a.yPosition + a.height }
            for (button in sorted) {
                val otherX = button.xPosition
                val otherX2 = button.xPosition + button.width
                val otherY = button.yPosition
                val otherY2 = button.yPosition + button.height
                if (otherX2 > x && otherX < x2 && otherY2 > y && otherY < y2) {
                    y = otherY - 20 - 2
                    y2 = y + 20
                }
            }
            event.buttonList.add(GuiButton(6969420, x, 0.coerceAtLeast(y), 100, 20, "KazzUtils"))
        }
    }

    @SubscribeEvent
    fun onGuiAction(event: GuiScreenEvent.ActionPerformedEvent.Post) {
        if (config.configButtonOnPause && event.gui is GuiIngameMenu && event.button.id == 6969420) {
            displayScreen = OptionsGui()
        }
    }

    @SubscribeEvent
    fun onGuiChange(event: GuiOpenEvent) {
        val old = mc.currentScreen
        if (event.gui == null && old is OptionsGui && old.parent != null) {
            displayScreen = old.parent
        } else if (event.gui == null && config.reopenOptionsMenu) {
            if (old is ReopenableGUI || (old is AccessorSettingsGui && old.config is Config)) {
                tickTimer(1) {
                    if (mc.thePlayer?.openContainer == mc.thePlayer?.inventoryContainer)
                        displayScreen = OptionsGui()
                }
            }
        }
        if (old is AccessorGuiStreamUnavailable) {
            if (event.gui == null && !(Utils.inSkyblock && old.parentScreen is GuiGameOver)) {
                event.gui = old.parentScreen
            }
        }
    }



    companion object : CoroutineScope {
        const val MOD_ID = "kazzutils"
        const val VERSION = "0.0.2"
        const val API_KEY = "94ce5984-d4d7-4436-97c3-ffee67af8d34"

        @JvmStatic
        val mc: Minecraft by lazy {
            Minecraft.getMinecraft()
        }

        val config by lazy {
            Config
        }

        val modDir by lazy {
            File(File(mc.mcDataDir, "config"), "kazzutils").also {
                it.mkdirs()
                File(it, "trackers").mkdirs()
            }
        }

        @JvmField
        val threadPool = Executors.newFixedThreadPool(10) as ThreadPoolExecutor

        @JvmField
        val dispatcher = threadPool.asCoroutineDispatcher()



        val IO = object : CoroutineScope {
            override val coroutineContext = Dispatchers.IO + SupervisorJob() + CoroutineName("KazzUtils IO")
        }

        override val coroutineContext: CoroutineContext = dispatcher + SupervisorJob() + CoroutineName("KazzUtils")

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

    private fun reg(obj: Any){
        MinecraftForge.EVENT_BUS.register(obj)
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

    private fun String.toDashedUUID(): String {
        if (this.length != 32) return this
        return buildString {
            append(this@toDashedUUID)
            insert(20, "-")
            insert(16, "-")
            insert(12, "-")
            insert(8, "-")
        }
    }
}
