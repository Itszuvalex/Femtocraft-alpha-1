package com.itszuvalex.femtocraft.api.implicits

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
object SugarImplicits {

  implicit class AnyRefImplicits[A <: AnyRef](a: A) {
    def DoIfNotNull[R](f: => R): Unit = if (a != null) f

    def IfNotNull[U](f: (A) => U): scala.Unit = if (a != null) f(a)
  }

}
