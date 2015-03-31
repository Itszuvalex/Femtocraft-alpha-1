package com.itszuvalex.femtocraft.api.managers

import java.util
import java.util.UUID

/**
 * Created by Chris on 1/2/2015.
 */
trait IAssistantManager {

  /**
   *
   * @param owner
   * @param user
   * @return True if user is an assistant of owner
   */
  def isPlayerAssistant(owner: UUID, user: UUID): Boolean

  /**
   *
   * @param owner
   * @return A collection of assistant's getCommandSenderName
   */
  def getPlayerAssistants(owner: UUID): util.Collection[UUID]

  /**
   *
   * @param owner Player who owns the list of assistants
   * @param user Player who will become owner's assistant.
   */
  def addAssistantTo(owner: UUID, user: UUID): Unit

  /**
   *
   * @param owner Player who owns the list of assistants
   * @param user Player who will no longer be owner's assistant.
   */
  def removeAssistantFrom(owner: UUID, user: UUID): Unit

}
