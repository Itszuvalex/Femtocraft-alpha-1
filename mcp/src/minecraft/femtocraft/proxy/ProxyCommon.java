/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.tiles.*;
import femtocraft.power.plasma.TileEntityPlasma;
import femtocraft.power.tiles.*;
import femtocraft.research.tiles.TileEntityResearchComputer;
import femtocraft.research.tiles.TileEntityResearchConsole;
import femtocraft.transport.items.tiles.TileEntityVacuumTube;
import femtocraft.transport.liquids.tiles.TileEntitySuctionPipe;

public class ProxyCommon {
    public void registerRendering() {
    }

    public void registerTileEntities() {
        // Tile Entities
        GameRegistry.registerTileEntity(TileEntityResearchComputer.class,
                                        "TileEntityResearchComputer");
        GameRegistry.registerTileEntity(TileEntityResearchConsole.class,
                                        "TileEntityResearchConsole");
        GameRegistry.registerTileEntity(TileEntityPowerBase.class,
                                        "TileEntityPowerBase");
        GameRegistry.registerTileEntity(TileEntityPowerProducerTest.class,
                                        "TileEntityPowerProducerTest");
        GameRegistry.registerTileEntity(TileEntityPowerConsumerTest.class,
                                        "TileEntityPowerConsumerTest");
        GameRegistry.registerTileEntity(TileEntityBaseEntityMicroFurnace.class,
                                        "FemtocraftMicroFurnace");
        GameRegistry.registerTileEntity(
                TileEntityBaseEntityMicroDeconstructor.class,
                "TileEntityMicroDeconstructor");
        GameRegistry.registerTileEntity(
                TileEntityBaseEntityMicroReconstructor.class,
                "TileEntityMicroReconstructor");
        GameRegistry.registerTileEntity(TileEntityEncoder.class,
                                        "TileEntityEncoder");
        GameRegistry.registerTileEntity(TileEntityNanoInnervator.class,
                                        "TileEntityNanoInnervator");
        GameRegistry.registerTileEntity(TileEntityNanoDismantler.class,
                                        "TileEntityNanoDeconstructor");
        GameRegistry.registerTileEntity(TileEntityNanoFabricator.class,
                                        "TileEntityNanoFabricator");
        GameRegistry.registerTileEntity(TileEntityBaseEntityNanoEnmesher
                                                .class,
                                        "TileEntityNanoEnmesher"
        );
        GameRegistry.registerTileEntity(TileEntityBaseEntityNanoHorologe
                                                .class,
                                        "TileEntityNanoHorologe"
        );
        GameRegistry.registerTileEntity(TileEntityFemtoImpulser.class,
                                        "TileEntityFemtoImpulser");
        GameRegistry.registerTileEntity(TileEntityFemtoRepurposer.class,
                                        "TileEntityFemtoRepurposer");
        GameRegistry.registerTileEntity(TileEntityFemtoCoagulator.class,
                                        "TileEntityFemtoCoagulator");
        GameRegistry.registerTileEntity(TileEntityFemtoEntangler.class,
                                        "TileEntityFemtoEntangler");
        GameRegistry.registerTileEntity(TileEntityFemtoChronoshifter.class,
                                        "TileEntityFemtoChronoshifter");
        GameRegistry.registerTileEntity(TileEntityMicroCable.class,
                                        "TileEntityMicroCable");
        GameRegistry.registerTileEntity(TileEntityNanoCable.class,
                                        "TileEntityNanoCable");
        GameRegistry.registerTileEntity(TileEntityFemtoCable.class,
                                        "TileEntityFemtoCable");
        GameRegistry.registerTileEntity(TileEntityMicroCube.class,
                                        "TileEntityMicroCube");
        GameRegistry.registerTileEntity(TileEntityNanoCubeFrame.class,
                                        "TileEntityNanoCubeFrame");
        GameRegistry.registerTileEntity(TileEntityNanoCubePort.class,
                                        "TileEntityNanoCubePort");
        GameRegistry.registerTileEntity(TileEntityFemtoCubeFrame.class,
                                        "TileEntityFemtoCubeFrame");
        GameRegistry.registerTileEntity(TileEntityFemtoCubeChassis.class,
                                        "TileEntityFemtoCubeChassis");
        GameRegistry.registerTileEntity(TileEntityFemtoCubePort.class,
                                        "TileEntityFemtoCubePort");
        GameRegistry.registerTileEntity(TileEntityFemtoStellaratorCore.class,
                                        "TileEntityFemtoStellaratorCore");
        GameRegistry.registerTileEntity
                (TileEntityFemtoStellaratorOpticalMaser.class,
                 "TileEntityFemtoStellaratorOpticalMaser");
        GameRegistry.registerTileEntity(TileEntityFemtoStellaratorHousing
                                                .class,
                                        "TileEntityFemtoStellaratorHousing"
        );
        GameRegistry.registerTileEntity(TileEntityFemtoStellaratorFocus
                                                .class,
                                        "TileEntityFemtoStellaratorFocus"
        );
        GameRegistry.registerTileEntity(TileEntityVacuumTube.class,
                                        "TileEntityVacuumTube");
        GameRegistry.registerTileEntity(TileEntityAtmosphericChargingBase.class,
                                        "TileEntityAtmosphericChargingBase");
        GameRegistry.registerTileEntity(TileEntitySuctionPipe.class,
                                        "TileEntitySuctionPipe");

        GameRegistry.registerTileEntity(TileEntityOrbitalEqualizer.class,
                                        "blockOrbitalEqualizer");
        GameRegistry.registerTileEntity(TileEntityNullEqualizer.class,
                                        "blockNullEqualizer");

        GameRegistry.registerTileEntity(TileEntityPlasma.class,
                                        "TileEntityPlasma");
    }

    public void registerBlockRenderers() {
    }

    public void registerTickHandlers() {
    }
}
