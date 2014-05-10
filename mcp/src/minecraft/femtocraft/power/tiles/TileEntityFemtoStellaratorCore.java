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

package femtocraft.power.tiles;

import femtocraft.core.multiblock.IMultiBlockComponent;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.core.tiles.TileEntityBase;
import femtocraft.power.plasma.*;
import femtocraft.power.plasma.volatility.IVolatilityEvent;
import femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/10/14.
 */
public class TileEntityFemtoStellaratorCore extends TileEntityBase implements
                                                                   IFusionReactorCore, IMultiBlockComponent {
    @FemtocraftDataUtils.Saveable
    private FusionReactorCore core;
    @FemtocraftDataUtils.Saveable
    private MultiBlockInfo info;

    public TileEntityFemtoStellaratorCore() {
        core = new FusionReactorCore();
        info = new MultiBlockInfo();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        update(worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public int getGuiID() {
        return super.getGuiID();
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public boolean isSelfSustaining() {
        return core.isSelfSustaining();
    }

    @Override
    public boolean isIgniting() {
        return core.isIgniting();
    }

    @Override
    public int getIgnitionProcessWindow() {
        return core.getIgnitionProcessWindow();
    }

    @Override
    public int getReactionInstability() {
        return core.getReactionInstability();
    }

    @Override
    public long getReactionTemperature() {
        return core.getReactionTemperature();
    }

    @Override
    public long getCoreEnergy() {
        return core.getCoreEnergy();
    }

    @Override
    public boolean consumeCoreEnergy(long energy) {
        return core.consumeCoreEnergy(energy);
    }

    @Override
    public long contributeCoreEnergy(long energy) {
        return core.contributeCoreEnergy(energy);
    }

    @Override
    public Collection<IFusionReactorComponent> getComponents() {
        return core.getComponents();
    }

    @Override
    public boolean addComponent(IFusionReactorComponent component) {
        return core.addComponent(component);
    }

    @Override
    public boolean removeComponent(IFusionReactorComponent component) {
        return core.removeComponent(component);
    }

    @Override
    public void beginIgnitionProcess(IFusionReactorCore core) {
        core.beginIgnitionProcess(core);
    }

    @Override
    public void endIgnitionProcess(IFusionReactorCore core) {
        core.endIgnitionProcess(core);
    }

    @Override
    public IFusionReactorCore getCore() {
        return core;
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        return info.formMultiBlock(world, x, y, z);
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        return info.breakMultiBlock(world, x, y, z);
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    @Override
    public IPlasmaContainer getInput() {
        return core.getInput();
    }

    @Override
    public IPlasmaContainer getOutput() {
        return core.getOutput();
    }

    @Override
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        return core.setInput(container, dir);
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        return core.setOutput(container, dir);
    }

    @Override
    public ForgeDirection getInputDir() {
        return core.getInputDir();
    }

    @Override
    public ForgeDirection getOutputDir() {
        return core.getOutputDir();
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        return core.addFlow(flow);
    }

    @Override
    public Collection<IPlasmaFlow> getFlows() {
        return core.getFlows();
    }

    @Override
    public boolean removeFlow(IPlasmaFlow flow) {
        return core.removeFlow(flow);
    }

    @Override
    public int getMaxFlows() {
        return core.getMaxFlows();
    }

    @Override
    public void update(World world, int x, int y, int z) {
        core.update(world, x, y, z);
    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {
        core.onVolatilityEvent(event);
    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {
        core.onPostVolatilityEvent(event);
    }

    @Override
    public int getTemperatureRating() {
        return core.getTemperatureRating();
    }

    @Override
    public int getStabilityRating() {
        return core.getStabilityRating();
    }
}
