//package com.itszuvalex.femtocraft.configuration
//
//import java.io.File
//import java.util
//
//import com.itszuvalex.femtocraft.Femtocraft
//import com.itszuvalex.femtocraft.api.EnumTechLevel
//import ITechnology
//import com.itszuvalex.femtocraft.managers.research.Technology
//import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
//import net.minecraftforge.common.config.Configuration
//import org.apache.logging.log4j.Level
//
//import scala.collection.JavaConversions._
//
///**
// * Created by Chris on 9/11/2014.
// */
//object FemtocraftTechnologyConfig {
//  val SECTION_KEY = "technologies"
//}
//
//class FemtocraftTechnologyConfig(private val file: File) {
//  private val xml             = if (FemtocraftConfigs.useXMLFile) new XMLTechnology(file) else null
//  private val config          = if (FemtocraftConfigs.useXMLFile && xml.valid) null else new Configuration(file)
//  private val assemblyRecipes = Femtocraft.recipeManager.assemblyRecipes
//
//  def loadTechnologies() {
//    Femtocraft.log(Level.INFO, "Loading Technologies.")
//    var loadedTechnologies: util.List[ITechnology] = null
//    if (xml != null && xml.valid) {
//      Femtocraft.log(Level.INFO, "XML file successfully parsed.  Using this instead of configuration " + "file.")
//      loadedTechnologies = if (FemtocraftConfigs.useCustomTechnologies) xml.loadCustomTechnologies else xml.loadDefaultTechnologies
//      if (xml.isChanged) xml.save
//    }
//    else {
//      loadedTechnologies =
//        if (config.get(FemtocraftTechnologyConfig.SECTION_KEY, "Use custom classes", false, "Set to true if you define new technologies in this " + "section.  If false, " + "Femtocraft will only look for technologies of " + "certain names, specifically the ones bundled with " + "the vanilla version.  If true, " + "it will instead look at all keys in this section " + "and attempt to load each as a distinct Technology, " + "and will not load ANY of the original technologies" + " unless they are present in this section.").getBoolean(false)) {
//          loadCustomTechnologies
//        }
//        else {
//          loadDefaultTechnologies
//        }
//      if (config.hasChanged) config.save()
//    }
//    registerTechnologies(loadedTechnologies)
//    Femtocraft.log(Level.INFO, "Finished loading Technologies.")
//  }
//
//  private def loadCustomTechnologies: util.List[ITechnology] = {
//    Femtocraft.log(Level.INFO, "Loading custom Technologies from configs.")
//    val ret = new util.ArrayList[ITechnology]
//    config.getCategory(FemtocraftTechnologyConfig.SECTION_KEY).getChildren.
//    foreach { cc =>
//      val name = cc.getQualifiedName.split("\\" + Configuration.CATEGORY_SPLITTER)
//      ret.add(loadTechnology(name(name.length - 1)))
//            }
//    Femtocraft.log(Level.INFO, "Finished loading custom Technologies from configs.")
//    ret
//  }
//
//  private def loadTechnology(name: String) =
//    loadResearchTechnology(new Technology(name, "DEFAULT DESCRIPTION", EnumTechLevel.MACRO, null, null, false, null))
//
//  private def loadDefaultTechnologies: util.List[ITechnology] = {
//    Femtocraft.log(Level.INFO, "Loading default Technologies from configs.")
//    val techs = FemtocraftTechnologies.defaultTechnologies
//    techs.foreach(loadResearchTechnology)
//    Femtocraft.log(Level.INFO, "Finished loading default technologies from configs.")
//    techs
//  }
//
//  def loadResearchTechnology(ret: ITechnology): ITechnology = {
//    FemtocraftConfigHelper.loadClassInstanceFromConfig(config, FemtocraftTechnologyConfig.SECTION_KEY, ret.getName, classOf[Technology], ret)
//    ret
//  }
//
//  private def registerTechnologies(loadedTechnologies: util.List[ITechnology]) {
//    loadedTechnologies.foreach(Femtocraft.researchManager.addTechnology)
//    loadedTechnologies.clear()
//  }
//}
