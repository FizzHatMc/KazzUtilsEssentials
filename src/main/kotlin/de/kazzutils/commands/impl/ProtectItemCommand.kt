package de.kazzutils.commands.impl

import de.kazzutils.commands.BaseCommand
import de.kazzutils.core.PersistentSave
import de.kazzutils.data.protect.impl.FavoriteStrategy
import de.kazzutils.utils.Utils
import de.kazzutils.utils.skyblockfeatures.ItemUtil
import gg.essential.universal.UChat
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.command.WrongUsageException

object ProtectItemCommand : BaseCommand("protect") {
    override fun getCommandUsage(player: EntityPlayerSP): String = "/protect <clearall>"

    override fun processCommand(player: EntityPlayerSP, args: Array<String>) {
        val subcommand = args.getOrNull(0)?.lowercase()
        if (subcommand == "clearall") {
            FavoriteStrategy.favoriteUUIDs.clear()
            FavoriteStrategy.favoriteItemIds.clear()
            PersistentSave.markDirty<FavoriteStrategy.FavoriteStrategySave>()
            UChat.chat("§aCleared all your protected items!")
            return
        }
        if (!Utils.inSkyblock) throw WrongUsageException("You must be in Skyblock to use this command!")
        val item = player.heldItem
            ?: throw WrongUsageException("You must hold an item to use this command")
        val extraAttributes = ItemUtil.getExtraAttributes(item)
            ?: throw WrongUsageException("This isn't a Skyblock Item? Where'd you get it from cheater...")
        if (extraAttributes.hasKey("uuid") && subcommand != "itemid") {
            val uuid = extraAttributes.getString("uuid")
            if (FavoriteStrategy.favoriteUUIDs.remove(uuid)) {
                PersistentSave.markDirty<FavoriteStrategy.FavoriteStrategySave>()
                UChat.chat("§cI will no longer protect your ${item.displayName}§a!")
            } else {
                FavoriteStrategy.favoriteUUIDs.add(uuid)
                PersistentSave.markDirty<FavoriteStrategy.FavoriteStrategySave>()
                UChat.chat("§aI will now protect your ${item.displayName}! Kazz")
            }
        } else {
            val itemId =
                ItemUtil.getSkyBlockItemID(item) ?: throw WrongUsageException("This item doesn't have a Skyblock ID.")
            if (FavoriteStrategy.favoriteItemIds.remove(itemId)) {
                PersistentSave.markDirty<FavoriteStrategy.FavoriteStrategySave>()
                UChat.chat("§cI will no longer protect all of your ${itemId}s!")
            } else {
                FavoriteStrategy.favoriteItemIds.add(itemId)
                PersistentSave.markDirty<FavoriteStrategy.FavoriteStrategySave>()
                UChat.chat("§aI will now protect all of your ${itemId}s!")
            }
        }
    }
}