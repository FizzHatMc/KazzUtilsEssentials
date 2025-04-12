package de.kazzutils.event

import de.kazzutils.KazzUtils
import gg.essential.universal.UChat
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.Event


abstract class KazzUtilsEvent : Event() {
    val eventName by lazy {
        this::class.simpleName
    }

    fun postAndCatch(): Boolean {
        return runCatching {
            MinecraftForge.EVENT_BUS.post(this)
        }.onFailure {
            it.printStackTrace()
            UChat.chat("§cKazzUtilsV2 ${KazzUtils.version} caught and logged an ${it::class.simpleName ?: "error"} at ${eventName}.")
        }.getOrDefault(isCanceled)
    }
}