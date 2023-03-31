package com.moltenwolfcub.crafted_cuisine.mixin;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockSetType.class)
public interface BlockSetTypeAccessor {

    @Invoker("register")
    static BlockSetType registerNew(BlockSetType type) {
        throw new AssertionError();
    }
}
