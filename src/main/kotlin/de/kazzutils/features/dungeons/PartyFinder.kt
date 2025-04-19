package de.kazzutils.features.dungeons

object PartyFinder {
    private val partyFinderRegex = Regex(
        "^Party Finder > (?<name>\\w+) joined the dungeon group! \\((?<class>Archer|Berserk|Mage|Healer|Tank) Level (?<classLevel>\\d+)\\)$"
    )

}