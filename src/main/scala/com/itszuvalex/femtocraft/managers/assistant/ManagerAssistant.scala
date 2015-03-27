package com.itszuvalex.femtocraft.managers.assistant

import java.io.{FilenameFilter, File, FileInputStream, FileOutputStream}
import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.managers.IAssistantManager
import com.itszuvalex.femtocraft.api.utils.FemtocraftFileUtils
import net.minecraft.nbt.{CompressedStreamTools, NBTTagCompound, NBTTagList}
import net.minecraft.world.World
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.mutable

@Configurable object ManagerAssistant extends IAssistantManager {
  private               val dir              = "Assistants"
  private               val assistKey        = "assistants"
  private               val data             = new mutable.HashMap[String, mutable.HashMap[String, AssistantPermissions]]
  private               var lastWorld: World = null
  @Configurable private val debugMessages    = false


  override def isPlayerAssistant(owner: String, user: String) = getPlayerAssistants(owner).contains(user)

  override def addAssistantTo(owner: String, user: String): Unit = {
    getPlayerAssistantsPermissions(owner).put(user, new AssistantPermissions(user))
    save()
  }

  override def removeAssistantFrom(owner: String, user: String): Unit = {
    getPlayerAssistants(owner).remove(user)
    save()
  }

  override def getPlayerAssistants(owner: String): util.Collection[String] =
    data.getOrElseUpdate(owner, new mutable.HashMap[String, AssistantPermissions]).keySet

  def save(): Boolean = if (lastWorld == null) false else save(lastWorld)

  def save(world: World): Boolean = {
    try {
      val file = new File(FemtocraftFileUtils.savePathFemtocraft(world), dir)
      if (!file.exists) {
        file.mkdirs
      }
      data.keySet.view.foreach { username =>
        try {
          val userfile = new File(file, username + ".dat")
          if (!userfile.exists) {
            userfile.createNewFile
          }
          val gTag = new NBTTagCompound
          val assistTag = new NBTTagList
          getPlayerAssistantsPermissions(username).values.foreach { perms =>
            val assistantTag = new NBTTagCompound
            perms.saveToNBT(assistantTag)
            assistTag.appendTag(assistantTag)
                                                                  }
          gTag.setTag(assistKey, assistTag)
          val fos = new FileOutputStream(userfile)
          CompressedStreamTools.writeCompressed(gTag, fos)
          fos.close()
        }
        catch {
          case e: Exception =>
            Femtocraft
            .log(Level.ERROR,
                 "Failed to save assistant data in world - " + FemtocraftFileUtils
                                                               .savePathFemtocraft(world) + " for player" + username + ".")
            e.printStackTrace()
        }
                               }
    }
    catch {
      case e: Exception =>
        Femtocraft
        .log(Level.ERROR,
             "Failed to save assistant data in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        e.printStackTrace()
        return false
    }
    if (debugMessages) Femtocraft.log(Level.INFO, "Saving Assistant data for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
    true
  }

  def load(world: World): Boolean = {
    save()
    lastWorld = world
    try {
      val file = new File(FemtocraftFileUtils.savePathFemtocraft(world), dir)
      if (!file.exists) {
        Femtocraft
        .log(Level.WARN,
             "No assistant data" + " found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        return false
      }
      else {
        if (debugMessages) Femtocraft.log(Level.INFO, "Loading Assistant data for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
      }
      data.clear()
      for (pfile <- file.listFiles(new FilenameFilter() {
        def accept(dir: File, name: String): Boolean = {
          name.endsWith(".dat")
        }
      }).view) {
        val username = pfile.getName.substring(0, pfile.getName.length - 4)
        try {
          val fis = new FileInputStream(pfile)
          val gTag = CompressedStreamTools.readCompressed(fis)
          fis.close()
          val assistTag = gTag.getTagList(assistKey, 10)

          for (j <- 0 until assistTag.tagCount) {
            val permTag = assistTag.getCompoundTagAt(j)
            val perm = new AssistantPermissions
            perm.loadFromNBT(permTag)
            getPlayerAssistantsPermissions(username).put(perm.assistant, perm)
          }
        }
        catch {
          case e: Exception =>
            Femtocraft
            .log(Level.ERROR,
                 "Failed to load assistant data in world - " + FemtocraftFileUtils
                                                               .savePathFemtocraft(world) + " for player" + username + ".")
            e.printStackTrace()
        }
      }
    }
    catch {
      case e: Exception =>
        Femtocraft
        .log(Level.ERROR,
             "Failed to load assistant data in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        e.printStackTrace()
        return false
    }
    true
  }

  def getPlayerAssistantsPermissions(owner: String) = data.getOrElseUpdate(owner, new
      mutable.HashMap[String, AssistantPermissions])
}
