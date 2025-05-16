package de.kazzutils.features.dungeons

import de.kazzutils.KazzUtils
import de.kazzutils.utils.skyblockfeatures.CatacombsUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class DungeonFeatures {

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent){
        if (event.type.toInt() == 2) return
        CatacombsUtils.handleBossMessage(event.message.unformattedText)

    }
}