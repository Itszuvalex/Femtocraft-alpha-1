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

public class RenderPoint {
    public float x;
    public float y;
    public float z;

    public RenderPoint() {
        x = 0;
        y = 0;
        z = 0;
    }

    public RenderPoint(float x_, float y_, float z_) {
        x = x_;
        y = y_;
        z = z_;
    }

    public RenderPoint copy() {
        return new RenderPoint(x, y, z);
    }

    public RenderPoint rotateOnXAxis(double rot, float yrotoffset, float zrotoffset) {
        if (rot == 0) {
            return this;
        }

        float ym = this.y;
        float zm = this.z;
        this.y = (float) ((ym - yrotoffset) * Math.cos(rot) - (zm - zrotoffset)
                                                              * Math.sin(rot))
                 + yrotoffset;
        this.z = (float) ((ym - yrotoffset) * Math.sin(rot) + (zm - zrotoffset)
                                                              * Math.cos(rot))
                 + zrotoffset;
        return this;
    }

    public RenderPoint rotateOnYAxis(double rot, float xrotoffset, float zrotoffset) {
        if (rot == 0) {
            return this;
        }

        float xm = this.x;
        float zm = this.z;
        this.z = (float) ((zm - zrotoffset) * Math.cos(rot) - (xm - xrotoffset)
                                                              * Math.sin(rot))
                 + xrotoffset;
        this.x = (float) ((zm - zrotoffset) * Math.sin(rot) + (xm - xrotoffset)
                                                              * Math.cos(rot))
                 + zrotoffset;
        return this;
    }

    public RenderPoint rotateOnZAxis(double rot, float xrotoffset, float yrotoffset) {
        if (rot == 0) {
            return this;
        }

        float xm = this.x;
        float ym = this.y;
        this.x = (float) (((xm - xrotoffset) * Math.cos(rot) - (ym - yrotoffset)
                                                               * Math.sin(rot)) + xrotoffset);
        this.y = (float) ((xm - xrotoffset) * Math.sin(rot) + (ym - yrotoffset)
                                                              * Math.cos(rot) + yrotoffset);
        return this;
    }

    public RenderPoint rotatedOnXAxis(double rot, float yrotoffset, float zrotoffset) {
        return copy().rotateOnXAxis(rot, yrotoffset, zrotoffset);
    }

    public RenderPoint rotatedOnYAxis(double rot, float xrotoffset, float zrotoffset) {
        return copy().rotateOnYAxis(rot, xrotoffset, zrotoffset);
    }

    public RenderPoint rotatedOnZAxis(double rot, float xrotoffset, float yrotoffset) {
        return copy().rotateOnZAxis(rot, xrotoffset, yrotoffset);
    }
}
