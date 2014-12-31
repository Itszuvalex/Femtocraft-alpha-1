package com.itszuvalex.femtocraft

/**
 * Created by Itszuvalex on 12/31/14.
 */
object Version {
  val MASSIVE    = "@MASSIVE@"
  val MAJOR      = "@MAJOR@"
  val MINOR      = "@MINOR@"
  val BUILD      = "@BUILD_NUMBER@"
  val MC_VERSION = "@MC_VERSION@"

  def fullVersion = MC_VERSION + '-' + MASSIVE + '.' + MAJOR + '.' + MINOR + '-' + BUILD
}
