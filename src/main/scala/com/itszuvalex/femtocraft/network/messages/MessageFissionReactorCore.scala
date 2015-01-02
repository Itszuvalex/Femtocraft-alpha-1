package com.itszuvalex.femtocraft.network.messages

import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore
import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf
import net.minecraftforge.common.DimensionManager

/**
 * Created by Christopher Harris (Itszuvalex) on 10/22/14.
 */
class MessageFissionReactorCore(private var x: Int, private var y: Int, private var z: Int, private var dim: Int, private var action: Byte) extends IMessage with IMessageHandler[MessageFissionReactorCore, IMessage] {
  def this() = this(0, 0, 0, 0, 0)

  override def fromBytes(buf: ByteBuf): Unit = {
    x = buf readInt()
    y = buf readInt()
    z = buf readInt()
    dim = buf readInt()
    action = buf readByte()
  }

  override def toBytes(buf: ByteBuf): Unit = {
    buf writeInt x
    buf writeInt y
    buf writeInt z
    buf writeInt dim
    buf writeByte action
  }

  override def onMessage(message: MessageFissionReactorCore, ctx: MessageContext): IMessage = {
    val world = DimensionManager.getWorld(message.dim)
    if (world != null) {
      world.getTileEntity(message.x, message.y, message.z) match {
        case core: TileEntityNanoFissionReactorCore =>
          core.handleAction(message.action)
        case _                                      =>
      }
    }
    null
  }
}
