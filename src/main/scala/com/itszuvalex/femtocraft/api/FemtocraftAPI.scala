package com.itszuvalex.femtocraft.api


import com.itszuvalex.femtocraft.api.managers.research.IResearchManager
import com.itszuvalex.femtocraft.api.managers.{IAssistantManager, IPowerAlgorithm}
import org.apache.logging.log4j.{Level, LogManager}

/**
 * Created by Chris on 1/2/2015.
 *
 * Provides methods to attempt to hook into Femtocraft.
 *
 * All objects are cached once hooked.
 */
object FemtocraftAPI {
  private lazy val powerAlgorithm   = getFemtocraftField[IPowerAlgorithm](powerAlgorithmGetter)
  private lazy val researchManager  = getFemtocraftField[IResearchManager](researchManagerGetter)
  private lazy val assistantManager = getFemtocraftField[IAssistantManager](assistantManagerGetter)
  private lazy val femtocraft       = attemptHookFemtocraft()
  val apiLogger   = LogManager.getLogger("FemtocraftAPI")
  val femtoLogger = LogManager.getLogger("Femtocraft")
  private val powerAlgorithmGetter   = "powerAlgorithm"
  private val researchManagerGetter  = "researchManager"
  private val assistantManagerGetter = "assistantManager"
  private val femtocraftClasspath    = "com.itszuvalex.femtocraft.Femtocraft"

  /**
   *
   * @return PowerAlgorithm singleton for use in distributing power from IPowerTileContainers
   */
  def getPowerAlgorithm = try powerAlgorithm
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft Power Algorithm"); null}

  /**
   *
   * @return ResearchManager singleton responsible for storing, saving, and loading research information for all players.
   */
  def getResearchManager = try researchManager
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft Research Manager"); null}

  /**
   *
   * @return AssistantManager singleton responsbie for storing, saving, and loading assistant information for all players.
   */
  def getAssistantManager = try assistantManager
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft Assistant Manager"); null}

  /**
   * Tell the API to attempt to search for Femtocraft now.  Otherwise, will attempt to hook classes as they're needed.
   *
   * @return True if hooking successful, false otherwise.
   */
  def hookFemtocraft(): Boolean = {
    try {
      femtocraft
      researchManager
      powerAlgorithm
      assistantManager
      true
    }
    catch {
      case e: ClassNotFoundException =>
        apiLogger.log(Level.ERROR, "Failed to hook Femtocraft classes.")
        false
    }
  }

  private def getFemtocraft = try femtocraft
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft."); null}

  private def getFemtocraftField[F >: Null](getter: String): F = {
    try {
      if (femtocraft == null) return null
      val method = femtocraft.getMethod(getter, Array[Class[_]](null): _*)
      method.invoke(null, Array(null): _*).asInstanceOf[F]
    }
    catch {
      case e: Throwable =>
        apiLogger.log(Level.ERROR, "Failed to hook " + getter)
        e.printStackTrace()
        null
    }
  }

  private def attemptHookFemtocraft(): Class[_] = {
    try {
      Class.forName(femtocraftClasspath)
    }
    catch {
      case e: ClassNotFoundException =>
        apiLogger.log(Level.ERROR, "Class " + femtocraftClasspath + " not found.  Is Femtocraft loaded?")
        null
    }
  }
}
