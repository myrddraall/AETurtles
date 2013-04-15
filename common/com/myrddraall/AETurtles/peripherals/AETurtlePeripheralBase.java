package com.myrddraall.AETurtles.peripherals;

import net.minecraft.item.ItemStack;
import appeng.api.Util;
import appeng.api.me.util.IMEInventory;
import cp.mods.core.integration.computercraft.peripheral.TurtlePeripheralBase;
import cp.mods.core.integration.computercraft.util.LuaUtil;
import dan200.turtle.api.ITurtleAccess;

public abstract class AETurtlePeripheralBase extends TurtlePeripheralBase
{
    public AETurtlePeripheralBase(ITurtleAccess turtle)
    {
        super(turtle);
    }

    protected IMEInventory getInventory(ItemStack stack)
    {
        
        return Util.getCell(stack);
    }
    protected IMEInventory getInventory(Integer slot)
    {
        return Util.getCell(getSlotContents(slot));
    }

    public Boolean isCell(Integer slot)
    {
        return Util.isCell(getSlotContents(slot));
    }
    public Boolean isCell(ItemStack stack)
    {
        return Util.isCell(stack);
    }

    public Object[] isCell(Object[] args)
    {
        Integer slot = args.length > 0 ? LuaUtil.toSlot(args[0]) : turtle.getSelectedSlot();
        Object[] r = { isCell(slot) };
        return r;
    }

    public Boolean isBlankPattern(Integer slot)
    {
        ItemStack stack = getSlotContents(slot);
        return Util.isBlankPattern(stack);
    }

    public Object[] isBlankPattern(Object[] args)
    {
        Integer slot = args.length > 0 ? LuaUtil.toSlot(args[0]) : turtle.getSelectedSlot();
        Object[] r = { isBlankPattern(slot) };
        return r;
    }

    public Boolean isEncodedPattern(Integer slot)
    {
        ItemStack stack = getSlotContents(slot);
        return Util.isAssemblerPattern(stack);
    }

    public Object[] isEncodedPattern(Object[] args)
    {
        Integer slot = args.length > 0 ? LuaUtil.toSlot(args[0]) : turtle.getSelectedSlot();
        Object[] r = { isEncodedPattern(slot) };
        return r;
    }

    public Boolean isPattern(Integer slot)
    {
        return isBlankPattern(slot) || isEncodedPattern(slot);
    }

    public Object[] isPattern(Object[] args)
    {
        Integer slot = args.length > 0 ? LuaUtil.toSlot(args[0]) : turtle.getSelectedSlot();
        Object[] r = { isPattern(slot) };
        return r;
    }
}
