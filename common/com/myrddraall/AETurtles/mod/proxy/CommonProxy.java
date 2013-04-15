package com.myrddraall.AETurtles.mod.proxy;

import com.myrddraall.AETurtles.api.item.UpgrageItems;
import com.myrddraall.AETurtles.item.UpgradeItemInit;

import cp.mods.core.mod.proxy.ProxyBase;

public class CommonProxy extends ProxyBase
{

    @Override
    public void initializeRegistration()
    {
        typeRegistry.register(UpgrageItems.class, UpgradeItemInit.class);
    }

    @Override
    public void initializeConfig()
    {
    }

}
