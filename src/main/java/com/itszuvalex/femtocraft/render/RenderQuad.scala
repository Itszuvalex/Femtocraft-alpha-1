///*
// * ******************************************************************************
// *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// *  * Itszuvalex@gmail.com
// *  *
// *  * This program is free software; you can redistribute it and/or
// *  * modify it under the terms of the GNU General Public License
// *  * as published by the Free Software Foundation; either version 2
// *  * of the License, or (at your option) any later version.
// *  *
// *  * This program is distributed in the hope that it will be useful,
// *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  * GNU General Public License for more details.
// *  *
// *  * You should have received a copy of the GNU General Public License
// *  * along with this program; if not, write to the Free Software
// *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// *  *****************************************************************************
// */
//package com.itszuvalex.femtocraft.render
//
//import net.minecraft.client.renderer.Tessellator
//import net.minecraft.util.IIcon
//
//class RenderQuad(var a: RenderPoint,
//                 var b: RenderPoint,
//                 var c: RenderPoint,
//                 var d: RenderPoint,
//                 var icon: IIcon,
//                 var minU: Float,
//                 var maxU: Float,
//                 var minV: Float,
//                 var maxV: Float) {
//
//  private def this(a: RenderPoint, b: RenderPoint, c: RenderPoint, d: RenderPoint) =
//    this(a, b, c, d, null)
//
//  def this(a: RenderPoint, b: RenderPoint, c: RenderPoint, d: RenderPoint, icon: IIcon) =
//    this(a, b, c, d, icon, icon.getMinU, icon.getMaxU, icon.getMinV, icon.getMaxV)
//
//  def reverse = {
//    var temp = a
//    a = d
//    d = temp
//    temp = c
//    c = b
//    b = temp
//    this
//  }
//
//  def reversed = new RenderQuad(d.copy, c.copy, b.copy, a.copy, icon, minU, maxU, minV, maxV)
//
//  def flippedU = copy.flipU
//
//  def flipU = {
//    val temp = minU
//    minU = maxU
//    maxU = temp
//    this
//  }
//
//  def copy = new RenderQuad(a.copy, b.copy, c.copy, d.copy, icon, minU, maxU, minV, maxV)
//
//  def flippedV = copy.flipV
//
//  def flipV = {
//    val temp = minV
//    minV = maxV
//    maxV = temp
//    this
//  }
//
//  def rotatePointsCounterClockwise: RenderQuad = {
//    val temp = a
//    a = b
//    b = c
//    c = d
//    d = temp
//    this
//  }
//
//  def rotatePointsClockwise: RenderQuad = {
//    val temp = d
//    d = c
//    c = b
//    b = a
//    a = temp
//    this
//  }
//
//  def rotatedOnXAxis(rot: Double, yrotoffset: Float, zrotoffset: Float) = copy.rotateOnXAxis(rot, yrotoffset, zrotoffset)
//
//  def rotateOnXAxis(rot: Double, yrotoffset: Float, zrotoffset: Float): RenderQuad = {
//    a.rotateOnXAxis(rot, yrotoffset, zrotoffset)
//    b.rotateOnXAxis(rot, yrotoffset, zrotoffset)
//    c.rotateOnXAxis(rot, yrotoffset, zrotoffset)
//    d.rotateOnXAxis(rot, yrotoffset, zrotoffset)
//    this
//  }
//
//  def rotatedOnYAxis(rot: Double, xrotoffset: Float, zrotoffset: Float) = copy.rotateOnYAxis(rot, xrotoffset, zrotoffset)
//
//  def rotateOnYAxis(rot: Double, xrotoffset: Float, zrotoffset: Float): RenderQuad = {
//    a.rotateOnYAxis(rot, xrotoffset, zrotoffset)
//    b.rotateOnYAxis(rot, xrotoffset, zrotoffset)
//    c.rotateOnYAxis(rot, xrotoffset, zrotoffset)
//    d.rotateOnYAxis(rot, xrotoffset, zrotoffset)
//    this
//  }
//
//  def rotatedOnZAxis(rot: Double, xrotoffset: Float, yrotoffset: Float): RenderQuad = {
//    copy.rotateOnZAxis(rot, xrotoffset, yrotoffset)
//  }
//
//  def rotateOnZAxis(rot: Double, xrotoffset: Float, yrotoffset: Float): RenderQuad = {
//    a.rotateOnZAxis(rot, xrotoffset, yrotoffset)
//    b.rotateOnZAxis(rot, xrotoffset, yrotoffset)
//    c.rotateOnZAxis(rot, xrotoffset, yrotoffset)
//    d.rotateOnZAxis(rot, xrotoffset, yrotoffset)
//    this
//  }
//
//  def draw() {
//    val tes = Tessellator.instance
//    val normal = getNormal
//    tes.setNormal(normal.x.toFloat, normal.y.toFloat, normal.z.toFloat)
//    tes.addVertexWithUV(a.x, a.y, a.z, minU, maxV)
//    tes.addVertexWithUV(b.x, b.y, b.z, minU, minV)
//    tes.addVertexWithUV(c.x, c.y, c.z, maxU, minV)
//    tes.addVertexWithUV(d.x, d.y, d.z, maxU, maxV)
//  }
//
//  def getNormal = new RenderVector3(c, b).crossProduct(new RenderVector3(a, b)).normalized
//}
