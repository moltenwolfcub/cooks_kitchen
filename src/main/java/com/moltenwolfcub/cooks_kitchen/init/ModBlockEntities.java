package com.moltenwolfcub.cooks_kitchen.init;

import com.moltenwolfcub.cooks_kitchen.CooksKitchen;
import com.moltenwolfcub.cooks_kitchen.blocks.entity.AutoBlowTorchBlockEntity;
import com.moltenwolfcub.cooks_kitchen.blocks.entity.CarameliserBlockEntity;
import com.moltenwolfcub.cooks_kitchen.blocks.entity.ModSignBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CooksKitchen.MODID);
    
    public static final RegistryObject<BlockEntityType<AutoBlowTorchBlockEntity>> AUTO_BLOWTORCH_BLOCK_ENTITY = 
        BLOCK_ENTITIES.register("auto_blowtorch_block_entity",  () -> BlockEntityType.Builder.of(
            AutoBlowTorchBlockEntity::new, ModBlocks.AUTO_BLOWTORCH.get()
        ).build(null)
    );

    public static final RegistryObject<BlockEntityType<CarameliserBlockEntity>> CARAMELISER_BLOCK_ENTITY = 
        BLOCK_ENTITIES.register("carameliser_block_entity",  () -> BlockEntityType.Builder.of(
            CarameliserBlockEntity::new, ModBlocks.CARAMELISER.get()
        ).build(null)
    );


    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN_BLOCK_ENTITIES = 
        BLOCK_ENTITIES.register("sign_block_entity",  () -> BlockEntityType.Builder.of(
            ModSignBlockEntity::new, 
            ModBlocks.CINNAMON_WALL_SIGN.get(),
            ModBlocks.CINNAMON_SIGN.get()
        ).build(null)
    );
}
