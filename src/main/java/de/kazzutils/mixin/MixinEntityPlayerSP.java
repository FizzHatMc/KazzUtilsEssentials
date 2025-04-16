package de.kazzutils.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import de.kazzutils.handler.hook.EntityPlayerSPHookKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
    @Shadow
    protected Minecraft mc;

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(method = "addChatMessage", at = @At("HEAD"), cancellable = true)
    private void onAddChatMessage(IChatComponent message, CallbackInfo ci) {
        EntityPlayerSPHookKt.onAddChatMessage(message, ci);
    }

    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    private void onDropItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        EntityPlayerSPHookKt.onDropItem(dropAll, cir);
    }

    @WrapOperation(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    private boolean setSprintState(KeyBinding keyBinding, Operation<Boolean> original) {
        return EntityPlayerSPHookKt.onKeybindCheck(keyBinding) || original.call(keyBinding);
    }
}