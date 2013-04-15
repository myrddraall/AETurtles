package com.myrddraall.AETurtles.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.tileentity.TileEntity;

import com.jcraft.jorbis.Block;
import com.myrddraall.AETurtles.peripherals.AEDriveState;
import com.myrddraall.AETurtles.peripherals.DriveReaderPeripheral;

import cp.mods.core.network.packet.BlockPacketBase;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.shared.ITurtle;

public class AETurtleDriveStatusUpdatePacket extends BlockPacketBase
{
    public int state;
    public double percentFull;

    @Override
    public void writePacket(DataOutputStream out) throws IOException
    {
        super.writeExternal(out);
        out.write(state);
        out.writeDouble(percentFull);
    }

    @Override
    public void readPacket(DataInputStream in) throws IOException
    {
        super.readExternal(in);
        state = in.read();
        percentFull = in.readDouble();
    }

    @Override
    public void execute()
    {
        EntityClientPlayerMP p = (EntityClientPlayerMP) getPlayer();
        TileEntity te = p.worldObj.getBlockTileEntity((int) getX(), (int) getY(), (int) getZ());

        if (te instanceof ITurtle)
        {
            ITurtle turtle = (ITurtle) te;
            IHostedPeripheral periph;
            periph = turtle.getPeripheral(TurtleSide.Left);

            if (periph instanceof DriveReaderPeripheral)
            {
                DriveReaderPeripheral d = (DriveReaderPeripheral) periph;
                d.percentFull = this.percentFull;
            }

            periph = turtle.getPeripheral(TurtleSide.Right);

            if (periph instanceof DriveReaderPeripheral)
            {
                DriveReaderPeripheral d = (DriveReaderPeripheral) periph;
                d.percentFull = this.percentFull;
                d.setState(AEDriveState.values()[this.state]);
            }
        }
    }
}
