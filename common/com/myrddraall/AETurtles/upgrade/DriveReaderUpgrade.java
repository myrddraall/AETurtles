package com.myrddraall.AETurtles.upgrade;

import com.myrddraall.AETurtles.api.item.UpgrageItems;

import net.minecraft.item.ItemStack;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.TurtleSide;

public class DriveReaderUpgrade extends DriveUpgradeBase
{
    public DriveReaderUpgrade()
    {
        super();
    }

    @Override
    public IHostedPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side)
    {
        return null;//new DriveReaderPeripheral(turtle);
    }

    @Override
    public String getAdjective()
    {
        return "DriveReader";
    }

    @Override
    public ItemStack getCraftingItem()
    {
        return null;//  AETurtleUpgradeItem.UpgrageItems.DriveReader.stack;
    }

    @Override
    public int getUpgradeID()
    {
        return UpgrageItems.DRIVE_READER.getUpgradeId();
    }

  
}
