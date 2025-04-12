package de.kazzutils.data.enumClass

import de.kazzutils.utils.randomutils.TabUtils


enum class DunClass(
    var classNum : Int,
    var playerName : String?,
    var classNameAsString : String
    ) {

    TANK(0,"","Tank"),
    MAGE(1,"","Mage"),
    HEALER(2,"","Healer"),
    BERSERKER(3,"","Berserker"),
    ARCHER(4,"","Archer");


    companion object {
        fun setupName() {
            for(s in DunClass.entries){
                //ChatUtils.messageToChat("Player name: " + TabUtils.getPlayerNameByClass(s.classNameAsString) + " Class: " + s.classNameAsString + " ClassNum: " + s.classNum)
                s.playerName = TabUtils.getPlayerNameByClass(s.classNameAsString) ?: return
            }
        }
    }

}