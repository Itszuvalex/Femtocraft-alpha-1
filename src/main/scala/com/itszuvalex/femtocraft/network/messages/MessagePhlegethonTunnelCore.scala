package com.itszuvalex.femtocraft.network.messages

import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore
import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf
import net.minecraftforge.common.DimensionManager

/**
 * Created by Christopher Harris (Itszuvalex) on 10/22/14.
 */
class MessagePhlegethonTunnelCore(private var x: Int, private var y: Int, private var z: Int, private var dim: Int, private var action: Boolean) extends IMessage with IMessageHandler[MessagePhlegethonTunnelCore, IMessage] {
  def this() = this(0, 0, 0, 0, false)

  override def fromBytes(buf: ByteBuf): Unit = {
    x = buf readInt()
    y = buf readInt()
    z = buf readInt()
    dim = buf readInt()
    action = buf readBoolean()
  }

  override def toBytes(buf: ByteBuf): Unit = {
    buf writeInt x
    buf writeInt y
    buf writeInt z
    buf writeInt dim
    buf writeBoolean action
  }

  override def onMessage(message: MessagePhlegethonTunnelCore, ctx: MessageContext): IMessage = {
    val world = DimensionManager.getWorld(message.dim)
    if (world != null) {
      world.getTileEntity(message.x, message.y, message.z) match {
        case core: TileEntityPhlegethonTunnelCore =>
          core.handlePacket(message.action)
        case _                                    =>
      }
    }
    null
  }
}
