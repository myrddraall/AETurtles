package com.myrddraall.AETurtles.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cp.mods.core.api.type.old.item.SubtypedItemType;


public enum UpgrageItems implements SubtypedItemType 
{
    DRIVE_READER(181), DRIVE_IO(182), DRIVE_INTERFACE(183);
    
    public final static  String itemName = "AETurtles:UpgradeItems";

    private int itemId = 7000;
    private int upgradeId = 0;
    private Item item;
    private ItemStack stack;
    
    UpgrageItems(int upgrageId){
        this.setUpgradeId(upgrageId);
    }
    @Override
    public int getItemId()
    {
        return itemId;
    }

    @Override
    public void setItemId(int itemId)
    {
       this.itemId = itemId;
    }

    @Override
    public Item getItem()
    {
        return item;
    }

    @Override
    public void setItem(Item item)
    {
        this.item = item;
    }

    @Override
    public ItemStack getItemStack()
    {
        return stack;
    }

    @Override
    public void setItemStack(ItemStack stack)
    {
        this.stack = stack;
    }
    public int getUpgradeId()
    {
        return upgradeId;
    }
    public void setUpgradeId(int upgradeId)
    {
        this.upgradeId = upgradeId;
    }
    @Override
    public String getItemName()
    {
        return itemName + "." + this.name().toLowerCase();
    }
   

}
