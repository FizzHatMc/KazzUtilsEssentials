package de.kazzutils.transformers;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiStreamUnavailable.class)
public interface AccessorGuiStreamUnavailable {
    @Accessor
    GuiScreen getParentScreen();
}