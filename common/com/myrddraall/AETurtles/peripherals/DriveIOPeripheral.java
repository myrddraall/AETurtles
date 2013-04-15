package com.myrddraall.AETurtles.peripherals;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import appeng.api.IAEItemStack;
import appeng.api.me.util.IMEInventory;
import cp.mods.core.integration.computercraft.util.LuaUtil;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class DriveIOPeripheral extends DriveReaderPeripheral
{
    private final static String[] luaMethods = { "transfer", "checkTransfer" };

    public DriveIOPeripheral(ITurtleAccess turtle)
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
                setState(AEDriveState.TRANSFER);
                returnVals = transferItemsTo(args);
                break;

            case 1:
                setState(AEDriveState.READ);
                returnVals = calcTransferItemsTo(args);
                break;

            default:
                returnVals = super.callMethod(cpu, methodId - luaMethods.length,
                        args);
                break;
        }

        return returnVals;
    }

    public Object[] transferItemsTo(Object[] args)
    {
        String item = LuaUtil.toString(args[0]);
        Integer amount = LuaUtil.toInteger(args[1]);
        Integer sourceSlot = turtle.getSelectedSlot();
        Integer destSlot = LuaUtil.toSlot(args[2]);
        Object[] r = { transferItemsTo(item, amount, sourceSlot, destSlot) };
        return r;
    }

    public Integer transferItemsTo(String itemId, Integer amount,
            Integer sourceSlot, Integer destSlot)
    {
        if (amount > 0)
        {
            IMEInventory source = getInventory(sourceSlot);
            IMEInventory dest = getInventory(destSlot);

            if (source != null && dest != null)
            {
                ItemStack item = findItem(source, itemId);
                return transferItemsTo(item, amount, source, dest);
            }
        }

        return 0;
    }

    public Integer transferItemsTo(ItemStack item, Integer amount,
            IMEInventory source, IMEInventory dest)
    {
//        ItemStack tstack = calcTransferItemsTo(item, amount, source, dest);
//
//        if (tstack != null && tstack.stackSize > 0)
//        {
//
//            ItemStack stack = source.extractItems(tstack);
//
//            if (stack != null && stack.stackSize > 0)
//            {
//                dest.addItems(stack);
//                return stack.stackSize;
//            }
//        }

        return 0;
    }

    public Object[] calcTransferItemsTo(Object[] args)
    {
        String item = LuaUtil.toString(args[0]);
        Integer amount = LuaUtil.toInteger(args[1]);
        Integer sourceSlot = turtle.getSelectedSlot();
        Integer destSlot = LuaUtil.toSlot(args[2]);
        Object[] r = { calcTransferItemsTo(item, amount, sourceSlot, destSlot) };
        return r;
    }
    public Integer calcTransferItemsTo(String itemId, Integer amount,
            Integer sourceSlot, Integer destSlot)
    {
        if (amount > 0)
        {
            IMEInventory source = getInventory(sourceSlot);
            IMEInventory dest = getInventory(destSlot);

            if (source != null && dest != null)
            {
                ItemStack item = findItem(source, itemId);
                item = calcTransferItemsTo(item, amount, source, dest);
                return item == null ? 0 : item.stackSize;
            }
        }

        return 0;
    }
    public ItemStack calcTransferItemsTo(ItemStack item, Integer amount,
            IMEInventory source, IMEInventory dest)
    {
//        if (amount > 0)
//        {
//            if (item != null)
//            {
//                Integer grab = Math.min(item.stackSize,
//                        Math.min(item.getItem().getItemStackLimit(), amount));
//                item.stackSize = grab;
//                ItemStack left = dest.calculateItemAddition(item);
//
//                if (left != null && left.stackSize > 0)
//                {
//                    item.stackSize -= left.stackSize;
//                }
//
//                return item;
//            }
//        }

        return null;
    }

    @Override
    public String getType()
    {
        return "driveio";
    }
}
