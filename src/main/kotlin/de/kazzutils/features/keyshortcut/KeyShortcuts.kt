package de.kazzutils.features.keyshortcut

import de.kazzutils.KazzUtils
import de.kazzutils.core.PersistentSave
import de.kazzutils.utils.randomutils.ChatUtils
import gg.essential.universal.UKeyboard
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import java.io.File
import java.io.Reader
import java.io.Writer

object KeyShortcuts : PersistentSave(File(KazzUtils.modDir, "keyshortcuts.json")) {
    val shortcuts = HashSet<KeybindShortcut>()

    @SubscribeEvent
    fun onInput(event: InputEvent) {
        if (shortcuts.isEmpty()) return
        val key =
            when {
                event is KeyInputEvent && Keyboard.getEventKeyState() -> Keyboard.getEventKey()
                event is InputEvent.MouseInputEvent && Mouse.getEventButtonState() -> Mouse.getEventButton() - 100
                else -> return
            }
        val modifiers = Modifiers.getBitfield(Modifiers.getPressed())
        for (s in shortcuts) {
            if (s.keyCode == 0) continue
            if (s.keyCode == key && s.modifiers == modifiers) {

                if (s.message.startsWith("/") && ClientCommandHandler.instance.executeCommand(
                        mc.thePlayer,
                        s.message
                    ) != 0
                ) continue
                ChatUtils.userMessage(s.message)
            }
        }
    }

    override fun read(reader: Reader) {
        shortcuts.clear()
        when (val data = json.decodeFromString<kotlinx.serialization.json.JsonElement>(reader.readText())) {
            is kotlinx.serialization.json.JsonArray -> {
                shortcuts.addAll(json.decodeFromJsonElement<List<KeybindShortcut>>(data))
            }

            is kotlinx.serialization.json.JsonObject -> {
                json.decodeFromJsonElement<Map<String, Int>>(data).mapTo(shortcuts) { (cmd, keyCode) ->
                    KeybindShortcut(cmd, keyCode)
                }
            }

            else -> error("Invalid shortcuts file")
        }
    }

    override fun write(writer: Writer) {
        writer.write(json.encodeToString(shortcuts))
    }

    override fun setDefault(writer: Writer) {
        writer.write("[]")
    }

    @Serializable
    data class KeybindShortcut(val message: String, val keyCode: Int, val modifiers: Int = 0) {
        constructor(message: String, keyCode: Int, modifiers: List<Modifiers>) : this(
            message,
            keyCode,
            Modifiers.getBitfield(modifiers)
        )
    }

    enum class Modifiers(val shortName: String, val pressed: () -> Boolean) {
        CONTROL("Ctrl", { UKeyboard.isCtrlKeyDown() }),
        ALT("Alt", { UKeyboard.isAltKeyDown() }),
        SHIFT("Sft", { UKeyboard.isShiftKeyDown() });

        val bitValue by lazy {
            1 shl ordinal
        }

        fun inBitfield(field: Int) = (field and bitValue) == bitValue

        companion object {
            fun getPressed() = entries.filter { it.pressed() }
            fun getBitfield(modifiers: List<Modifiers>): Int {
                var bits = 0
                for (modifier in modifiers) {
                    bits = bits or modifier.bitValue
                }
                return bits
            }

            fun fromBitfield(field: Int) = entries.filter { it.inBitfield(field) }

            fun fromUCraftBitfield(modifiers: UKeyboard.Modifiers) = getBitfield(fromUCraft(modifiers))

            fun fromUCraft(modifiers: UKeyboard.Modifiers) = modifiers.run {
                mutableListOf<Modifiers>().apply {
                    if (isCtrl) add(CONTROL)
                    if (isAlt) add(ALT)
                    if (isShift) add(SHIFT)
                }
            }
        }
    }
}