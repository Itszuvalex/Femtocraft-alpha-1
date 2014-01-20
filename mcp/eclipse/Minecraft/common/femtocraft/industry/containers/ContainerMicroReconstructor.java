package femtocraft.industry.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.api.IAssemblerSchematic;
import femtocraft.common.gui.DisplaySlot;
import femtocraft.common.gui.OutputSlot;
import femtocraft.industry.TileEntity.MicroReconstructorTile;
import femtocraft.industry.items.AssemblySchematic;

public class ContainerMicroReconstructor extends Container
{
    private MicroReconstructorTile reconstructor;
    private int lastCookTime = 0;
    private int lastPower = 0;
    private int lastMass = 0;

    public ContainerMicroReconstructor(InventoryPlayer par1InventoryPlayer, MicroReconstructorTile reconstructor)
    {
        this.reconstructor = reconstructor;
        this.addSlotToContainer(new OutputSlot(reconstructor, 9, 122, 23));
        Slot schematic = new Slot(reconstructor, 10, 94, 54) {
			        	public boolean isItemValid(ItemStack par1ItemStack)
			            {
			                return par1ItemStack.getItem() instanceof IAssemblerSchematic;
			            }
        		};
        schematic.setBackgroundIcon(AssemblySchematic.placeholderIcon);
        this.addSlotToContainer(schematic);
        for(int y = 0; y < 3; ++y)
        {
        	for(int x = 0; x < 3; ++x)
        	{
        		this.addSlotToContainer(new DisplaySlot(reconstructor, x + y*3, 32 + x*18, 18 + y*18){
        			@Override
        			@SideOnly(Side.CLIENT)
        			public Icon getBackgroundIconIndex() {
        				return ((MicroReconstructorTile)this.inventory).getStackInSlot(10) != null ? null : DisplaySlot.noPlaceDisplayIcon;
        			}
        		});
        	}
        }
        
        for(int y = 0; y < 2; ++y)
        {
        	for(int x = 0; x < 9; ++x)
        	{
        		this.addSlotToContainer(new Slot(reconstructor, 11 + x + y*9, 8 + x*18, 77 + y*18));
        	}
        }
        
        
        int i;

        //Bind player inventory
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
            }
        }
        //Bind player actionbar
        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 180));
        }
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.reconstructor.cookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.reconstructor.getCurrentPower());
        par1ICrafting.sendProgressBarUpdate(this, 2, this.reconstructor.getMassAmount());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.reconstructor.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.reconstructor.cookTime);
            }
            if(this.lastPower != this.reconstructor.getCurrentPower()) {
            	icrafting.sendProgressBarUpdate(this, 1, this.reconstructor.getCurrentPower());
            }
            if(this.lastMass != this.reconstructor.getMassAmount())
            {
            	icrafting.sendProgressBarUpdate(this, 2, this.reconstructor.getMassAmount());
            }
        }

        this.lastCookTime = this.reconstructor.cookTime;
        this.lastPower = this.reconstructor.getCurrentPower();
        this.lastMass = this.reconstructor.getMassAmount();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
    	switch(par1)
    	{
    	case 0:
    		this.reconstructor.cookTime = par2;
    		break;
    	case 1:
    		this.reconstructor.currentPower = par2;
    		break;
    	case 2:
    		if(par2 > 0)
    		{
    			this.reconstructor.setFluidAmount(par2);
    		}
    		else
    		{
    			this.reconstructor.clearFluid();
    		}
    		break;
    	default:
    		return;
    	}
//        if (par1 == 0)
//        {
//            this.deconstructor.cookTime = par2;
//        }
//        if(par1 == 1)
//        {
//        	this.deconstructor.currentPower = par2;
//        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.reconstructor.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if(par2 < 9) return null;

            if (par2 >= 9 && par2 <= 10)
            {
                if (!this.mergeItemStack(itemstack1, 11, 28, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(par2 > 10 && par2 < 28)
            {
            	if(!this.mergeItemStack(itemstack1, 28, 64, true))
            	{
            		return null;
            	}
            }
            else if ( par2 > 28)
            {
                if (itemstack1.getItem() instanceof IAssemblerSchematic)
                {
                    if (!this.mergeItemStack(itemstack1, 10, 11, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 28 && par2 < 55)
                {
                    if (!this.mergeItemStack(itemstack1, 55, 64, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 55 && par2 < 64 && !this.mergeItemStack(itemstack1, 28, 55, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 28, 64, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }
}
