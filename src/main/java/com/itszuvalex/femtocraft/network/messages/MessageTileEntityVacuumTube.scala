//package com.itszuvalex.femtocraft.network.messages
//
//import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube
//import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
//import io.netty.buffer.ByteBuf
//import net.minecraft.client.Minecraft
//
///**
// * Created by Christopher Harris (Itszuvalex) on 7/4/14.
// */
//class MessageTileEntityVacuumTube(tube: TileEntityVacuumTube) extends IMessage with IMessageHandler[MessageTileEntityVacuumTube, IMessage] {
//  private var dim  : Int  = if (tube != null) tube.getWorldObj.provider.dimensionId else 0
//  private var x    : Int  = if (tube != null) tube.xCoord else 0
//  private var y    : Int  = if (tube != null) tube.yCoord else 0
//  private var z    : Int  = if (tube != null) tube.zCoord else 0
//  private var conns: Byte = if (tube != null) tube.generateConnectionMask else 0
//  private var fill : Byte = if (tube != null) tube.generateItemMask else 0
//
//  def this() {
//    this(null)
//  }
//
//  def fromBytes(buf: ByteBuf) {
//    dim = buf.readInt
//    x = buf.readInt
//    y = buf.readInt
//    z = buf.readInt
//    conns = buf.readByte
//    fill = buf.readByte
//  }
//
//  def toBytes(buf: ByteBuf) {
//    buf.writeInt(dim)
//    buf.writeInt(x)
//    buf.writeInt(y)
//    buf.writeInt(z)
//    buf.writeByte(conns)
//    buf.writeByte(fill)
//  }
//
//  def onMessage(message: MessageTileEntityVacuumTube, ctx: MessageContext): IMessage = {
//    if (Minecraft.getMinecraft.theWorld.provider.dimensionId == message.dim) {
//      Minecraft.getMinecraft.theWorld.getTileEntity(message.x, message.y, message.z) match {
//        case vacuumTube: TileEntityVacuumTube =>
//          vacuumTube.parseConnectionMask(message.conns)
//          vacuumTube.parseItemMask(message.fill)
//        case _                                =>
//      }
//    }
//    null
//  }
//}
