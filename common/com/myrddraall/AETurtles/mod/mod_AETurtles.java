package com.myrddraall.AETurtles.mod;

import cp.mods.core.mod.ModBase;
import com.myrddraall.AETurtles.mod.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME)
@NetworkMod(clientSideRequired = ModInfo.CLIENT_REQUIRED, serverSideRequired = ModInfo.SERVER_REQUIRED)
public class mod_AETurtles extends ModBase<CommonProxy>
{
    @SidedProxy(clientSide = ModInfo.CLIENT, serverSide = ModInfo.SERVER)
    public static CommonProxy proxy;

    @Override
    public CommonProxy getProxy()
    {
        return proxy;
    }

    @Override
    @PreInit
    public void preInitialize(FMLPreInitializationEvent event)
    {
        super.preInitialize(event);
    }

    @Override
    @Init
    public void initialize(FMLInitializationEvent event)
    {
        super.initialize(event);
    }

    @Override
    @PostInit
    public void postInitialize(FMLPostInitializationEvent event)
    {
        super.postInitialize(event);
    }
}
