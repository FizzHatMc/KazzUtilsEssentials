package de.kazzutils.utils.skyblockfeatures

import de.kazzutils.KazzUtils
import de.kazzutils.utils.randomutils.ContainerUtils
import de.kazzutils.utils.randomutils.DelayedExecutor
import de.kazzutils.utils.randomutils.JsonUtils
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.io.File


object MuseumUtils{
    var currentInv = "empty"
    val path = File(KazzUtils.modDir, "/trackers/museum.json")


    @SubscribeEvent
    fun onOpenGui(event: GuiOpenEvent) {
        if(KazzUtils.mc.thePlayer == null) return
        if(event.gui!=null) {
            DelayedExecutor.runDelayed(100) {
                updateInventory()
            }
        }
    }

    private fun updateInventory() {
        val currentScreen = ContainerUtils.openInventoryName()
        when(currentScreen){
            "Your Museum" -> currentInv = "MuseumMain"
            "Museum ➜ Weapons" -> currentInv = "Weapons"
            "Museum ➜ Armor Sets" -> currentInv = "ArmorSets"
            "Museum ➜ Rarities" -> currentInv = "Rarities"
            "Museum ➜ Special Items" -> currentInv = "Items"
            else -> currentInv = "empty"
        }
        getMuseumItems()
    }

    private var missingItems: MutableMap<String, Int> = mutableMapOf()
    private var collectedItems: MutableMap<String, Int> = mutableMapOf()

    //§c



    private fun getMuseumItems(){
        val items = ContainerUtils.getItemsInOpenChest()
        if(currentInv == "empty") return

        for(i in 10..43){
            val item : ItemStack = ContainerUtils.getItemStackFromSlot(items,i) ?: return
            val itemName = ContainerUtils.getDisplayName(item)

            if(itemName == "[ ]") continue

            val clearName = itemName.substring(startIndex = 3).replace("]","").trim()

            if(itemName.contains("§c") && itemName != "Close") {
                missingItems[clearName] = i
            }else if(itemName != "Close"){
                collectedItems[clearName] = i
            }

//            missingItems.forEach { (t, u) -> ChatUtils.messageToChat("$t $u") }
            JsonUtils.saveMapToFile(path.path, "missingItems", missingItems)
            //JsonUtils.saveMapToFile(path.path, "collectedItems", collectedItems)  //Not sure if i need it but i have it lol


        }
    }

    fun loadData(){
        missingItems = JsonUtils.loadMapFromFile(path.path,"missingItems")
        //collectedItems = JsonUtils.loadMapFromFile(path.path,"collectedItems")
    }

    fun getMissingItems() : MutableMap<String, Int>{return missingItems}
    fun getCollectedItems() : MutableMap<String, Int>{return collectedItems}
}