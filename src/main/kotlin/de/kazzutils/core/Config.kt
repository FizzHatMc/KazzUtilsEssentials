package de.kazzutils.core

import de.kazzutils.KazzUtils
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.SortingBehavior
import java.io.File
import gg.essential.vigilance.data.Category
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType

object Config : Vigilant(
    File("./config/KazzUtils/config.toml"),
    "KazzUtils (Essentials Edition)" + " (${KazzUtils.version})",
    sortingBehavior = ConfigSorting
) {

    @Property(
        type = PropertyType.SWITCH,
        name = "Useless Switch",
        description = "Test",
        category = "General",
        subcategory = "Test"
    )
    var test = true

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
    var customAnimations = false;


    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Size",
        description = "Scales the size of your currently held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customSize = 0f;

    @Property(
        type = PropertyType.SWITCH,
        name = "Scale Swing",
        description = "Also scale the size of the swing animation.",
        category = "Miscellaneous",
        subcategory = "Animation"
    )
    var doesScaleSwing = true;

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "X",
        description = "Moves the held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customX = 0f;

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Y",
        description = "Moves the held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customY = 0f;

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Z",
        description = "Moves the held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -1.5f,
        maxF = 1.5f
    )
    var customZ = 0f;

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Yaw",
        description = "Rotates your held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -180f,
        maxF = 180f
    )
    var customYaw = 0f;

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Pitch",
        description = "Rotates your held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -180f,
        maxF = 180f
    )
    var customPitch = 0f;

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Roll",
        description = "Rotates your held item. Default: 0",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -180f,
        maxF = 180f
    )
    var customRoll = 0f;

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Speed",
        description = "Speed of the swing animation.",
        category = "Miscellaneous",
        subcategory = "Animation",
        minF = -2f,
        maxF = 1f
    )
    var customSpeed = 0f;

    @Property(
        type = PropertyType.SWITCH,
        name = "Ignore Haste",
        description = "Makes the chosen speed override haste modifiers.",
        category = "Miscellaneous",
        subcategory = "Animation"
    )
    var ignoreHaste = true;

    @Property(
        type = PropertyType.SLIDER,
        name = "Drinking Fix",
        description = "Pick how to handle drinking animations.",
        category = "Miscellaneous",
        subcategory = "Animation",
        min = 0,
        max = 2
    )
    var drinkingSelector = 2;



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