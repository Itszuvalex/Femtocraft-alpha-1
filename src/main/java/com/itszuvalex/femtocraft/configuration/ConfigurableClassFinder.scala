package com.itszuvalex.femtocraft.configuration

import java.util.logging.Level

import com.google.common.reflect.ClassPath
import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigHelper._
import net.minecraftforge.common.Configuration

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Christopher Harris (Itszuvalex) on 10/10/14.
 */
class ConfigurableClassFinder(val classPackage: String) {
  private val configurableClasses = new ArrayBuffer[Class[_]]

  /**
   * @param clazz Class to load all @Configurable annotated public/private fields from.
   * @return True if class successfully added.
   */
  def registerConfigurableClass(clazz: Class[_]) = configurableClasses.append(clazz)

  def loadClassConstants(configuration: Configuration) = configurableClasses.foreach(clazz => FemtocraftConfigHelper.loadClassFromConfig(configuration, CLASS_CONSTANTS_KEY, clazz.getSimpleName, clazz))

  def registerConfigurableClasses() {
    try {
      Femtocraft.log(Level.INFO, "Finding all configurable classes for registration.")
      val classes = ClassPath.from(getClass.getClassLoader).getTopLevelClassesRecursive(classPackage)
      classes.foreach(info => {
        try {
          val clazz = Class.forName(info.getName)
          if (clazz.getAnnotation(classOf[Configurable]) != null) {
            registerConfigurableClass(clazz)
          }
          Femtocraft.log(Level.FINER, "Registered " + clazz.getSimpleName + " as configurable.")
        }
        catch {
          case e: ClassNotFoundException =>
            Femtocraft.log(Level.SEVERE, "Could not find @Configurable class " + info.getName + " when attempting to discover.")
            e.printStackTrace()
        }
      })
      Femtocraft.log(Level.INFO, "Finished registering configurable classes.")
    }
    catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
