package de.kazzutils.data

import kotlinx.serialization.Serializable

@Serializable
data class PetsData(
    val autopet: AutopetData = AutopetData(),
    val pets: List<Pet> = emptyList()
)

@Serializable
data class AutopetData(
    val migrated: Boolean = false
)

@Serializable
data class Pet(
    // Seems to be null when the pet is:
    // a reward (rock, skeleton horse)
    // a fishing drop (megalodon, flying fish)
    // a mithril golem
    // a tarantula
    // a spirit pet
    // a rat
    // from a game system (bingo, grandma wolf)
    // not convertible into an item (kuudra)
    val uuid: String? = null,
    // Seems to not exist when on an ironman type profile (ironman, bingo)
    val uniqueId: String? = null,
    val type: String,
    val exp: Double = 0.0,
    val active: Boolean = false,
    val tier: String,
    val heldItem: String? = null,
    val candyUsed: Int = 0,
    val skin: String? = null,
    val hideInfo: Boolean = false,
)