package com.itszuvalex.femtocraft.api.events

import com.itszuvalex.femtocraft.api.research.ITechnology
import cpw.mods.fml.common.eventhandler.{Cancelable, Event}

import scala.beans.BeanProperty

object EventTechnology {

  /**
   * Called when Technologies are being added to the list of Technologies.  Cancelling prevents the Technology from being added.
   * Use to prevent registration of a specific technology, but know that other technologies may rely upon that Technology's name.  In this case, either be certain it's not,
   * or be sure to replace that technology with your own of the same name.
   *
   * @param tech Technology being added to the list of all technologies.
   */
  @Cancelable
  class TechnologyAddedEvent(@BeanProperty val tech: ITechnology) extends Event with EventTechnology

  /**
   * Called whenever a player will discover a new technology.  Cancel to prevent him discovering it.  This will prevent it from
   * being put in the player's IResearchPlayer store.
   *
   * @param username getCommandSenderName of the player who will discover this technology.
   * @param tech Technology that the player will discover.
   */
  @Cancelable
  class TechnologyDiscoveredEvent(@BeanProperty val username: String, @BeanProperty val tech: ITechnology)
    extends Event with EventTechnology

  /**
   * Called whenever a player will research a new technology.  Cancel to prevent him from researching it.  In most cases, the
   * research will have first already been discovered by the player.
   *
   * @param username getCommandSenderName of the player who will research this technology.
   * @param tech Technology that the player will research.
   */
  @Cancelable
  class TechnologyResearchedEvent(@BeanProperty val username: String, @BeanProperty val tech: ITechnology)
    extends Event with EventTechnology

}

trait EventTechnology