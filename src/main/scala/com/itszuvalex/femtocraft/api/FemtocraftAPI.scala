package com.itszuvalex.femtocraft.api


import com.itszuvalex.femtocraft.api.managers.research.IResearchManager
import com.itszuvalex.femtocraft.api.managers.{IAssemblerRecipeManager, IAssistantManager, IPlasmaManager, IPowerAlgorithm}
import org.apache.logging.log4j.{Level, LogManager}

/**
 * Created by Chris on 1/2/2015.
 *
 * Provides methods to attempt to hook into Femtocraft.
 *
 * All objects are cached once hooked.
 */
object FemtocraftAPI {
  private lazy val powerAlgorithm         = getFemtocraftFieldGetter[IPowerAlgorithm](powerAlgorithmGetter)
  private lazy val researchManager        = getFemtocraftFieldGetter[IResearchManager](researchManagerGetter)
  private lazy val assistantManager       = getFemtocraftFieldGetter[IAssistantManager](assistantManagerGetter)
  private lazy val plasmaManager          = getFemtocraftFieldGetter[IPlasmaManager](plasmaManagerGetter)
  private lazy val assemblerRecipeManager = getFemtocraftFieldGetter[IAssemblerRecipeManager](assemblerRecipeManagerGetter)
  private lazy val femtocraft             = attemptHookFemtocraft()
  val FemtocraftID = "Femtocraft"
  val apiLogger    = LogManager.getLogger(FemtocraftID + "API")
  val femtoLogger  = LogManager.getLogger(FemtocraftID)
  private val powerAlgorithmGetter         = "powerAlgorithm"
  private val researchManagerGetter        = "researchManager"
  private val assistantManagerGetter       = "assistantManager"
  private val plasmaManagerGetter          = "plasmaManager"
  private val assemblerRecipeManagerGetter = "assemblerRecipeManager"
  private val femtocraftClasspath          = "com.itszuvalex.femtocraft.Femtocraft"

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
   * @return AssistantManager singleton responsible for storing, saving, and loading assistant information for all players.
   */
  def getAssistantManager = try assistantManager
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft Assistant Manager"); null}

  /**
   *
   * @return PlasmaManager singleton with helper functions for interacting with plasma.
   */
  def getPlasmaManager = try plasmaManager
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft Plasma Manager"); null}

  /**
   *
   * @return AssemblerRecipeManager singleton responsbie for storing, saving, and loading AssemblerRecipe information
   */
  def getAssemblerRecipeManager = try assemblerRecipeManager
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft Assembler Recipe Manager"); null}

  /**
   * Tell the API to attempt to search for Femtocraft now.  Otherwise, will attempt to hook classes as they're needed.
   *
   * @return True if hooking successful, false otherwise.
   */
  def hookFemtocraft(): Boolean = {
    apiLogger.log(Level.INFO, "Attempting to hook Femtocraft.")
    try {
      femtocraft
      researchManager
      powerAlgorithm
      assistantManager
      plasmaManager
      apiLogger.log(Level.INFO, "Successfully hooked Femtocraft!")
      true
    }
    catch {
      case e: Throwable =>
        apiLogger.log(Level.ERROR, "Failed to hook Femtocraft classes.")
        e.printStackTrace()
        false
    }
  }

  private def getFemtocraft = try femtocraft
  catch {case _: Throwable => apiLogger.log(Level.ERROR, "Failed to hook Femtocraft."); null}

  private def getFemtocraftFieldGetter[F >: Null](getter: String): F =
    femtocraft.getMethod(getter, Array[Class[_]](): _*).invoke(null, Array(): _*).asInstanceOf[F]


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
