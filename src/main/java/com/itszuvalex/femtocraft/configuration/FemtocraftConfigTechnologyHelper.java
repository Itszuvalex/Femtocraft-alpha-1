package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Chris on 9/11/2014.
 */
public class FemtocraftConfigTechnologyHelper {
    public static final String SECTION_KEY = "Technologies";
    private final Configuration config;

    public FemtocraftConfigTechnologyHelper(Configuration config) {
        this.config = config;
    }

    public void loadTechnologies() {
        List<ResearchTechnology> loadedTechnologies;
        if (config.get(SECTION_KEY, "Use custom classes", false, "Set to true if you define new technologies in this " +
                                                                 "section.  If false, " +
                                                                 "Femtocraft will only look for technologies of " +
                                                                 "certain names, specifically the ones bundled with " +
                                                                 "the vanilla version.  If true, " +
                                                                 "it will instead look at all keys in this section " +
                                                                 "and attempt to load each as a distinct Technology, " +
                                                                 "and will not load ANY of the original technologies" +
                                                                 ".").getBoolean(false)) {
            loadedTechnologies = loadCustomTechnologies();
        } else {
            loadedTechnologies = loadDefaultTechnologies();
        }

        registerTechnologies(loadedTechnologies);
    }

    private List<ResearchTechnology> loadCustomTechnologies() {
        List<ResearchTechnology> ret = new ArrayList<ResearchTechnology>();
        ConfigCategory cat = config.getCategory(SECTION_KEY);
        Set<ConfigCategory> techs = cat.getChildren();
        for (ConfigCategory cc : techs) {
            ret.add(loadTechnology(cc.getQualifiedName()));
        }
        return ret;
    }

    private List<ResearchTechnology> loadDefaultTechnologies() {
        return null;
    }

    private ResearchTechnology loadTechnology(String name) {
        ResearchTechnology ret = new ResearchTechnology("DEFAULT NAME", "DEFAULT DESCRIPTION", EnumTechLevel.MACRO,
                null, null, false, null);
        FemtocraftConfigHelper.loadClassInstanceFromConfig(config, SECTION_KEY, name, ResearchTechnology.class, ret);
        return ret;
    }

    private void registerTechnologies(List<ResearchTechnology> loadedTechnologies) {
        for (ResearchTechnology tech : loadedTechnologies) {
            Femtocraft.researchManager.addTechnology(tech);
        }
        loadedTechnologies.clear();
    }
}
