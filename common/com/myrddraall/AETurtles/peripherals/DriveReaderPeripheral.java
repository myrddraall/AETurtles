package com.myrddraall.AETurtles.peripherals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import appeng.api.Util;
import appeng.api.me.util.IMEInventory;
import cp.mods.core.integration.computercraft.util.LuaUtil;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class DriveReaderPeripheral extends DrivePeripheralBase
{
    private final static String[] luaMethods =
        { "isCell", "isPattern", "isBlankPattern", "isEncodedPattern", "getCellInfo",
                "getCellInventory", "find", "findByKey", "findByName", "findByItemID", "search"
    };

    public DriveReaderPeripheral(ITurtleAccess turtle)
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
    public Object[] callMethod(IComputerAccess cpu, int methodId, Object[] args) throws Exception
    {
        Object[] returnVals = null;

        switch (methodId)
        {
            case 0:
                setState(AEDriveState.READ);
                returnVals = isCell(args);
                break;

            case 1:
                setState(AEDriveState.READ);
                returnVals = isPattern(args);
                break;

            case 2:
                setState(AEDriveState.READ);
                returnVals = isBlankPattern(args);
                break;

            case 3:
                setState(AEDriveState.READ);
                returnVals = isEncodedPattern(args);
                break;
            case 4:
                setState(AEDriveState.READ);
                returnVals = getCellInfo(args);
                break;

            case 5:
                setState(AEDriveState.READ);
                returnVals = getCellContents(args);
                break;

            case 6:
                setState(AEDriveState.READ);
                returnVals = findItem(args);
                break;

            case 7:
                setState(AEDriveState.READ);
                returnVals = findItemByKey(args);
                break;

            case 8:
                setState(AEDriveState.READ);
                returnVals = findItemByName(args);
                break;

            case 9:
                setState(AEDriveState.READ);
                returnVals = findItemByItemID(args);
                break;

            case 10:
                setState(AEDriveState.READ);
                returnVals = searchItems(args);
                break;

            default:
                returnVals = super.callMethod(cpu, methodId - luaMethods.length, args);
                break;
        }

        return returnVals;
    }

    @Override
    public String getType()
    {
        return "drivereader";
    }

    public Map<String, Object> getCellInfo(ItemStack stack)
    {
        HashMap<String, Object> r = new HashMap<String, Object>();
        IMEInventory inv = getInventory(stack);
        // Item item = stack.getItem();
        r.put("maxTypes", inv.getTotalItemTypes());
        r.put("types", inv.storedItemTypes());
        r.put("items", inv.storedItemCount());
        r.put("typesLeft", inv.remainingItemTypes());
        r.put("itemLeft", inv.remainingItemCount());
        return r;
    }

    public Map<String, Object> getCellInfo(Integer slot)
    {
        return getCellInfo(getSlotContents(slot));
    }

    public Object[] getCellInfo(Object[] args)
    {
        Integer slot = args.length > 0 ? LuaUtil.toSlot(args[0]) : turtle.getSelectedSlot();
        Object[] r =
            { getCellInfo(slot) };
        return r;
    }

    public List<ItemStack> getCellStoredItems(Integer slot)
    {
        return getCellStoredItems(getSlotContents(slot));
    }

    public List<ItemStack> getCellStoredItems(ItemStack stack)
    {
        IMEInventory inv = getInventory(stack);
        return getCellStoredItems(inv);
    }

    @SuppressWarnings("unchecked")
    public List<ItemStack> getCellStoredItems(IMEInventory inv)
    {
        return inv.getAvailableItems().getItems();
    }

    public Map<Integer, Object> getCellContents(Integer slot)
    {
        Map<Integer, Object> ret = new HashMap<Integer, Object>();
        List<ItemStack> list = getCellStoredItems(slot);
        int len = list.size();

        for (int i = 0; i < len; i++)
        {
            Map<String, Object> itm = getSimpleItemInfo(list.get(i));
            ret.put(i + 1, itm);
        }

        return ret;
    }

    public Object[] getCellContents(Object[] args)
    {
        Integer slot = turtle.getSelectedSlot();
        Object[] r =
            { getCellContents(slot) };
        return r;
    }

    protected Map<String, Object> getSimpleItemInfo(ItemStack stack)
    {
        if (stack == null) { return null; }

        HashMap<String, Object> itm = new HashMap<String, Object>();
        itm.put("id", ItemID.getUID(stack));
        itm.put("count", stack.stackSize);
        return itm;
    }

    protected Map<String, Object> getDetailedItemInfo(ItemStack stack)
    {
        if (stack == null) { return null; }

        Map<String, Object> itm = getSimpleItemInfo(stack);
        itm.put("name", stack.getDisplayName());
        itm.put("key", stack.getItemName());
        itm.put("damage", stack.getItemDamage());

        if (stack.hasTagCompound())
        {
            itm.put("tagID", stack.getTagCompound().hashCode());
        }

        return itm;
    }

    protected ItemStack findItem(ItemStack stack, String id)
    {
        List<ItemStack> items = getCellStoredItems(stack);
        return findItem(items, id);
    }

    protected ItemStack findItem(IMEInventory inv, String id)
    {
        List<ItemStack> items = getCellStoredItems(inv);
        return findItem(items, id);
    }

    protected ItemStack findItem(Integer slot, String id)
    {
        List<ItemStack> items = getCellStoredItems(slot);
        return findItem(items, id);
    }

    protected ItemStack findItem(List<ItemStack> items, String id)
    {
        ItemStack item = null;
        int len = items.size();

        for (int i = 0; item == null && i < len; i++)
        {
            ItemStack itm = items.get(i);

            if (id.equals(ItemID.getUID(itm)))
            {
                item = itm;
            }
        }

        return item;
    }

    protected Object[] findItem(Object[] args)
    {
        Integer slot = turtle.getSelectedSlot();
        String id = args[0].toString();
        Object[] r =
            { getSimpleItemInfo(findItem(slot, id)) };
        return r;
    }

    protected ItemStack findItemByKey(Integer slot, String key)
    {
        List<ItemStack> items = getCellStoredItems(slot);
        ItemStack item = null;
        int len = items.size();

        for (int i = 0; item != null && i < len; i++)
        {
            ItemStack itm = items.get(i);

            if (key.equals(itm.getItemName()))
            {
                item = itm;
            }
        }

        return item;
    }

    protected Object[] findItemByKey(Object[] args)
    {
        Integer slot = turtle.getSelectedSlot();
        String id = args[0].toString();
        Object[] r =
            { getSimpleItemInfo(findItemByKey(slot, id)) };
        return r;
    }

    protected ItemStack findItemByName(Integer slot, String name)
    {
        List<ItemStack> items = getCellStoredItems(slot);
        ItemStack item = null;
        int len = items.size();

        for (int i = 0; item != null && i < len; i++)
        {
            ItemStack itm = items.get(i);

            if (name.equals(itm.getDisplayName()))
            {
                item = itm;
            }
        }

        return item;
    }

    protected Object[] findItemByName(Object[] args)
    {
        Integer slot = turtle.getSelectedSlot();
        String id = args[0].toString();
        Object[] r =
            { getSimpleItemInfo(findItemByName(slot, id)) };
        return r;
    }

    protected ItemStack findItemByItemID(Integer slot, String id)
    {
        String[] parts = id.split(":", 2);
        Integer itemID = Integer.valueOf(parts[0]);
        Integer damage = null;

        if (parts.length == 2)
        {
            damage = Integer.valueOf(parts[1]);
        }

        List<ItemStack> items = getCellStoredItems(slot);
        ItemStack item = null;
        int len = items.size();

        for (int i = 0; item != null && i < len; i++)
        {
            ItemStack itm = items.get(i);

            if (itemID == item.itemID && (damage == null || damage == item.getItemDamage()))
            {
                item = itm;
            }
        }

        return item;
    }

    protected Object[] findItemByItemID(Object[] args)
    {
        Integer slot = turtle.getSelectedSlot();
        String id = args[0].toString();
        Object[] r =
            { getSimpleItemInfo(findItemByItemID(slot, id)) };
        return r;
    }

    protected List<ItemStack> searchItems(Integer slot, String search)
    {
        List<ItemStack> list = new ArrayList<ItemStack>();
        List<ItemStack> inv = this.getCellStoredItems(slot);
        ItemID searchId = new ItemID(search);
        int len = inv.size();

        for (int i = 0; i < len; i++)
        {
            ItemStack item = inv.get(i);
            ItemID id = new ItemID(item);

            if (id.matches(searchId))
            {
                list.add(item);
            }
            else
            {
                if (item.getDisplayName().indexOf(search) != -1)
                {
                    list.add(item);
                }
            }
        }

        return list;
    }

    protected Object[] searchItems(Object[] args)
    {
        Integer slot = turtle.getSelectedSlot();
        String search = args[0].toString();
        Map<Integer, Object> ret = new HashMap<Integer, Object>();
        List<ItemStack> list = searchItems(slot, search);
        int len = list.size();

        for (int i = 0; i < len; i++)
        {
            Map<String, Object> itm = getSimpleItemInfo(list.get(i));
            ret.put(i + 1, itm);
        }

        Object[] r =
            { ret };
        return r;
    }
}

