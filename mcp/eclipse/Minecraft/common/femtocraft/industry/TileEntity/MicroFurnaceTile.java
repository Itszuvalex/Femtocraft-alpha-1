package femtocraft.industry.TileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeDummyContainer;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.power.TileEntity.FemtopowerTile;

public class MicroFurnaceTile  extends FemtopowerTile implements ISidedInventory, net.minecraftforge.common.ISidedInventory
{
	public MicroFurnaceTile() {
		super();
		setMaxStorage(4000);
	}
	
	
	private int powerToCook = 40;
	private int ticksToCook = 100;
	
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[2];

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime = 0;
    public int currentPower = 0;
    private String field_94130_e;
    private ItemStack smeltingStack = null;
    
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.furnaceItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.furnaceItemStacks[par1].splitStack(par2);

                if (this.furnaceItemStacks[par1].stackSize == 0)
                {
                    this.furnaceItemStacks[par1] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.field_94130_e : "MicroFurnace";
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized()
    {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    public void func_94129_a(String par1Str)
    {
        this.field_94130_e = par1Str;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount()-1; ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        NBTTagCompound nbttagcompoundsmelt = (NBTTagCompound)nbttaglist.tagAt(nbttaglist.tagCount()-1);
        if(nbttagcompoundsmelt.getBoolean("isSmelting")) {
        	this.smeltingStack.loadItemStackFromNBT(nbttagcompoundsmelt);
        }
        else {
        	this.smeltingStack = null;
        }
        
        
        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.field_94130_e = par1NBTTagCompound.getString("CustomName");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        
        NBTTagCompound nbttagcompoundsmelt = new NBTTagCompound();
        nbttagcompoundsmelt.setBoolean("isSmelting", smeltingStack != null);
        if(smeltingStack != null) {
        	this.smeltingStack.writeToNBT(nbttagcompoundsmelt);
        }
    	nbttaglist.appendTag(nbttagcompoundsmelt);
        
        par1NBTTagCompound.setTag("Items", nbttaglist);

        if (this.isInvNameLocalized())
        {
            par1NBTTagCompound.setString("CustomName", this.field_94130_e);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return this.furnaceCookTime * par1 / ticksToCook;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        boolean flag1 = false;

        if(smeltingStack != null) {
	        if (this.furnaceCookTime == ticksToCook)
	        {
	            this.furnaceCookTime = 0;
	            this.endSmelt();
	            flag1 = true;
	        }
	        
	
	        	++this.furnaceCookTime;
        }
        else if (this.canSmelt())
        {
           this.startSmelt();
           flag1 = true;
        }
        else
        {
            this.furnaceCookTime = 0;
        }

        if (flag1)
        {
            this.onInventoryChanged();
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.furnaceItemStacks[0] == null)
        {
            return false;
        }
        if(smeltingStack != null) {
        	return false;
        }
        else if(this.getCurrentPower() < this.powerToCook) {
        	return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
            if (itemstack == null) return false;
            if (this.furnaceItemStacks[1] == null) return true;
            if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) return false;
            int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }
    
    public void startSmelt() {
		this.smeltingStack = this.furnaceItemStacks[0].copy();
		this.smeltingStack.stackSize = 1;
		
		--this.furnaceItemStacks[0].stackSize;

        if (this.furnaceItemStacks[0].stackSize <= 0)
        {
            this.furnaceItemStacks[0] = null;
        }
		
        this.consume(powerToCook);
    }
    
    public void endSmelt() {
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.smeltingStack);
        itemstack.stackSize = smeltingStack.stackSize;
        
        if(itemstack != null) {
	        if (this.furnaceItemStacks[1] == null)
	        {
	            this.furnaceItemStacks[1] = itemstack.copy();
	        }
	        else if (this.furnaceItemStacks[1].isItemEqual(itemstack))
	        {
	            furnaceItemStacks[1].stackSize += itemstack.stackSize;
	        }
	        
	        smeltingStack = null;
        }
    }

 
    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 1 ? false : true;
    }

    /**
     * Get the size of the side inventory.
     */
    public int[] getSizeInventorySide(int par1)
    {
        return new int[]{1, 1, 1, 1, 1, 1};
    }

    public boolean func_102007_a(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isStackValidForSlot(par1, par2ItemStack);
    }

    public boolean func_102008_b(int par1, ItemStack par2ItemStack, int par3)
    {
        return par3 != 0 || par1 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
    }

    /***********************************************************************************
     * This function is here for compatibilities sake, Modders should Check for
     * Sided before ContainerWorldly, Vanilla Minecraft does not follow the sided standard
     * that Modding has for a while.
     *
     * In vanilla:
     *
     *   Top: Ores
     *   Sides: Fuel
     *   Bottom: Output
     *
     * Standard Modding:
     *   Top: Ores
     *   Sides: Output
     *   Bottom: Fuel
     *
     * The Modding one is designed after the GUI, the vanilla one is designed because its
     * intended use is for the hopper, which logically would take things in from the top.
     *
     * This will possibly be removed in future updates, and make vanilla the definitive
     * standard.
     */

    @Override
    public int getStartInventorySide(ForgeDirection side)
    {
       if(side == ForgeDirection.UP) return 0;
       if(side == ForgeDirection.NORTH) return 0;
       if(side == ForgeDirection.WEST) return 0;
       if(side == ForgeDirection.EAST) return 1;
       if(side == ForgeDirection.SOUTH) return 1;
       if(side == ForgeDirection.DOWN) return 1;
       else return 0;
    }

    @Override
    public int getSizeInventorySide(ForgeDirection side)
    {
        return 1;
    }
}

