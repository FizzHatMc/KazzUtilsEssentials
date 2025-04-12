package de.kazzutils.handler

object EventHandler {

    /**
     * In diesem EventHandler kann ich Mittels offizielen Events Custom events Triggern.
     * Beispiel:
     * Offizieles ItemTossEvent (Triggert immer wenn ein Item gedroppt wird egal in welcher Form) wenn versucht wird Diamond zu droppen Triggert es alle ItemDropEvents
     *
     *
     * @SubscribeEvent(priority = EventPriority.HIGHEST)
     *     fun onItemDrop(event: ItemTossEvent) {
     *         val player = event.player
     *         val item = event.entityItem.entityItem
     *         if(item.displayName.lowercase().contains("diamond")) {
     *             ChatUtils.messageToChat("ItemTossEvent Diamond")
     *
     *             ItemDropEvent(player, item).postAndCatch()
     *         }else ChatUtils.messageToChat("No Diamond")
     *     }
     *
     *
     *
    */

//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    fun onItemDrop(event: ItemTossEvent) {
//
//
//        val protectedItemNames = MuseumUtils.getMissingItems().keys.toList()
//
//        protectedItemNames.forEach { name ->
//            if(itemName.contains(name.lowercase())){
//                event.isCanceled = true
//                ItemDropEvent(player,item).postAndCatch()
//            }
//        }
//
//
//    }



}