class ItemID
{
    private String id;
    private String damage;
    private String tagId;
    private Boolean isIdNumeric = false;
    private Boolean wildCard = false;

    public static String getUID(ItemStack stack)
    {
        //Item item = stack.getItem();
        String id = String.valueOf(stack.itemID);
        id += ":" + stack.getItemDamage();

        if (stack.hasTagCompound())
        {
            id += "|" + stack.getTagCompound().hashCode();
        }

        return id;
    }

    public String getId()
    {
        return id;
    }

    public String getDamage()
    {
        return damage;
    }

    public String getTagId()
    {
        return tagId;
    }

    public Boolean isIDNumberic()
    {
        return isIdNumeric;
    }

    public Boolean hasWildCard()
    {
        return wildCard;
    }

    public ItemID(ItemStack stack)
    {
        this(getUID(stack));
    }

    public ItemID(String compondId)
    {
        String[] parts = compondId.split("\\|", 2);
        id = parts[0];
        String damage = "*";
        String tagId = null;

        if (parts.length == 2)
        {
            tagId = parts[1];
        }

        parts = id.split(":", 2);
        id = parts[0];

        if (parts.length == 2)
        {
            damage = parts[1];
        }

        try
        {
            Integer.parseInt(id, 10);
            isIdNumeric = true;
        } catch (NumberFormatException e)
        {
            isIdNumeric = false;
        }

        this.damage = damage;
        this.tagId = tagId;

        if (damage == "*" || tagId == "*")
        {
            wildCard = true;
        }
    }

