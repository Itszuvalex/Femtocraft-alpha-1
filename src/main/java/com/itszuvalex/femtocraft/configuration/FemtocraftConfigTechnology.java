package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.assembler.ManagerAssemblerRecipe;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * Created by Chris on 9/11/2014.
 */
public class FemtocraftConfigTechnology {
    public static final String SECTION_KEY = "technologies";
    private final Configuration config;
    private final XMLTechnology xml;
    private final File file;
    private final ManagerAssemblerRecipe assemblyRecipes;

    public FemtocraftConfigTechnology(File file) {
        this.file = file;
        if (FemtocraftConfigs.useXMLFile) {
            this.xml = new XMLTechnology(file);
            if (!xml.valid()) {
                this.config = new Configuration(file);
            } else {
                this.config = null;
            }
        } else {
            this.config = new Configuration();
            xml = null;
        }
        assemblyRecipes = Femtocraft.recipeManager.assemblyRecipes;
    }

    public void loadTechnologies() {
        Femtocraft.logger.log(Level.INFO, "Loading Technologies.");
        List<ResearchTechnology> loadedTechnologies;
        if (xml != null && xml.valid()) {
            Femtocraft.logger.log(Level.INFO, "XML file successfully parsed.  Using this instead of configuration " +
                                              "file.");
            if (FemtocraftConfigs.useCustomTechnologies) {
                loadedTechnologies = xml.loadCustomTechnologies();
            } else {
                loadedTechnologies = xml.loadDefaultTechnologies();
            }

            if (xml.isChanged()) {
                xml.save();
            }
        } else {
            if (config.get(SECTION_KEY, "Use custom classes", false,
                    "Set to true if you define new technologies in this " +
                    "section.  If false, " +
                    "Femtocraft will only look for technologies of " +
                    "certain names, specifically the ones bundled with " +
                    "the vanilla version.  If true, " +
                    "it will instead look at all keys in this section " +
                    "and attempt to load each as a distinct Technology, " +
                    "and will not load ANY of the original technologies" +
                    " unless they are present in this section.")
                    .getBoolean(false)) {
                loadedTechnologies = loadCustomTechnologies();
            } else {
                loadedTechnologies = loadDefaultTechnologies();
            }

            if (config.hasChanged()) {
                config.save();
            }
        }
        registerTechnologies(loadedTechnologies);
        Femtocraft.logger.log(Level.INFO, "Finished loading Technologies.");
    }

    private List<ResearchTechnology> loadCustomTechnologies() {
        Femtocraft.logger.log(Level.INFO, "Loading custom Technologies from configs.");
        List<ResearchTechnology> ret = new ArrayList<ResearchTechnology>();
        ConfigCategory cat = config.getCategory(SECTION_KEY);
        Set<ConfigCategory> techs = cat.getChildren();
        for (ConfigCategory cc : techs) {
            String[] name = cc.getQualifiedName().split("\\" + Configuration.CATEGORY_SPLITTER);
            ret.add(loadTechnology(name[name.length - 1]));
        }
        Femtocraft.logger.log(Level.INFO, "Finished loading custom Technologies from configs.");
        return ret;
    }

    private List<ResearchTechnology> loadDefaultTechnologies() {
        Femtocraft.logger.log(Level.INFO, "Loading default Technologies from configs.");

        List<ResearchTechnology> techs = FemtocraftTechnologies.defaultTechnologies();
        for (int i = 0; i < techs.size(); ++i) {
            techs.set(i, loadResearchTechnology(techs.get(i)));
        }

        Femtocraft.logger.log(Level.INFO, "Finished loading default technologies from configs.");
        return techs;
    }


    private ResearchTechnology loadTechnology(String name) {
        ResearchTechnology ret = new ResearchTechnology(name, "DEFAULT DESCRIPTION", EnumTechLevel.MACRO,
                null, null, false, null);
        return loadResearchTechnology(ret);
    }

    public ResearchTechnology loadResearchTechnology(ResearchTechnology ret) {
        FemtocraftConfigHelper.loadClassInstanceFromConfig(config, SECTION_KEY, ret.name,
                ResearchTechnology.class, ret);
        return ret;
    }

    private void registerTechnologies(List<ResearchTechnology> loadedTechnologies) {
        for (ResearchTechnology tech : loadedTechnologies) {
            Femtocraft.researchManager.addTechnology(tech);
        }
        loadedTechnologies.clear();
    }
}
