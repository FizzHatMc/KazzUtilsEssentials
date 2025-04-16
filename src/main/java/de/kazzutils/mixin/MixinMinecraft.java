package de.kazzutils.mixin;

import com.google.common.util.concurrent.ListenableFuture;
import de.kazzutils.utils.Utils;
import de.kazzutils.utils.skyblockfeatures.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executor;


@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements Executor {
    @Shadow
    public EntityPlayerSP thePlayer;

    @Shadow
    public abstract ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule);

    @Inject(method = "clickMouse()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;swingItem()V", shift = At.Shift.AFTER))
    private void clickMouse(CallbackInfo info) {
        if (!Utils.INSTANCE.getInSkyblock()) return;

        ItemStack item = this.thePlayer.getHeldItem();
        if (item != null) {
            NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(item);
            String itemId = ItemUtil.getSkyBlockItemID(extraAttr);


        }
    }

    @Override
    public void execute(@NotNull Runnable command) {
        this.addScheduledTask(command);
    }
}