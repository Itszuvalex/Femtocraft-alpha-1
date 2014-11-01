package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.managers.assembler.ManagerAssemblerRecipe;
import com.itszuvalex.femtocraft.managers.research.Technology;
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * Created by Chris on 9/11/2014.
 */
public class FemtocraftTechnologyConfig {
    public static final String SECTION_KEY = "technologies";
    private final Configuration config;
    private final XMLTechnology xml;
    private final File file;
    private final ManagerAssemblerRecipe assemblyRecipes;

    public FemtocraftTechnologyConfig(File file) {
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
        assemblyRecipes = Femtocraft.recipeManager().assemblyRecipes;
    }

    public void loadTechnologies() {
        Femtocraft.log(Level.INFO, "Loading Technologies.");
        List<ITechnology> loadedTechnologies;
        if (xml != null && xml.valid()) {
            Femtocraft.log(Level.INFO, "XML file successfully parsed.  Using this instead of configuration " +
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
        Femtocraft.log(Level.INFO, "Finished loading Technologies.");
    }

    private List<ITechnology> loadCustomTechnologies() {
        Femtocraft.log(Level.INFO, "Loading custom Technologies from configs.");
        List<ITechnology> ret = new ArrayList<>();
        ConfigCategory cat = config.getCategory(SECTION_KEY);
        Set<ConfigCategory> techs = cat.getChildren();
        for (ConfigCategory cc : techs) {
            String[] name = cc.getQualifiedName().split("\\" + Configuration.CATEGORY_SPLITTER);
            ret.add(loadTechnology(name[name.length - 1]));
        }
        Femtocraft.log(Level.INFO, "Finished loading custom Technologies from configs.");
        return ret;
    }

    private List<ITechnology> loadDefaultTechnologies() {
        Femtocraft.log(Level.INFO, "Loading default Technologies from configs.");

        List<ITechnology> techs = FemtocraftTechnologies.defaultTechnologies();
        for (int i = 0; i < techs.size(); ++i) {
            techs.set(i, loadResearchTechnology(techs.get(i)));
        }

        Femtocraft.log(Level.INFO, "Finished loading default technologies from configs.");
        return techs;
    }


    private ITechnology loadTechnology(String name) {
        ITechnology ret = new Technology(name, "DEFAULT DESCRIPTION", EnumTechLevel.MACRO,
                null, null, false, null);
        return loadResearchTechnology(ret);
    }

    public ITechnology loadResearchTechnology(ITechnology ret) {
        FemtocraftConfigHelper.loadClassInstanceFromConfig(config, SECTION_KEY, ret.getName(),
                Technology.class, ret);
        return ret;
    }

    private void registerTechnologies(List<ITechnology> loadedTechnologies) {
        for (ITechnology tech : loadedTechnologies) {
            Femtocraft.researchManager().addTechnology(tech);
        }
        loadedTechnologies.clear();
    }
}
