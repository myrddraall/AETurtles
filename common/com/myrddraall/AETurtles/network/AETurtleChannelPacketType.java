package com.myrddraall.AETurtles.network;

import cp.mods.core.network.packet.IPacket;
import cp.mods.core.network.packet.IPacketType;

public enum AETurtleChannelPacketType implements IPacketType
{
    DRIVE_STATUS_UPDATE(AETurtleDriveStatusUpdatePacket.class);

    private Class <? extends IPacket > type;
    private String channel = null;

    private AETurtleChannelPacketType(Class <? extends IPacket> type)
    {
        this.type = type;
    }
    @Override
    public Class <? extends IPacket> getPacketClass()
    {
        return type;
    }

    @Override
    public IPacket create()
    {
        IPacket inst;

        try
        {
            inst = type.newInstance();
            inst.setChannelType(this);
            return inst;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    @Override
    public String getChannel()
    {
        return channel;
    }
}
