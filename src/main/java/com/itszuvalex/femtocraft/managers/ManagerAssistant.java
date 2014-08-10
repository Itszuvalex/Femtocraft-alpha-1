package com.itszuvalex.femtocraft.managers;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.logging.Level;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import com.itszuvalex.femtocraft.FemtocraftFileUtils;

public class ManagerAssistant {
    private static final String dir = "Assistants";
    private static final String unameKey = "username";
    private static final String assistKey = "assistant";
    private static final String listKey = "assistantData";
    private static HashMap<String, List<String>> data;

    public ManagerAssistant() {
        data = new HashMap<String, List<String>>();
    }

    public List<String> addPlayerAssistant(String uname) {
        if(data.containsKey(uname)){
            System.out.print("Already has username");
            return data.get(uname);
        } else {
            System.out.print("Creating username");
            List<String> pdata = new ArrayList<String>();
            data.put(uname,pdata);
            return pdata;
        }
    }

    public List<String> getPlayerAssistants(String owner) {
        return data.get(owner);
    }

    public boolean isPlayerAssistant(String owner, String user) {
        List<String> pdata = data.get(owner);
        return (pdata != null) && (pdata.contains(user));
    }

    public boolean addAssistantTo(String owner, String user) {
        List<String> pdata = data.get(owner);
        if(pdata == null) {
            pdata = addPlayerAssistant(owner);
        }
        if(pdata != null) {
            pdata.add(user);
            return true;
        }
        return false;
    }

    public boolean removeAssistantFrom(String owner, String user) {
        List<String> pdata = data.get(owner);
        if(pdata != null) {
            pdata.remove(user);
            return true;
        }
        return false;
    }

    public boolean save(World world) {
        try {
            File file = new File(FemtocraftFileUtils.savePathFemtocraft(world), dir);
            if (!file.exists()) {
                file.createNewFile();
            }
            NBTTagCompound gTag = new NBTTagCompound();
            NBTTagList pDataTag = new NBTTagList();

            for(Map.Entry<String,List<String>> entry : data.entrySet()) {
                NBTTagCompound pTag = new NBTTagCompound();
                pTag.setString(unameKey,entry.getKey());

                NBTTagList assistTag = new NBTTagList();
                for(String assistant : entry.getValue()) {
                    NBTTagString strTag = new NBTTagString(assistant);
                    assistTag.appendTag(strTag);
                }

                pTag.setTag(assistKey,assistTag);
                pDataTag.appendTag(pTag);
            }

            gTag.setTag(listKey,pDataTag);

            FileOutputStream fos = new FileOutputStream(file);
            CompressedStreamTools.writeCompressed(gTag,fos);
            fos.close();

        } catch (Exception e) {
            Femtocraft.logger.log(Level.SEVERE,
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
                Femtocraft.logger.log(Level.WARNING, "No assistant data"
                        + " found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".");
                return false;
            }

            data.clear();

            FileInputStream fis = new FileInputStream(file);
            NBTTagCompound gTag = CompressedStreamTools.readCompressed(fis);
            fis.close();
            NBTTagList pDataTag = gTag.getTagList(listKey);

            for(int i=0; i<pDataTag.tagCount(); i++) {
                NBTTagCompound pTag = (NBTTagCompound) pDataTag.tagAt(i);
                String username = pTag.getString(unameKey);
                NBTTagList assistTag = pTag.getTagList(assistKey);
                List<String> assistData = addPlayerAssistant(username);
                for(int j=0; j<assistTag.tagCount(); j++) {
                    NBTTagString strTag = (NBTTagString)assistTag.tagAt(j);
                    assistData.add(strTag.data);
                }
            }

        } catch (Exception e) {
            Femtocraft.logger.log(Level.SEVERE,
                    "Failed to load assistant data in world - "
                            + FemtocraftFileUtils.savePathFemtocraft(world) + "."
            );
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
