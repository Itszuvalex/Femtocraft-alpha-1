package com.itszuvalex.femtocraft.api.power.plasma

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
trait IFusionReactorComponent extends IPlasmaContainer {
  /**
   * Called when the ignition process begins.  Likely started by a player.
   *
   * @param core Core this is a component of.
   */
  def beginIgnitionProcess(core: IFusionReactorCore)

  /**
   * Called when the ignition process ends, whether it was successful or not.
   *
   * @param core Core this is a component of.
   */
  def endIgnitionProcess(core: IFusionReactorCore)

  /**
   * @return Core this is a component of
   */
  def getCore: IFusionReactorCore

  /**
   * Called when a self-sustaining reaction is ended, for any reason.
   *
   * @param core Core this is a component of.
   */
  def onReactionStop(core: IFusionReactorCore)
}
