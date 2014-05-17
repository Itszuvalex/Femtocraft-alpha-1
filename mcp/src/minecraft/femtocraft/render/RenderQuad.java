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

package femtocraft.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public class RenderQuad {
    public RenderPoint a;
    public RenderPoint b;
    public RenderPoint c;
    public RenderPoint d;
    public Icon icon;
    public float minU;
    public float maxU;
    public float minV;
    public float maxV;

    // This will cause crashes, cause I'm stupid
    private RenderQuad(RenderPoint a, RenderPoint b, RenderPoint c, RenderPoint d) {
        this(a, b, c, d, null);
    }

    public RenderQuad(RenderPoint a, RenderPoint b, RenderPoint c, RenderPoint d, Icon icon) {
        this(a, b, c, d, icon, icon.getMinU(), icon.getMaxU(), icon.getMinV(),
             icon.getMaxV());
    }

    public RenderQuad(RenderPoint a, RenderPoint b, RenderPoint c, RenderPoint d, Icon icon, float minU,
                      float maxU, float minV, float maxV) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.icon = icon;
        this.minU = minU;
        this.maxU = maxU;
        this.minV = minV;
        this.maxV = maxV;
    }

    public RenderQuad copy() {
        return new RenderQuad(a.copy(), b.copy(), c.copy(), d.copy(), icon, minU,
                              maxU, minV, maxV);
    }

    public RenderQuad reverse() {
        RenderPoint temp = a;
        a = d;
        d = temp;

        temp = c;
        c = b;
        b = temp;

        return this;
    }

    public RenderQuad reversed() {
        return new RenderQuad(d.copy(), c.copy(), b.copy(), a.copy(), icon, minU,
                              maxU, minV, maxV);
    }

    public RenderQuad flipU() {
        float temp = minU;
        minU = maxU;
        maxU = temp;
        return this;
    }

    public RenderQuad flippedU() {
        return copy().flipU();
    }

    public RenderQuad flipV() {
        float temp = minV;
        minV = maxV;
        maxV = temp;
        return this;
    }

    public RenderQuad flippedV() {
        return copy().flipV();
    }

    public RenderVector3 getNormal() {
        return new RenderVector3(c, b).crossProduct(new RenderVector3(a,
                                                                      b)).normalized();
    }


    public RenderQuad rotateOnXAxis(double rot, float yrotoffset, float zrotoffset) {
        a.rotateOnXAxis(rot, yrotoffset, zrotoffset);
        b.rotateOnXAxis(rot, yrotoffset, zrotoffset);
        c.rotateOnXAxis(rot, yrotoffset, zrotoffset);
        d.rotateOnXAxis(rot, yrotoffset, zrotoffset);
        return this;
    }

    public RenderQuad rotateOnYAxis(double rot, float xrotoffset, float zrotoffset) {
        a.rotateOnYAxis(rot, xrotoffset, zrotoffset);
        b.rotateOnYAxis(rot, xrotoffset, zrotoffset);
        c.rotateOnYAxis(rot, xrotoffset, zrotoffset);
        d.rotateOnYAxis(rot, xrotoffset, zrotoffset);
        return this;
    }

    public RenderQuad rotateOnZAxis(double rot, float xrotoffset, float yrotoffset) {
        a.rotateOnZAxis(rot, xrotoffset, yrotoffset);
        b.rotateOnZAxis(rot, xrotoffset, yrotoffset);
        c.rotateOnZAxis(rot, xrotoffset, yrotoffset);
        d.rotateOnZAxis(rot, xrotoffset, yrotoffset);
        return this;
    }

    public RenderQuad rotatedOnXAxis(double rot, float yrotoffset, float zrotoffset) {
        return copy().rotateOnXAxis(rot, yrotoffset, zrotoffset);
    }

    public RenderQuad rotatedOnYAxis(double rot, float xrotoffset, float zrotoffset) {
        return copy().rotateOnYAxis(rot, xrotoffset, zrotoffset);
    }

    public RenderQuad rotatedOnZAxis(double rot, float xrotoffset, float yrotoffset) {
        return copy().rotateOnZAxis(rot, xrotoffset, yrotoffset);
    }

    public void draw() {
        Tessellator tes = Tessellator.instance;
        RenderVector3 normal = getNormal();
        tes.setNormal((float) normal.x, (float) normal.y, (float) normal.z);
        tes.addVertexWithUV(a.x, a.y, a.z, minU, maxV);
        tes.addVertexWithUV(b.x, b.y, b.z, minU, minV);
        tes.addVertexWithUV(c.x, c.y, c.z, maxU, minV);
        tes.addVertexWithUV(d.x, d.y, d.z, maxU, maxV);
    }
}
