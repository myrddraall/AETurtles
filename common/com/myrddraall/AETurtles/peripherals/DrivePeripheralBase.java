package com.myrddraall.AETurtles.peripherals;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import appeng.api.me.util.IMEInventory;

import com.myrddraall.AETurtles.network.AETurtleChannelPacketType;
import com.myrddraall.AETurtles.network.AETurtleDriveStatusUpdatePacket;

import cp.mods.core.network.NetworkManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.TurtleSide;

public class DrivePeripheralBase extends AETurtlePeripheralBase
{
    @Override
    public void attach(IComputerAccess turtle)
    {
        super.attach(turtle);
        turtle.mountFixedDir("rom/apis/aedrive", "mods/AETurtle/lua/TurtleDriveAPI.lua", true, 0);
    }

    public long bytesUsed;
    public long bytesTotal;
    public double percentFull;
    protected AEDriveState state = AEDriveState.IDEL;
    public double percentFullOld;
    protected AEDriveState stateOld = AEDriveState.IDEL;

    public DrivePeripheralBase(ITurtleAccess turtle)
    {
        super(turtle);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        percentFull = tag.getDouble("percentFull");
        setState(AEDriveState.values()[tag.getInteger("state")]);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setDouble("percentFull", percentFull);
        tag.setInteger("state", getState().ordinal());
    }

    @Override
    public boolean canAttachToSide(int side)
    {
        IHostedPeripheral p = turtle.getPeripheral(TurtleSide.Left.ordinal() == side ? TurtleSide.Right : TurtleSide.Left);
        String pt = p.getType();

        if (pt == "drivereader" || pt == "driveio" || pt == "driveinterface")
        {
            return false;
        }

        return true;
    }

    private int calcDSTimer = 19;

    public void calculateDriveSpace()
    {
        calcDSTimer = ++calcDSTimer % 20;

        if (calcDSTimer == 0)
        {
            int len = turtle.getInventorySize();
            this.bytesUsed = 0;
            this.bytesTotal = 0;

            for (int i = 0; i < len; i++)
            {
                ItemStack stack = turtle.getSlotContents(i);

                if (stack != null && isCell(stack))
                {
                    IMEInventory inv = getInventory(stack);
                    long typesUsed = inv.storedItemTypes();
                    long itemsUsed = inv.storedItemCount();
                    long itemsRemaining = inv.remainingItemCount();
                    long used = (typesUsed * 8) + itemsUsed;
                    long total = used + itemsRemaining;
                    this.bytesUsed += used;
                    this.bytesTotal += total;
                }
            }

            if (bytesTotal == 0)
            {
                bytesUsed = -1;
                bytesTotal = -1;
            }

            double percent = 1.0 * bytesUsed / bytesTotal;

            if (percent != this.percentFull)
            {
                this.percentFull = percent;
                invalidateState();
            }
        }
    }

    private boolean validState = true;

    public void invalidateState()
    {
        validState = false;
    }

    private boolean stateUpdatedThisTick = false;

    @Override
    public void update()
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();

        if (side == Side.SERVER)
            // server side
        {
            this.calculateDriveSpace();
        }
        else
        {
            // client side
        }

        updateState(side);

        if (!stateUpdatedThisTick && side == Side.SERVER)
        {
            stateUpdatedThisTick = true;
            setState(AEDriveState.IDEL);
        }

        super.update();
        stateUpdatedThisTick = false;
    }

    private int ticksSinceLastUpdate = 0;

    private void updateState(Side side)
    {
        if (side == Side.SERVER)
        {
            ++ticksSinceLastUpdate;

            if (!validState && ticksSinceLastUpdate > 3)
            {
                if (state != stateOld || percentFull != percentFullOld)
                {
                    AETurtleDriveStatusUpdatePacket packet = (AETurtleDriveStatusUpdatePacket) AETurtleChannelPacketType.DRIVE_STATUS_UPDATE.create();
                    packet.percentFull = percentFull;
                    packet.state = getState().ordinal();
                    Vec3 p = turtle.getPosition();
                    packet.setX((int)p.xCoord);
                    packet.setY((int)p.yCoord);
                    packet.setZ((int)p.zCoord);
                    packet.setDimention(turtle.getWorld().getWorldInfo().getDimension());
                    NetworkManager.broadcastAround(packet);
                    stateOld = state;
                    percentFullOld = percentFull;
                    stateUpdatedThisTick = true;
                    validState = true;
                    ticksSinceLastUpdate = 0;
                }
            }
        }
    }

    public AEDriveState getState()
    {
        return state;
    }

    public void setState(AEDriveState state)
    {
        if (this.state != state)
        {
            this.state = state;
            invalidateState();
        }
    }
}
