package com.myrddraall.AETurtles.upgrade;

import net.minecraft.util.Icon;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtleUpgrade;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.api.TurtleUpgradeType;
import dan200.turtle.api.TurtleVerb;

public abstract class DriveUpgradeBase implements ITurtleUpgrade
{
    protected int typeOffset = 0;
    public DriveUpgradeBase()
    {
    }
   
    @Override
    public TurtleUpgradeType getType()
    {
        return TurtleUpgradeType.Peripheral;
    }
    @Override
    public boolean isSecret()
    {
        return false;
    }
   
    @Override
    public boolean useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction)
    {
        return false;
    }
    @Override
    public Icon getIcon(ITurtleAccess turtle, TurtleSide side)
    {
        return null;
    }
  
    
    
//    
//    
//    @Override
//    public int getIconIndex(ITurtleAccess turtle, TurtleSide side)
//    {
//        if (turtle != null && side != null)
//        {
//            IHostedPeripheral periph = turtle.getPeripheral(side);
//            DrivePeripheralBase p = null;
//
//            if (periph instanceof DrivePeripheralBase)
//            {
//                p = (DrivePeripheralBase)periph;
//            }
//
//            if (p != null)
//            {
//                double percent = p.percentFull;
//                int percentOffset = new Double(percent * 10).intValue();
//
//                if (percent == 1.0)
//                {
//                    percentOffset = 11;
//                }
//
//                int reading = p.getState().ordinal();
//                return typeOffset * 16 + reading * 16 + percentOffset;
//            }
//        }
//
//        return 0;
//    }
//
//    @Override
//    public String getIconTexture(ITurtleAccess arg0, TurtleSide arg1)
//    {
//        return "/erd/mods/AETurtle/gfx/aeturtle.png";
//    }
//
//    @Override
//    public TurtleUpgradeType getType()
//    {
//        return TurtleUpgradeType.Peripheral;
//    }
//
//    @Override
//    public boolean isSecret()
//    {
//        return false;
//    }
//
//    @Override
//    public boolean useTool(ITurtleAccess arg0, TurtleSide arg1, TurtleVerb arg2, int arg3)
//    {
//        return false;
//    }
}
