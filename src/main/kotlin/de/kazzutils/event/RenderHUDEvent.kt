package de.kazzutils.event


import net.minecraftforge.client.event.RenderGameOverlayEvent

data class RenderHUDEvent(val event: RenderGameOverlayEvent) : KazzUtilsEvent()