package femtocraft.power.tiles;

import femtocraft.api.IInterfaceDevice;
import femtocraft.api.IPowerBlockContainer;
import femtocraft.core.multiblock.IMultiBlockComponent;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityFemtoCubePort extends TileEntityPowerBase implements
                                                                 IMultiBlockComponent {
    private static int storage = 10000000;
    public
    @Saveable(desc = true)
    boolean output;
    private
    @Saveable(desc = true)
    MultiBlockInfo info;

    public TileEntityFemtoCubePort() {
        super();
        info = new MultiBlockInfo();
        setMaxStorage(storage);
        setTechLevel(EnumTechLevel.FEMTO);
        output = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.power.tiles.TileEntityPowerBase#canCharge(net.minecraftforge
     * .common.ForgeDirection)
     */
    @Override
    public boolean canCharge(ForgeDirection from) {
        if (!info.isValidMultiBlock() || output) {
            return false;
        }
        return super.canCharge(from);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.power.tiles.TileEntityPowerBase#canAcceptPowerOfLevel(femtocraft
     * .managers.research.EnumTechLevel,
     * net.minecraftforge.common.ForgeDirection)
     */
    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level,
                                         ForgeDirection from) {
        if (!info.isValidMultiBlock()) {
            return false;
        }
        return super.canAcceptPowerOfLevel(level, from);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.power.tiles.TileEntityPowerBase#canConnect(net.minecraftforge
     * .common.ForgeDirection)
     */
    @Override
    public boolean canConnect(ForgeDirection from) {
        if (!info.isValidMultiBlock()) {
            return false;
        }
        return super.canConnect(from);
    }

    /*
     * (non-Javadoc)
     *
     * @see femtocraft.power.tiles.TileEntityPowerBase#getCurrentPower()
     */
    @Override
    public int getCurrentPower() {
        if (info.isValidMultiBlock()) {
            if (isController()) {
                return super.getCurrentPower();
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.getCurrentPower();
            }
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see femtocraft.power.tiles.TileEntityPowerBase#getMaxPower()
     */
    @Override
    public int getMaxPower() {
        if (info.isValidMultiBlock()) {
            if (isController()) {
                return super.getMaxPower();
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.getMaxPower();
            }
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see femtocraft.power.tiles.TileEntityPowerBase#getFillPercentage()
     */
    @Override
    public float getFillPercentage() {
        if (info.isValidMultiBlock()) {
            if (isController()) {
                return super.getFillPercentage();
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.getFillPercentage();
            }
        }
        return super.getFillPercentage();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.power.tiles.TileEntityPowerBase#getFillPercentageForCharging
     * (net.minecraftforge.common.ForgeDirection)
     */
    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            return output ? 1.f : 0.f;
        }
        return 1.f;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.power.tiles.TileEntityPowerBase#getFillPercentageForOutput
     * (net.minecraftforge.common.ForgeDirection)
     */
    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        if (info.isValidMultiBlock()) {
            return output ? 1.f : 0.f;
        }
        return 0.f;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.power.tiles.TileEntityPowerBase#charge(net.minecraftforge.
     * common.ForgeDirection, int)
     */
    @Override
    public int charge(ForgeDirection from, int amount) {
        if (info.isValidMultiBlock() && !output) {
            if (isController()) {
                return super.charge(from, amount);
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.charge(from, amount);
            }
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see femtocraft.power.tiles.TileEntityPowerBase#consume(int)
     */
    @Override
    public boolean consume(int amount) {
        if (info.isValidMultiBlock() && output) {
            if (isController()) {
                return super.consume(amount);
            }

            IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
                    .getBlockTileEntity(info.x(), info.y(), info.z());
            if (fc != null) {
                return fc.consume(amount);
            }
        }
        return false;
    }

    private boolean isController() {
        return info.isValidMultiBlock()
                && ((info.x() == xCoord) && (info.y() == yCoord) && (info.z() == zCoord));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.core.tiles.TileEntityBase#onSideActivate(net.minecraft.entity
     * .player.EntityPlayer, int)
     */
    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (isUseableByPlayer(par5EntityPlayer) && info.isValidMultiBlock()) {
            ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
            if (item != null
                    && item.getItem() instanceof IInterfaceDevice
                    && ((IInterfaceDevice) item.getItem()).getInterfaceLevel().tier >= EnumTechLevel.FEMTO.tier) {
                output = !output;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return true;
            }
            else {
                par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj,
                                         info.x(), info.y(), info.z());
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.core.tiles.TileEntityBase#handleDescriptionNBT(net.minecraft
     * .nbt.NBTTagCompound)
     */
    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        // info.loadFromNBT(compound.getCompoundTag("info"));
        // output = compound.getBoolean("output");

        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public boolean hasGUI() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = info.formMultiBlock(world, x, y, z);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
                                              worldObj.getBlockId(xCoord, yCoord, zCoord));
        return result;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean result = info.breakMultiBlock(world, x, y, z);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
                                              worldObj.getBlockId(xCoord, yCoord, zCoord));
        return result;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }
}