///*******************************************************************************
// * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// * Itszuvalex@gmail.com
// *
// * This program is free software; you can redistribute it and/or
// * modify it under the terms of the GNU General Public License
// * as published by the Free Software Foundation; either version 2
// * of the License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program; if not, write to the Free Software
// * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// ******************************************************************************/
//
//package femtocraft.power.render;
//
//import femtocraft.Femtocraft;
//import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.ResourceLocation;
//
//import java.util.Random;
//
///**
// * Created by Christopher Harris (Itszuvalex) on 5/10/14.
// */
//public class RenderStellaratorCore extends TileEntitySpecialRenderer {
//    private static final int totalPeriodTicks = 20 * 5;
//    private static final ResourceLocation blockTexture = new ResourceLocation
//            (Femtocraft.ID.toLowerCase() + ":" + "BlockStellaratorCore_side");
//    private static final ResourceLocation tunnelTexture = new
//            ResourceLocation(Femtocraft.ID.toLowerCase() + ":" +
//                                     "BlockStellaratorCore_tunnel");
//    private static final ResourceLocation cubeTexture = new ResourceLocation(
//            (Femtocraft.ID.toLowerCase() + ":" + "BlockStellaratorCore_core"));
//
//    private int periodTicks;
//
//    private Random random = new Random();
//    private int xOffset;
//    private int yOffset;
//    private int zOffset;
//    private boolean xPosMovement;
//    private boolean yPosMovement;
//    private boolean zPosMovement;
//    private double xRotOffset;
//    private double yRotOffset;
//    private double zRotOffset;
//    private boolean xRotPosMovement;
//    private boolean yRotPosMovement;
//    private boolean zRotPosMovement;
//
//
//    public RenderStellaratorCore() {
//        xOffset = random.nextInt(totalPeriodTicks);
//        yOffset = random.nextInt(totalPeriodTicks);
//        zOffset = random.nextInt(totalPeriodTicks);
//        xPosMovement = random.nextBoolean();
//        yPosMovement = random.nextBoolean();
//        zPosMovement = random.nextBoolean();
//        xRotOffset = random.nextDouble() * totalPeriodTicks;
//        yRotOffset = random.nextDouble() * totalPeriodTicks;
//        zRotOffset = random.nextDouble() * totalPeriodTicks;
//        xRotPosMovement = random.nextBoolean();
//        yRotPosMovement = random.nextBoolean();
//        zRotPosMovement = random.nextBoolean();
//        periodTicks = random.nextInt(totalPeriodTicks);
//    }
//
//    @Override
//    public void renderTileEntityAt(TileEntity tileentity, double x,
//                                   double y, double z, float partialTick) {
//
//    }
//}
