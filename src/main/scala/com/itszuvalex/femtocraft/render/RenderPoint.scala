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
//class RenderPoint(var x: Float, var y: Float, var z: Float) {
//  def this() = this(0, 0, 0)
//
//  def rotatedOnXAxis(rot: Double, yrotoffset: Float, zrotoffset: Float) = copy.rotateOnXAxis(rot, yrotoffset, zrotoffset)
//
//  def rotateOnXAxis(rot: Double, yrotoffset: Float, zrotoffset: Float): RenderPoint = {
//    if (rot == 0) {
//      return this
//    }
//    val ym = y
//    val zm = z
//    y = ((ym - yrotoffset) * Math.cos(rot) - (zm - zrotoffset) * Math.sin(rot)).toFloat + yrotoffset
//    z = ((ym - yrotoffset) * Math.sin(rot) + (zm - zrotoffset) * Math.cos(rot)).toFloat + zrotoffset
//    this
//  }
//
//  def copy = new RenderPoint(x, y, z)
//
//  def rotatedOnYAxis(rot: Double, xrotoffset: Float, zrotoffset: Float) = copy.rotateOnYAxis(rot, xrotoffset, zrotoffset)
//
//  def rotateOnYAxis(rot: Double, xrotoffset: Float, zrotoffset: Float): RenderPoint = {
//    if (rot == 0) {
//      return this
//    }
//    val xm = x
//    val zm = z
//    z = ((zm - zrotoffset) * Math.cos(rot) - (xm - xrotoffset) * Math.sin(rot)).toFloat + xrotoffset
//    x = ((zm - zrotoffset) * Math.sin(rot) + (xm - xrotoffset) * Math.cos(rot)).toFloat + zrotoffset
//    this
//  }
//
//  def rotatedOnZAxis(rot: Double, xrotoffset: Float, yrotoffset: Float) = copy.rotateOnZAxis(rot, xrotoffset, yrotoffset)
//
//  def rotateOnZAxis(rot: Double, xrotoffset: Float, yrotoffset: Float): RenderPoint = {
//    if (rot == 0) {
//      return this
//    }
//    val xm = x
//    val ym = y
//    x = (((xm - xrotoffset) * Math.cos(rot) - (ym - yrotoffset) * Math.sin(rot)) + xrotoffset).toFloat
//    y = ((xm - xrotoffset) * Math.sin(rot) + (ym - yrotoffset) * Math.cos(rot) + yrotoffset).toFloat
//    this
//  }
//}
