package com.itszuvalex.femtocraft.api.managers

import java.util

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
  def isPlayerAssistant(owner: String, user: String): Boolean

  /**
   *
   * @param owner
   * @return A map of assistant getCommandSenderName s to IAssistantPermissions
   */
  def getPlayerAssistants(owner: String): util.Collection[String]

  /**
   *
   * @param owner Player who owns the list of assistants
   * @param user Player who will become owner's assistant.
   */
  def addAssistantTo(owner: String, user: String): Unit

  /**
   *
   * @param owner Player who owns the list of assistants
   * @param user Player who will no longer be owner's assistant.
   */
  def removeAssistantFrom(owner: String, user: String): Unit

}
