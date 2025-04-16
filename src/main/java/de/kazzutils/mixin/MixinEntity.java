package de.kazzutils.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;


// For MixinEntityLivingBase to extend
@Mixin(value = {Entity.class}, priority = 800)
public abstract class MixinEntity {
//    @Shadow
    public abstract boolean equals(Object paramObject);
}
