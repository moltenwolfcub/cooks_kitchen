package com.moltenwolfcub.crafted_cuisine.screen;

import com.moltenwolfcub.crafted_cuisine.event.ModEventBusEvents;
import com.moltenwolfcub.crafted_cuisine.init.AllBlocks;
import com.moltenwolfcub.crafted_cuisine.init.AllMenuTypes;
import com.moltenwolfcub.crafted_cuisine.screen.slot.FuelSlot;
import com.moltenwolfcub.crafted_cuisine.screen.slot.IngredientSlot;
import com.moltenwolfcub.crafted_cuisine.screen.slot.ResultSlot;
import com.moltenwolfcub.crafted_cuisine.screen.slot.WaterSlot;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class CarameliserMenu extends ModAbstractContainerMenu {

    public CarameliserMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(
            containerId,
            inv,
            new SimpleContainer(6),
            new SimpleContainerData(6),
            ContainerLevelAccess.NULL
        );
    }

    public CarameliserMenu(int containerId, Inventory inv, Container container, ContainerData data, ContainerLevelAccess access) {
        super(AllMenuTypes.CARAMELISER.get(), containerId, AllBlocks.CARAMELISER.get(), data, access);
        checkContainerSize(inv, 6);

        this.addSlot(new WaterSlot(container, 0, 8, 53));
        this.addSlot(new IngredientSlot(container, 1, 32, 9));
        this.addSlot(new IngredientSlot(container, 2, 32, 31));
        this.addSlot(new IngredientSlot(container, 3, 32, 53));
        this.addSlot(new FuelSlot(this, container, 4, 80, 61));
        this.addSlot(new ResultSlot(container, 5, 134, 33));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);
    }


    public boolean isCrafting() {
        return isCrafting(0);
    }

    public int getScaledProgress() {
        return getScaledData(0, 1, 44);
    }

    public int getScaledMilibuckets() {
        return getScaledData(2, 3, 40);
    }

    public int getScaledFuel() {
        return getScaledData(4, 5, 14);
    }


    public boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, ModEventBusEvents.CARAMELISER_RECIPE) > 0;
    } 

    public boolean isWater(ItemStack stack) {
        return stack.is(Items.WATER_BUCKET) || PotionUtils.getPotion(stack) == Potions.WATER;
    }


    @Override
    public ItemStack quickMoveStack(Player player, int slotClickedId) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slotClicked = this.slots.get(slotClickedId);

        if (slotClicked != null && slotClicked.hasItem()) {
            ItemStack slotClickedStack = slotClicked.getItem();
            itemstack = slotClickedStack.copy();
            if (slotClickedId == 5) {
                if (!this.moveItemStackTo(slotClickedStack, 6, 42, true)) {
                    return ItemStack.EMPTY;
                }
  
                slotClicked.onQuickCraft(slotClickedStack, itemstack);
            } else if (slotClickedId > 5) {
                if (this.isWater(slotClickedStack)) {
                    if (!this.moveItemStackTo(slotClickedStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(slotClickedStack)) {
                    if (!this.moveItemStackTo(slotClickedStack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(slotClickedStack, 1, 4, false)) {
                    if (slotClickedId >= 6 && slotClickedId < 33) {
                        if (!this.moveItemStackTo(slotClickedStack, 33, 42, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotClickedId >= 33 && slotClickedId < 42) {
                        if (!this.moveItemStackTo(slotClickedStack, 6, 33, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.moveItemStackTo(slotClickedStack, 6, 42, false)) {
                return ItemStack.EMPTY;
            }
  
            if (slotClickedStack.isEmpty()) {
                slotClicked.set(ItemStack.EMPTY);
            } else {
                slotClicked.setChanged();
            }
    
            if (slotClickedStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
  
            slotClicked.onTake(player, slotClickedStack);
        }
  
        return itemstack;
    }
}
