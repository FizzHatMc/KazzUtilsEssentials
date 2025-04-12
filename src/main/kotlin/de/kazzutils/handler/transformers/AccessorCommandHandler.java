package de.kazzutils.handler.transformers;

import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

@Mixin(CommandHandler.class)
public interface AccessorCommandHandler {
    @Accessor
    Set<ICommand> getCommandSet();

//    @Mutable
    @Accessor
    void setCommandSet(Set<ICommand> set);

    @Accessor
    Map<String, ICommand> getCommandMap();

//    @Mutable
    @Accessor
    void setCommandMap(Map<String, ICommand> map);

}