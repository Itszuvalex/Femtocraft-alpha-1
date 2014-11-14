package com.itszuvalex.femtocraft.configuration


import com.google.common.reflect.ClassPath
import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigHelper._
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import net.minecraftforge.common.config.Configuration
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


/**
 * Created by Christopher Harris (Itszuvalex) on 10/10/14.
 */
class ConfigurableClassFinder(val classPackage: String) {
  private val configurableClasses    = new ArrayBuffer[Class[_]]
  private val configurableSingletons = new mutable.HashMap[AnyRef, Class[_]]

  /**
   * @param clazz Class to load all @Configurable annotated public/private fields from.
   * @return True if class successfully added.
   */
  def registerConfigurableClass(clazz: Class[_]) = configurableClasses.append(clazz)

  def loadClassConstants(configuration: Configuration) = {
    configurableClasses.foreach(clazz => FemtocraftConfigHelper.loadClassFromConfig(configuration, CLASS_CONSTANTS_KEY, clazz.getSimpleName, clazz))
    configurableSingletons.foreach(pair => FemtocraftConfigHelper.loadClassInstanceFromConfig(configuration, CLASS_CONSTANTS_KEY, pair._2.getSimpleName.subSequence(0, pair._2.getSimpleName.length - 1).toString, pair._2, pair._1))
  }

  def registerConfigurableClasses() {
    Femtocraft.log(Level.INFO, "Finding all configurable classes for registration.")
    val classes = ClassPath.from(getClass.getClassLoader).getTopLevelClassesRecursive(classPackage)
    classes.foreach(info => {
      try {
        val side = FMLCommonHandler.instance().getEffectiveSide
        Femtocraft.log(Level.INFO, info.getResourceName)
        val clientPackage = info.getResourceName.toLowerCase.matches(".*client.*") ||
                            info.getResourceName.toLowerCase.matches(".*gui.*") ||
                            info.getResourceName.toLowerCase.matches(".*render.*") ||
                            info.getResourceName.toLowerCase.matches(".*fx.*") ||
                            info.getResourceName.toLowerCase.matches(".*model.*")
        if (!clientPackage || (clientPackage && (side == Side.CLIENT))) {
          val clazz = Class.forName(info.getName)
          try {
            if (clazz.getAnnotation(classOf[Configurable]) != null) {
              try {
                val compclazz = Class.forName(info.getName + "$")
                val inst = compclazz.getField("MODULE$").get(null)
                if (inst != null) {
                  configurableSingletons.put(inst, compclazz)
                  Femtocraft.log(Level.INFO, "Registered " + clazz.getSimpleName + " as configurable singleton.")
                }
              }
              catch {
                case e: ClassNotFoundException =>
                  registerConfigurableClass(clazz)
                  Femtocraft.log(Level.INFO, "Registered " + clazz.getSimpleName + " as configurable.")
                case e: Exception              =>
              }
            }
          }
          catch {
            case e: ClassNotFoundException =>
            case e: NoClassDefFoundError   =>
            case e: Exception              =>
          }
        }
      }
    })
    Femtocraft.log(Level.INFO, "Registered " + configurableClasses.length + " configurable classes.")
    Femtocraft.log(Level.INFO, "Registered " + configurableSingletons.size + " configurable singletons.")
  }

}
