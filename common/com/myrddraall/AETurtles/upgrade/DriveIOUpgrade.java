package com.myrddraall.AETurtles.upgrade;

import com.myrddraall.AETurtles.api.item.UpgrageItems;

import net.minecraft.item.ItemStack;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.TurtleSide;

public class DriveIOUpgrade extends DriveUpgradeBase
{
    public DriveIOUpgrade()
    {
        super();
        typeOffset = 2;
    }

    @Override
    public IHostedPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side)
    {
        return null;//new DriveIOPeripheral(turtle);
    }

    @Override
    public String getAdjective()
    {
        return "DriveIO";
    }

    @Override
    public ItemStack getCraftingItem()
    {
        return null;//AETurtleUpgradeItem.UpgrageItems.DriveIO.stack;
    }

    @Override
    public int getUpgradeID()
    {
        return UpgrageItems.DRIVE_IO.getUpgradeId();
    }

   
}
