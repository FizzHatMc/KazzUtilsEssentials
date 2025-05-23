package de.kazzutils.mixin;

import de.kazzutils.event.CheckRenderEntityEvent;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public class MixinRenderManager {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void shouldRender(Entity entity, ICamera camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
        if (new CheckRenderEntityEvent<>(entity, camera, camX, camY, camZ).postAndCatch()) {
            cir.setReturnValue(false);
        }else{
            System.out.println("Failed to check render entity");
        }
    }
}
