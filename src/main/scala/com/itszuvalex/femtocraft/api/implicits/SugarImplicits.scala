package com.itszuvalex.femtocraft.api.implicits

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
object SugarImplicits {

  implicit class AnyRefImplicits(a:AnyRef) {
    def IfNotNull[R](f: => R) : Unit = if(a != null) f
  }
}
