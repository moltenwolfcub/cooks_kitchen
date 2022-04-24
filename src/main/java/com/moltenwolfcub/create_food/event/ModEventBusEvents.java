package com.moltenwolfcub.create_food.event;

import javax.annotation.Nonnull;

import com.moltenwolfcub.create_food.CreateFood;
import com.moltenwolfcub.create_food.event.loot.GenericStructureAdditionModifier;
import com.moltenwolfcub.create_food.recipe.AutoBlowTorchRecipe;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateFood.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        
        event.getRegistry().registerAll(
            new GenericStructureAdditionModifier.Serializer().setRegistryName(new ResourceLocation(
                CreateFood.MODID,"generic_structure"))
        );
    }

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, AutoBlowTorchRecipe.Type.ID, AutoBlowTorchRecipe.Type.INSTANCE);
    }
}
