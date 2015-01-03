package com.itszuvalex.femtocraft.api.managers.research

import java.util

import com.itszuvalex.femtocraft.api.research.ITechnology

/**
 * Created by Chris on 1/2/2015.
 */
trait IResearchManager {

  def addTechnology(tech: ITechnology): Boolean

  def getTechnologies: util.Collection[ITechnology]

  def removeTechnology(tech: ITechnology): Boolean

  def getTechnology(name: String): ITechnology

  def hasPlayerDiscoveredTechnology(username: String, tech: ITechnology): Boolean

  def hasPlayerDiscoveredTechnology(username: String, tech: String): Boolean

  def hasPlayerResearchedTechnology(username: String, tech: ITechnology): Boolean

  def hasPlayerResearchedTechnology(username: String, tech: String): Boolean

  def removePlayerResearch(username: String): Boolean

  def getPlayerResearch(username: String): IPlayerResearch

}
