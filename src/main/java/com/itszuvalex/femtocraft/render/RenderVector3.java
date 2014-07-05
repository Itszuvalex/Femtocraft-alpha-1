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

package com.itszuvalex.femtocraft.render;

import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/16/14.
 */
public class RenderVector3 implements ISaveable {
    @FemtocraftDataUtils.Saveable
    double x;
    @FemtocraftDataUtils.Saveable
    double y;
    @FemtocraftDataUtils.Saveable
    double z;

    public RenderVector3() {
        this(0, 0, 0);
    }

    public RenderVector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RenderVector3(RenderPoint a, RenderPoint b) {
        this(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public RenderVector3 normalized() {
        RenderVector3 ret = new RenderVector3(x, y, z);
        double mag = ret.magnitude();
        ret.x = ret.x / mag;
        ret.y = ret.y / mag;
        ret.z = ret.z / mag;
        return ret;
    }

    public double magnitude() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public RenderVector3 crossProduct(RenderVector3 vector) {

        /*
                x*(u_y*v_z-u_z*v_y)+y*(u_z*v_x-u_x*v_z)+z*(u_x*v_y-u_y*v_x),
    */

        RenderVector3 ret = new RenderVector3();
        ret.x = y * vector.z - z * vector.y;
        ret.y = z * vector.x - x * vector.z;
        ret.z = x * vector.y - y * vector.x;
        return ret;

    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                FemtocraftDataUtils.EnumSaveType.WORLD);
    }
}
