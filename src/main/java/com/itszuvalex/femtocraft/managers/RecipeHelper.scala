package com.itszuvalex.femtocraft.managers

/**
 * Created by Itszuvalex on 12/20/14.
 */
object RecipeHelper {

    implicit def boxChar(char: Char): Character = {
      new Character(char)
    }

  implicit def boxArray(array: Array[Any]): Array[Object] = array.map { case c: Char => c.asInstanceOf[Character]; case a: Object => a}

  implicit class boxedArray(array: Array[Any]) {
    def box: Array[Object] = array
  }

  //  implicit def boxArray(array:Array[Any]) : Array[Object] = array.map {case o: Object => o}
}