    public boolean equals(ItemID i)
    {
        return this.toString().equals(i.toString());
    }

    public boolean equals(String i)
    {
        return this.toString().equals(i);
    }

    public boolean equals(ItemStack stack)
    {
        ItemID b = new ItemID(stack);

        if (!this.isIDNumberic())
        {
            b.id = stack.getItemName();
            b.isIdNumeric = false;
        }

        return equals(b);
    }

    public boolean matches(ItemStack stack)
    {
        ItemID b = new ItemID(stack);

        if (!this.isIDNumberic())
        {
            b.id = stack.getItemName();
            b.isIdNumeric = false;
        }

        return matches(b);
    }

    public boolean matches(ItemID i)
    {
        if (!this.id.equals(i.id)) { return false; }

        if (!damage.equals("*") && !i.damage.equals("*"))
            if (!this.damage.equals(i.damage))
            {
            return false;
            }

        if (tagId == null && i.tagId == null) { return true; }

        if (tagId != null && tagId.equals("*")) { return true; }

        if (i.tagId != null && i.tagId.equals("*")) { return true; }

        if (this.tagId != null && this.tagId.equals(i.tagId)) { return true; }

        return false;
    }

    @Override
    public String toString()
    {
        String key = id + ":" + damage;

        if (tagId != null)
        {
            key += "|" + tagId;
        }

        return key;
    }
}
