package de.kazzutils.transformers;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.gui.SettingsGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SettingsGui.class, remap = false)
public interface AccessorSettingsGui {
    @Accessor
    Vigilant getConfig();
}
