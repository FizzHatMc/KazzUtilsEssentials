package de.kazzutils.event

import net.minecraft.world.World

class OnWorldChangeEvent(val oldWorld: World?, val newWorld: World) : KazzUtilsEvent()