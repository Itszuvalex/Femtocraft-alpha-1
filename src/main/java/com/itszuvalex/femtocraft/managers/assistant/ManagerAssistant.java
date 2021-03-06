package com.itszuvalex.femtocraft.managers.assistant;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.utils.FemtocraftFileUtils;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ManagerAssistant {
    private static final String dir = "Assistants";
    private static final String assistKey = "assistants";
    private static HashMap<String, Map<String, AssistantPermissions>> data;

    public ManagerAssistant() {
        data = new HashMap<String, Map<String, AssistantPermissions>>();
    }

    public Map<String, AssistantPermissions> getPlayerAssistants(String owner) {
        return data.get(owner);
    }

    public boolean isPlayerAssistant(String owner, String user) {
        Map<String, AssistantPermissions> pdata = data.get(owner);
        return (pdata != null) && (pdata.keySet().contains(user));
    }

    public boolean addAssistantTo(String owner, String user) {
        Map<String, AssistantPermissions> pdata = data.get(owner);
        if (pdata == null) {
            pdata = addPlayerAssistant(owner);
        }
        if (pdata != null) {
            return pdata.put(user, new AssistantPermissions(user)) == null;
        }
        return false;
    }

    public Map<String, AssistantPermissions> addPlayerAssistant(String uname) {
        if (data.containsKey(uname)) {
            return data.get(uname);
        } else {
            Map<String, AssistantPermissions> pdata = new TreeMap<String, AssistantPermissions>();
            data.put(uname, pdata);
            return pdata;
        }
    }

    public boolean removeAssistantFrom(String owner, String user) {
        Map<String, AssistantPermissions> pdata = data.get(owner);
        if (pdata != null) {
            return pdata.remove(user) != null;
        }
        return false;
    }

    public boolean save(World world) {
        try {
            File file = new File(FemtocraftFileUtils.savePathFemtocraft(world), dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (String username : data.keySet()) {
                try {
                    File userfile = new File(file, username + ".dat");
                    if (!userfile.exists()) {
                        userfile.createNewFile();
                    }

                    NBTTagCompound gTag = new NBTTagCompound();
                    NBTTagList pDataTag = new NBTTagList();

                    NBTTagList assistTag = new NBTTagList();
                    for (String assistant : data.get(username).keySet()) {
                        NBTTagCompound assistantTag = new NBTTagCompound();
                        data.get(username).get(assistant).saveToNBT(assistantTag);
                        assistTag.appendTag(assistantTag);
                    }

                    gTag.setTag(assistKey, assistTag);

                    FileOutputStream fos = new FileOutputStream(userfile);
                    CompressedStreamTools.writeCompressed(gTag, fos);
                    fos.close();
                } catch (Exception e) {
                    Femtocraft.log(Level.ERROR,
                            "Failed to save assistant data in world - "
                            + FemtocraftFileUtils.savePathFemtocraft(world) + " for player" + username + "."
                    );
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Femtocraft.log(Level.ERROR,
                    "Failed to save assistant data in world - "
                    + FemtocraftFileUtils.savePathFemtocraft(world) + "."
            );
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean load(World world) {
        try {
            File file = new File(FemtocraftFileUtils.savePathFemtocraft(world), dir);
            if (!file.exists()) {
                Femtocraft.log(Level.WARN, "No assistant data"
                                           + " found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) +
                                           ".");
                return false;
            }

            data.clear();

            for (File pfile : file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".dat");
                }
            }))

            {
                String username = pfile.getName().substring(0, pfile.getName().length() - 4);
                try {
                    FileInputStream fis = new FileInputStream(pfile);
                    NBTTagCompound gTag = CompressedStreamTools.readCompressed(fis);
                    fis.close();
                    NBTTagList assistTag = gTag.getTagList(assistKey, 10);

                    for (int j = 0; j < assistTag.tagCount(); j++) {
                        NBTTagCompound permTag = assistTag.getCompoundTagAt(j);
                        AssistantPermissions perm = new AssistantPermissions();
                        perm.loadFromNBT(permTag);
                        Map<String, AssistantPermissions> pdata = data.get(username);
                        if (pdata == null) {
                            pdata = addPlayerAssistant(username);
                        }
                        if (pdata != null) {
                            pdata.put(perm.assistant, perm);
                        }
                    }
                } catch (Exception e) {
                    Femtocraft.log(Level.ERROR,
                            "Failed to load assistant data in world - "
                            + FemtocraftFileUtils.savePathFemtocraft(world) + " for player" + username + "."
                    );
                    e.printStackTrace();
                }
            }

        } catch (
                Exception e
                )

        {
            Femtocraft.log(Level.ERROR,
                    "Failed to load assistant data in world - "
                    + FemtocraftFileUtils.savePathFemtocraft(world) + "."
            );
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
