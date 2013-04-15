package com.myrddraall.AETurtles.peripherals;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import appeng.api.me.util.IMEInventory;
import cp.mods.core.integration.computercraft.util.LuaUtil;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class DriveInterfacePeripheral extends DriveIOPeripheral
{
    private final static String[] luaMethods = { "export", "import" };

    public DriveInterfacePeripheral(ITurtleAccess turtle)
    {
        super(turtle);
    }

    @Override
    protected void initLuaMethods()
    {
        collectedLuaMethods.addAll(Arrays.asList(luaMethods));
        super.initLuaMethods();
    }

    @Override
    public Object[] callMethod(IComputerAccess cpu, int methodId, Object[] args)
    throws Exception
    {
        Object[] returnVals = null;

        switch (methodId)
        {
            case 0:
                setState(AEDriveState.EXPORT);
                returnVals = exportItems(args);
                break;

            case 1:
                setState(AEDriveState.IMPORT);
                returnVals = importItems(args);
                break;

            default:
                returnVals = super.callMethod(cpu, methodId - luaMethods.length,
                        args);
                break;
        }

        return returnVals;
    }

    public Object[] exportItems(Object[] args)
    {
        String item = LuaUtil.toString(args[0]);
        Integer amount = LuaUtil.toInteger(args[1]);
        Integer sourceSlot = turtle.getSelectedSlot();
        Object[] r = { exportItems(item, amount, sourceSlot) };
        return r;
    }

    public Integer exportItems(String itemId, Integer amount, Integer sourceSlot)
    {
        if (amount > 0)
        {
            IMEInventory source = getInventory(sourceSlot);

            if (source != null)
            {
                ItemStack item = findItem(source, itemId);
                return exportItems(item, amount, source);
            }
        }

        return 0;
    }

    public Integer exportItems(ItemStack item, Integer amount,
            IMEInventory source)
    {
        Integer exported = 0;
//        Integer grab = Math.min(item.stackSize,
//                Math.min(item.getItem().getItemStackLimit(), amount));
//
//        if (grab <= item.stackSize)
//        {
//            item.stackSize = grab;
//            ItemStack get = item.copy();
//
//            if (!turtle.storeItemStack(get))
//            {
//                item.stackSize -= get.stackSize;
//            }
//
//            exported = item.stackSize;
//
//            if (item.stackSize > 0)
//            {
//                source.extractItems(item);
//            }
//        }

        return exported;
    }

    public Object[] importItems(Object[] args)
    {
        Integer amount = LuaUtil.toInteger(args[0]);
        Integer destSlot = LuaUtil.toSlot(args[1]);
        Object[] r = { importItems(amount, destSlot) };
        return r;
    }

    public Integer importItems(Integer amount, Integer destSlot)
    {
        if (amount > 0)
        {
            IMEInventory dest = getInventory(destSlot);

            if (dest != null)
            {
                // ItemStack item = findItem(source, itemId);
                ItemStack item = turtle.getSlotContents(turtle
                        .getSelectedSlot());

                if (item != null)
                {
                    return importItems(item, amount, dest);
                }
            }
        }

        return 0;
    }

    public Integer importItems(ItemStack item, Integer amount, IMEInventory dest)
    {
        ItemStack request = item.copy();
        Integer imported = 0;
//        Integer grab = imported = request.stackSize = Math.min(item.stackSize,
//                amount);
//        request = dest.addItems(request);
//
//        if (request != null)
//        {
//            imported = grab - request.stackSize;
//        }
//
//        if (item.stackSize == imported)
//        {
//            turtle.setSlotContents(turtle.getSelectedSlot(), null);
//        }
//        else
//        {
//            item.splitStack(imported);
//        }

        return imported;
    }

    @Override
    public String getType()
    {
        return "driveinterface";
    }
}
