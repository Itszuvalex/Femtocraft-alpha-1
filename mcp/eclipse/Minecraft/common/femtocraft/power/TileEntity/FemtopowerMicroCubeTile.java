package femtocraft.power.TileEntity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

import cpw.mods.fml.common.FMLCommonHandler;

import femtocraft.Femtocraft;
import femtocraft.FemtocraftPacketHandler;
import femtocraft.FemtocraftUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeDirection;

public class FemtopowerMicroCubeTile extends FemtopowerTile {
	public boolean[] outputs;
	
	public FemtopowerMicroCubeTile() {
		super();
		setMaxStorage(10000);
		outputs = new boolean[6];
		Arrays.fill(outputs, false);
	}

	public void onSideActivate(ForgeDirection side, EntityPlayer playerEntity)
	{
		int i = FemtocraftUtils.indexOfForgeDirection(side);
		outputs[i] = !outputs[i];
	}

	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		return outputs[FemtocraftUtils.indexOfForgeDirection(from)] ? 1.f : 0.f;
	}

	@Override
	public float getFillPercentageForOutput(ForgeDirection to) {
		return outputs[FemtocraftUtils.indexOfForgeDirection(to)] ? 1.f : 0.f;
	}

	@Override
	public boolean canCharge(ForgeDirection from) {
		return !outputs[FemtocraftUtils.indexOfForgeDirection(from)] && super.canCharge(from);
	}

	@Override
	public int charge(ForgeDirection from, int amount) {
		if(!outputs[FemtocraftUtils.indexOfForgeDirection(from)])
		{
			return super.charge(from, amount);
		}
		
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		for(int i = 0; i < 6; i++) {
			outputs[i] = par1nbtTagCompound.getBoolean(String.format("output%d", i));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		
		for(int i = 0; i < 6; i++) {
			par1nbtTagCompound.setBoolean(String.format("output%d", i), outputs[i]);
		}
	}
}
