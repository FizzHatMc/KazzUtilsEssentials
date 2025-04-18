package de.kazzutils.features.dungeons

class DungeonFeatures {

    var dungeonFloor: String? = null
        set(value) {
            field = value
            dungeonFloorNumber = value?.drop(1)?.ifEmpty { "0" }?.toIntOrNull()
        }
    var dungeonFloorNumber: Int? = null
        private set

}