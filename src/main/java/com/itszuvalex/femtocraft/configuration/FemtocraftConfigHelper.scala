/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.configuration

import java.lang.reflect.Field
import java.util.regex.Pattern

import com.google.common.collect.HashBiMap
import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.implicits.ItemStackImplicits._
import net.minecraft.item.ItemStack
import net.minecraftforge.common.config.Configuration
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime._

/**
 * Created by Christopher Harris (Itszuvalex) on 9/10/14.
 */
object FemtocraftConfigHelper {

  final   val CLASS_CONSTANTS_KEY           = "Class Constants"
  final   val CATEGORY_SPLITTER_REPLACEMENT = '-'
  final   val CATEGORY_SPLITTER_CHAR        = Configuration.CATEGORY_SPLITTER.charAt(0)
  private val configStringEscapes           = HashBiMap.create[String, String]
  private val loaderMap                     = new mutable.HashMap[Class[_], FemtocraftConfigHelper.FieldLoader[_]]

  private abstract class FieldLoader[T] {
    def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration)

    def getValue(key: String, default: T, section: String, anno: Configurable, config: Configuration): T
  }


  private def addConfigEscape(toEscape: String, escape: String) = configStringEscapes.put(Pattern.quote(toEscape), Pattern.quote(escape))


  private def escapeConfigString(string: String): String = {
    var str = string
    configStringEscapes.keySet.foreach(escape => str = str.replaceAll(escape, configStringEscapes.get(escape)))
    str
  }

  private def unescapeConfigString(string: String): String = {
    val unEscape = configStringEscapes.inverse
    var str = string
    unEscape.keySet.foreach(unescape => str = str.replaceAll(unescape, unEscape.get(unescape)))
    str
  }

  def escapeCategorySplitter(string: String) = string.replace(CATEGORY_SPLITTER_CHAR, CATEGORY_SPLITTER_REPLACEMENT)

  def unescapeCategorySplitter(string: String) = string.replace(CATEGORY_SPLITTER_REPLACEMENT, CATEGORY_SPLITTER_CHAR)

  def loadClassFromConfig(configuration: Configuration, section: String, key: String, clazz: Class[_]) = {
//    var inst: AnyRef = null
//    try {
//      val rootMirror = universe.runtimeMirror(getClass.getClassLoader)
//      val classSymbol = rootMirror.classSymbol(clazz)
//      val moduleSymbol = classSymbol.companionSymbol.asModule
//      val moduleMirror = rootMirror.reflectModule(moduleSymbol)
//
//      if (moduleSymbol.annotations.exists(a => a.isInstanceOf[Configurable])) {
//        inst = moduleMirror.instance.asInstanceOf[clazz.type]
//      }
//    }
//    catch {
//      case e: Exception         =>
//        inst = null
//      case e: NoSuchMethodError =>
//        inst = null
//    }

    loadClassInstanceFromConfig(configuration, section, key, clazz, null)
  }

  def loadClassInstanceFromConfig(configuration: Configuration, section: String, key: String, clazz: Class[_], obj: AnyRef) {
    val fieldsList = new ArrayBuffer[Field]
    fieldsList appendAll clazz.getFields
    if (obj != null) {
      var superclass = clazz.getSuperclass
      while (superclass != null) {
        fieldsList appendAll superclass.getFields
        superclass = superclass.getSuperclass
      }
    }
    fieldsList.foreach(field => {
      if (!(field.getDeclaringClass.ne(clazz) && obj == null)) {
        val accessible = field.isAccessible
        if (!accessible) field.setAccessible(true)
        val canno = field.getAnnotation(classOf[Configurable])
        if (canno != null) {
          val loader = loaderMap.get(field.getType).orNull
          if (loader != null) {
            try {
              loader.load(field, section + Configuration.CATEGORY_SPLITTER + key, canno, obj, configuration)
            }
            catch {
              case e: Exception =>
                Femtocraft.log(Level.WARN, "Error loading @Configurable field " + field.getName + " in class " + clazz.getName + ".")
                e.printStackTrace()
            }
          }
        }
        if (!accessible) {
          field.setAccessible(false)

        }
      }
    })
  }

  def init() {
    registerConfigLoaders()
    registerStringEscapes()
  }

  private def registerStringEscapes() {
    configStringEscapes.put(",", "-comma-")
    configStringEscapes.put("'", "-apostrophe-")
  }

  private def registerConfigLoaders() {
    loaderMap.put(classOf[Int], new FemtocraftConfigHelper.FieldLoader[Integer] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.setInt(obj, getValue(field.getName, field.getInt(obj), section, anno, config))
      }

      def getValue(key: String, default: Integer, section: String, anno: Configurable, config: Configuration): Integer = {
        config.get(section, key, default, anno.comment).getInt
      }
    })
    loaderMap.put(classOf[Array[Int]], new FemtocraftConfigHelper.FieldLoader[Array[Int]] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[Array[Int]], section, anno, config))
      }

      def getValue(key: String, default: Array[Int], section: String, anno: Configurable, config: Configuration): Array[Int] = {
        config.get(section, key, default.asInstanceOf[Array[Int]], anno.comment).getIntList
      }
    })
    loaderMap.put(classOf[String], new FemtocraftConfigHelper.FieldLoader[String] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) =
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[String], section, anno, config))


      def getValue(key: String, default: String, section: String, anno: Configurable, config: Configuration): String =
        unescapeConfigString(config.get(section, key, escapeConfigString(default), anno.comment).getString)

    })
    loaderMap.put(classOf[Array[String]], new FemtocraftConfigHelper.FieldLoader[Array[String]] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[Array[String]], section, anno, config))
      }

      def getValue(key: String, default: Array[String], section: String, anno: Configurable, config: Configuration): Array[String] = {
        config.get(section, key, default, anno.comment).getStringList
      }
    })
    loaderMap.put(classOf[Boolean], new FemtocraftConfigHelper.FieldLoader[Boolean] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.setBoolean(obj, getValue(field.getName, field.getBoolean(obj), section, anno, config))
      }

      def getValue(key: String, default: Boolean, section: String, anno: Configurable, config: Configuration): Boolean =
        config.get(section, key, default, anno.comment).getBoolean(default)

    })
    loaderMap.put(classOf[Array[Boolean]], new FemtocraftConfigHelper.FieldLoader[Array[Boolean]] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[Array[Boolean]], section, anno, config))
      }

      def getValue(key: String, default: Array[Boolean], section: String, anno: Configurable, config: Configuration): Array[Boolean] =
        config.get(section, key, default.asInstanceOf[Array[Boolean]], anno.comment).getBooleanList

    })
    loaderMap.put(classOf[Double], new FemtocraftConfigHelper.FieldLoader[Double] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.setDouble(obj, getValue(field.getName, field.getDouble(obj), section, anno, config))
      }

      def getValue(key: String, default: Double, section: String, anno: Configurable, config: Configuration): Double = config.get(section, key, default, anno.comment).getDouble(default)
    })
    loaderMap.put(classOf[Array[Double]], new FemtocraftConfigHelper.FieldLoader[Array[Double]] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[Array[Double]], section, anno, config))
      }

      def getValue(key: String, default: Array[Double], section: String, anno: Configurable, config: Configuration): Array[Double] =
        config.get(section, key, default.asInstanceOf[Array[Double]], anno.comment).getDoubleList

    })
    loaderMap.put(classOf[Float], new FemtocraftConfigHelper.FieldLoader[Float] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.setFloat(obj, getValue(field.getName, field.getFloat(obj), section, anno, config))
      }

      def getValue(key: String, default: Float, section: String, anno: Configurable, config: Configuration): Float =
        config.get(section, key, default, anno.comment).getDouble(default).asInstanceOf[Float]

    })
    loaderMap.put(classOf[EnumTechLevel], new FemtocraftConfigHelper.FieldLoader[EnumTechLevel] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[EnumTechLevel], section, anno, config))
      }

      def getValue(key: String, default: EnumTechLevel, section: String, anno: Configurable, config: Configuration): EnumTechLevel = {
        EnumTechLevel.getTech(config.get(section, key, default.key, anno.comment).getString)
      }
    })
    loaderMap.put(classOf[ItemStack], new FemtocraftConfigHelper.FieldLoader[ItemStack] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[ItemStack], section, anno, config))
      }

      def getValue(key: String, default: ItemStack, section: String, anno: Configurable, config: Configuration): ItemStack = {
        config.get(section, key, default.toModQualifiedString, anno.comment).getString.toItemStack
      }
    })
    loaderMap.put(classOf[Array[ItemStack]], new FemtocraftConfigHelper.FieldLoader[Array[ItemStack]] {
      def load(field: Field, section: String, anno: Configurable, obj: AnyRef, config: Configuration) {
        field.set(obj, getValue(field.getName, field.get(obj).asInstanceOf[Array[ItemStack]], section, anno, config))
      }

      def getValue(key: String, default: Array[ItemStack], section: String, anno: Configurable, config: Configuration): Array[ItemStack] = {
        val defsar = if (default == null) new Array[String](0) else new Array[String](default.length)
        for (i <- 0 until defsar.length) {
          defsar(i) = default(i).toModQualifiedString
        }
        val sar = config.get(section, key, defsar, anno.comment).getStringList
        val ret = if (sar == null) new Array[ItemStack](0) else new Array[ItemStack](sar.length)
        for (i <- 0 until sar.length) {
          ret(i) = sar(i).toItemStack
        }
        ret
      }
    })
  }
}



