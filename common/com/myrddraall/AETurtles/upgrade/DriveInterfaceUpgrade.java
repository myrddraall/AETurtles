package com.myrddraall.AETurtles.upgrade;

import com.myrddraall.AETurtles.api.item.UpgrageItems;

import net.minecraft.item.ItemStack;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.TurtleSide;

public class DriveInterfaceUpgrade extends DriveUpgradeBase
{
    public DriveInterfaceUpgrade()
    {
        super();
        typeOffset = 5;
    }

    @Override
    public IHostedPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side)
    {
        return null;//new DriveInterfacePeripheral(turtle);
    }

    @Override
    public String getAdjective()
    {
        return "DriveInterface";
    }

    @Override
    public ItemStack getCraftingItem()
    {
        return null;//AETurtleUpgradeItem.UpgrageItems.DriveInterface.stack;
    }

    @Override
    public int getUpgradeID()
    {
        return UpgrageItems.DRIVE_INTERFACE.getUpgradeId();
    }

}
