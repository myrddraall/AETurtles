package com.myrddraall.AETurtles.item;

import net.minecraftforge.common.Configuration;

import com.myrddraall.AETurtles.api.item.UpgrageItems;

import cp.mods.core.api.type.IEnumerableType;
import cp.mods.core.item.SubtypedItemInitializer;

public class UpgradeItemInit extends SubtypedItemInitializer
{
    private static int itemId = 7000;

    @Override
    public void initialize()
    {
        item = new UpgradeItem(itemId);
        item.setName(UpgrageItems.itemName);
        item.setTypes(UpgrageItems.values());
        super.initialize();
        
    }

    @Override
    public void config(Configuration config)
    {
        itemId = config.get("item_ids", "upgrages", itemId).getInt();
    }

    @Override
    public void config(Configuration config, IEnumerableType type)
    {
        UpgrageItems t = (UpgrageItems) type;
        t.setUpgradeId(config.get("upgrage_ids", t.name(), t.getUpgradeId()).getInt());

    }

}
