package com.itszuvalex.femtocraft.network.messages

import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf
import net.minecraftforge.common.DimensionManager

/**
 * Created by Christopher Harris (Itszuvalex) on 7/4/14.
 */
class MessageResearchConsoleStart(console: TileEntityResearchConsole) extends IMessage with IMessageHandler[MessageResearchConsoleStart, IMessage] {
  private var dim = if (console != null) console.getWorldObj.provider.dimensionId else 0
  private var x   = if (console != null) console.xCoord else 0
  private var y   = if (console != null) console.yCoord else 0
  private var z   = if (console != null) console.zCoord else 0

  def this() {
    this(null)
  }

  def fromBytes(buf: ByteBuf) {
    dim = buf.readInt
    x = buf.readInt
    y = buf.readInt
    z = buf.readInt
  }

  def toBytes(buf: ByteBuf) {
    buf.writeInt(dim)
    buf.writeInt(x)
    buf.writeInt(y)
    buf.writeInt(z)
  }

  def onMessage(message: MessageResearchConsoleStart, ctx: MessageContext): IMessage = {
    val worldServer = DimensionManager.getWorld(message.dim)
    if (worldServer != null) {
      worldServer.getTileEntity(message.x, message.y, message.z) match {
        case console: TileEntityResearchConsole =>
          if (!console.isResearching) {
            console.startWork()
          }
        case _                                  =>
      }
    }
    null
  }
}
