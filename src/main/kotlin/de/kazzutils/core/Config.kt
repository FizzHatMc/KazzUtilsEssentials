package de.kazzutils.core

import de.kazzutils.KazzUtils
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.SortingBehavior
import java.io.File
import gg.essential.vigilance.data.Category
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import java.awt.Color

object Config : Vigilant(
    File("./config/KazzUtils/config.toml"),
    "KazzUtils (Essentials Edition)" + " (${KazzUtils.version})",
    sortingBehavior = ConfigSorting
) {

    init {
        initialize()
        // First Field = Option that is Hidden
        // Second Field = Condition Field
        // Not option Names but Variable Names
        // If second field then First is shown
        //addDependency("dependent", "dependency")

        //Animation dependencies
        addDependency("customSize", "customAnimations")
        addDependency("doesScaleSwing", "customAnimations")
        addDependency("customX", "customAnimations")
        addDependency("customY", "customAnimations")
        addDependency("customZ", "customAnimations")
        addDependency("customYaw", "customAnimations")
        addDependency("customPitch", "customAnimations")
        addDependency("customRoll", "customAnimations")
        addDependency("customSpeed", "customAnimations")
        addDependency("ignoreHaste", "customAnimations")
        addDependency("drinkingSelector", "customAnimations")

        addDependency("partyPrefix", "partyCommands")
        addDependency("downtime", "partyCommands")

        addDependency("gyroColor", "gyroRange")
        addDependency("gyroSize", "gyroRange")
        addDependency("gyroPhased", "gyroRange")
        addDependency("gyroFilled", "gyroRange")



    }

    @Property(
        type = PropertyType.SWITCH,
        name = "Protect Items",
        description = "Protects Items, add Item with /protectitem",
        category = "Miscellaneous",
        subcategory = "Item"
    )
    var protectItems = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Reopen Options Menu",
        description = "Sets the menu to the KazzUtils options menu instead of exiting when on a KazzUtils config menu.",
        category = "General",
        subcategory = "Other"

    )
    var reopenOptionsMenu = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Config Button on Pause",
        description = "Adds a button to configure KazzUtils to the pause menu.",
        category = "General",
        subcategory = "Other"

    )
    var configButtonOnPause = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Global Toggle",
        description = "Change the look of your held item",
        category = "Miscellaneous",
        subcategory = "Animation"
    )
    var customAnimations = false


    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Size",
        description = "Scales the size of your currently held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customSize = 0f

    @Property(
        type = PropertyType.SWITCH,
        name = "Scale Swing",
        description = "Also scale the size of the swing animation.",
        category = "Miscellaneous",
        subcategory = "Animation"
    )
    var doesScaleSwing = true

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "X",
        description = "Moves the held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customX = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Y",
        description = "Moves the held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customY = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Z",
        description = "Moves the held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customZ = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Yaw",
        description = "Rotates your held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -180f,
        maxF = 180f
    )
    var customYaw = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Pitch",
        description = "Rotates your held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -180f,
        maxF = 180f
    )
    var customPitch = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Roll",
        description = "Rotates your held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -180f,
        maxF = 180f
    )
    var customRoll = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Speed",
        description = "Speed of the swing animation.",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -2f,
        maxF = 1f
    )
    var customSpeed = 0f

    @Property(
        type = PropertyType.SWITCH,
        name = "Ignore Haste",
        description = "Makes the chosen speed override haste modifiers.",
        category = "Miscellaneous",
        subcategory = "Animation"
    )
    var ignoreHaste = true

    @Property(
        type = PropertyType.SLIDER,
        name = "Drinking Fix",
        description = "Pick how to handle drinking animations.",
        category = "Miscellaneous",
        subcategory = "Animation",
        min = 0,
        max = 2
    )
    var drinkingSelector = 2

    @Property(
        type = PropertyType.SWITCH,
        name = "Display Gyro area",
        description = "Displays a Circle where the Gyro will trigger.",
        category = "Miscellaneous",
        subcategory = "Item"
    )
    var gyroRange = false

    @Property(
        type = PropertyType.COLOR,
        name = "Gyro area Color",
        description = "Changes the Color of the Gyro Range.",
        category = "Miscellaneous",
        subcategory = "Item"
    )
    var gyroColor = Color(0,0,0,0)

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Gyro area Size",
        description = "Changes the size of the Gyro Range.",
        category = "Miscellaneous",
        subcategory = "Item",
        minF = 0f,
        maxF = 10f
    )
    var gyroSize = 1F

    @Property(
        type = PropertyType.SWITCH,
        name = "Gyro range filled",
        description = "Fills the Gyro Range",
        category = "Miscellaneous",
        subcategory = "Item"
    )
    var gyroFilled = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Gyro range Phase",
        description = "Gyro range phase through Walls / TEST",
        category = "Miscellaneous",
        subcategory = "Item"
    )
    var gyroPhased = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Enable Fruit Digging helper",
        description = "Helps with Fruit Digging",
        category = "Event",
        subcategory = "Carnival"
    )
    var minesweeper = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Stop Enderman Fake teleportation",
        description = "Stops Enderman from fake teleportation.",
        category = "Miscellaneous",
        subcategory = "misc"
    )
    var stopEndermanFakeTeleport = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Enable Party Commands",
        description = "Enable Party Commands",
        category = "Chat Features",
        subcategory = "Party Commands"
    )
    var partyCommands = false


    @Property(
        type = PropertyType.SELECTOR,
        name = "Party commands Prefix",
        description = "Choose what Prefix Party commands should use",
        category = "Chat Features",
        subcategory = "Party Commands",
        options= ["!",".","?",",","-"]
    )
    var partyPrefix = 1

    @Property(
        type = PropertyType.SWITCH,
        name = "Downtime Display",
        description = "Displays if a user needs Downtime, USAGE: {prefix}dt",
        category = "Chat Features",
        subcategory = "Party Commands"
    )
    var downtime = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Enable Chat Emotes",
        description = "Enables MVP++ Emotes for People with MVP++.",
        category = "Chat Features",
        subcategory = "General"
    )
    var chatEmotes = false







}


private object ConfigSorting : SortingBehavior() {
    override fun getCategoryComparator(): Comparator<in Category> = Comparator { o1, o2 ->
        if (o1.name == "General") return@Comparator -1
        if (o2.name == "General") return@Comparator 1
        else compareValuesBy(o1, o2) {
            it.name
        }
    }
}