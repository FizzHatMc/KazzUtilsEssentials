package de.kazzutils.handler.hook

import de.kazzutils.event.WorldChangeEvent
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class RenderLivingEntityHelper{

    @SubscribeEvent
    fun onWorldChange(event: WorldChangeEvent) {
        entityColorMap.clear()
        entityColorCondition.clear()
        entityNoHurTimeCondition.clear()
    }

    companion object {

        private val entityColorMap = mutableMapOf<EntityLivingBase, Int>()
        private val entityColorCondition = mutableMapOf<EntityLivingBase, () -> Boolean>()

        private val entityNoHurTimeCondition = mutableMapOf<EntityLivingBase, () -> Boolean>()

        fun <T : EntityLivingBase> removeEntityColor(entity: T) {
            entityColorMap.remove(entity)
            entityColorCondition.remove(entity)
        }

        fun <T : EntityLivingBase> setEntityColor(entity: T, color: Int, condition: () -> Boolean) {
            entityColorMap[entity] = color
            entityColorCondition[entity] = condition
        }

        fun <T : EntityLivingBase> setNoHurtTime(entity: T, condition: () -> Boolean) {
            entityNoHurTimeCondition[entity] = condition
        }

        fun <T : EntityLivingBase> setEntityColorWithNoHurtTime(entity: T, color: Int, condition: () -> Boolean) {
            setEntityColor(entity, color, condition)
            setNoHurtTime(entity, condition)
        }

        fun <T : EntityLivingBase> removeNoHurtTime(entity: T) {
            entityNoHurTimeCondition.remove(entity)
        }

        fun <T : EntityLivingBase> removeCustomRender(entity: T) {
            removeEntityColor(entity)
            removeNoHurtTime(entity)
        }

        @JvmStatic
        fun <T : EntityLivingBase> internalSetColorMultiplier(entity: T): Int {
            if (entityColorMap.containsKey(entity)) {
                val condition = entityColorCondition[entity]!!
                if (condition.invoke()) {
                    return entityColorMap[entity]!!
                }
            }
            return 0
        }


        @JvmStatic
        fun <T : EntityLivingBase> internalChangeHurtTime(entity: T): Int {
            run {
                val condition = entityNoHurTimeCondition[entity] ?: return@run
                if (condition.invoke()) {
                    return 0
                }
            }
            return entity.hurtTime
        }
    }

}