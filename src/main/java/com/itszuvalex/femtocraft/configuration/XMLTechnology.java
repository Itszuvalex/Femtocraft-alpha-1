package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.assembler.ManagerAssemblerRecipe;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies;
import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils;
import net.minecraft.item.ItemStack;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Chris on 10/3/2014.
 */
public class XMLTechnology {
    public final File techFile;
    private Document xml = null;
    private boolean changed = false;

    private final ManagerAssemblerRecipe assemblyRecipes;

    private static final String technologyTag = "technology";

    private static final String shortDescTag = "ShortDescription";
    private static final String techLevelTag = "TechLevel";
    private static final String prerequisitesTag = "Prerequisites";
    private static final String displayItemTag = "DisplayItem";
    private static final String keystoneTag = "Keystone";
    private static final String researchMaterialsTag = "ResearchMaterials";
    private static final String discoverItemTag = "DiscoverItem";
    private static final String researcheDescriptionTag = "ResearchedDescription";
    private static final String discoveredDescriptionTag = "DiscoveredDescription";
    private static final String discoveredByDefaultTag = "DiscoveredByDefault";
    private static final String researchedByDefaultTag = "ResearchedByDefault";

    public XMLTechnology(File file) {
        techFile = file;
        assemblyRecipes = Femtocraft.recipeManager().assemblyRecipes;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                xml = null;
                return;
            }
        }
        try {
            xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(techFile);
        } catch (Exception e) {
            try {
                xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
                xml = null;
                return;
            }
            xml.appendChild(xml.createElement("technologies"));
            changed = true;
        }
    }


    public ResearchTechnology loadTechnology(ResearchTechnology tech) {
        return loadTechnology(tech, null);
    }

    private ResearchTechnology loadTechnology(ResearchTechnology tech, NodeList techList) {
        if (techList == null) {
            techList = getTechnologies();
        }

        Element node = getTechnologyNode(tech.name, techList);
        tech.shortDescription = getTechString(node, shortDescTag, tech.shortDescription);
        tech.level = getTechLevel(node, techLevelTag, tech.level);
        tech.prerequisites = getTechStringArray(node, prerequisitesTag, tech.prerequisites);
        tech.displayItem = getTechItemStack(node, displayItemTag, tech.displayItem);
        tech.isKeystone = getTechBoolean(node, keystoneTag, tech.isKeystone);
        tech.researchMaterials = getTechItemStackArray(node, researchMaterialsTag, tech.researchMaterials);
        tech.discoverItem = getTechItemStack(node, discoverItemTag, tech.discoverItem);
        tech.researchedDescription = getTechString(node, researcheDescriptionTag, tech.researchedDescription);
        tech.discoveredDescription = getTechString(node, discoveredDescriptionTag, tech.discoveredDescription);
        tech.discoveredByDefault = getTechBoolean(node, discoveredByDefaultTag, tech.discoveredByDefault);
        tech.researchedByDefault = getTechBoolean(node, researchedByDefaultTag, tech.researchedByDefault);

        return tech;
    }

    private EnumTechLevel getTechLevel(Element parent, String tag, EnumTechLevel def) {
        return EnumTechLevel.getTech(getTechString(parent, tag, def.key));
    }

    private String getTechString(Element parent, String tag, String def) {
        Node strNode = getNode(parent, tag, def);
        return strNode.getTextContent().trim();
    }

    private boolean getTechBoolean(Element parent, String tag, boolean def) {
        return Boolean.parseBoolean(getTechString(parent, tag, String.valueOf(def)));
    }

    private ItemStack getTechItemStack(Element parent, String tag, ItemStack def) {
        return FemtocraftStringUtils.itemStackFromString(getTechString(parent, tag,
                FemtocraftStringUtils.itemStackToString(def)));
    }

    private ItemStack[] getTechItemStackArray(Element parent, String tag, ItemStack[] def) {
        boolean loadFromFile = false;
        NodeList nodeList = parent.getElementsByTagName(tag);
        Element arrNode;
        if (nodeList.getLength() == 0) {
            arrNode = xml.createElement(tag);
            parent.appendChild(arrNode);
            changed = true;
        } else {
            arrNode = (Element) nodeList.item(0);
            loadFromFile = true;
        }

        int length;
        if (loadFromFile) {
            if (arrNode.hasAttribute("length")) { length = Integer.parseInt(arrNode.getAttribute("length")); } else {
                length = 0;
            }
        } else {
            length = def.length;
            arrNode.setAttribute("length", String.valueOf(def.length));
        }

        ItemStack[] ret = new ItemStack[length];

        for (int i = 0; i < ret.length; ++i) {
            ret[i] = getTechItemStack(arrNode, tag +
                                               String.valueOf(i), loadFromFile ? FemtocraftStringUtils
                    .itemStackFromString(arrNode.getElementsByTagName(
                            tag + String.valueOf(i)).item(0).getTextContent().trim()) : def[i]);
        }

        return ret;
    }

    private String[] getTechStringArray(Element parent, String tag, String[] def) {
        boolean loadFromFile = false;
        NodeList nodeList = parent.getElementsByTagName(tag);
        Element arrNode;
        if (nodeList.getLength() == 0) {
            arrNode = xml.createElement(tag);
            parent.appendChild(arrNode);
            changed = true;
        } else {
            arrNode = (Element) nodeList.item(0);
            loadFromFile = true;
        }

        int length;
        if (loadFromFile) {
            if (arrNode.hasAttribute("length")) { length = Integer.parseInt(arrNode.getAttribute("length")); } else {
                length = 0;
            }
        } else {
            length = def.length;
            arrNode.setAttribute("length", String.valueOf(def.length));
        }

        String[] ret = new String[length];

        for (int i = 0; i < ret.length; ++i) {
            ret[i] = getTechString(arrNode, tag + String.valueOf(i), loadFromFile ? arrNode.getElementsByTagName(
                    tag + String.valueOf(i)).item(0).getTextContent().trim() : def[i]);
        }

        return ret;
    }

    private Element getTechnologyNode(String techName, NodeList techList) {
        for (int i = 0; i < techList.getLength(); ++i) {
            Node node = techList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("name");
                if (techName.equalsIgnoreCase(name)) {
                    return element;
                }
            }
        }

        Element ret = xml.createElement(technologyTag);
        ret.setAttribute("name", techName);
        xml.getDocumentElement().appendChild(ret);
        changed = true;

        return ret;
    }

    private Node getNode(Element parent, String tag, String def, Map<String, String> attributes) {
        NodeList nodeList = parent.getElementsByTagName(tag);
        if (nodeList.getLength() == 0) {
            Element newNode = xml.createElement(tag);
            if (def != null) {
                newNode.setTextContent(def.trim());
            } else {
                newNode.setTextContent(null);
            }
            if (attributes != null) {
                for (String attr : attributes.keySet()) {
                    newNode.setAttribute(attr, attributes.get(attr));
                }
            }
            parent.appendChild(newNode);
            changed = true;
            return newNode;
        }
        return nodeList.item(0);
    }

    private Node getNode(Element parent, String tag, String def) {
        return getNode(parent, tag, def, null);
    }

    public List<ResearchTechnology> loadCustomTechnologies() {
        List<ResearchTechnology> ret = new ArrayList<ResearchTechnology>();
        NodeList nodeList = getTechnologies();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("name");
                ret.add(loadTechnology(new ResearchTechnology(name, "DEFAULT DESCRIPTION", EnumTechLevel.MACRO,
                        null, null, false, null), nodeList));
            }
        }

        return ret;
    }

    List<ResearchTechnology> loadDefaultTechnologies() {
        Femtocraft.log(Level.INFO, "Loading default Technologies from XML.");

        NodeList nodes = getTechnologies();
        List<ResearchTechnology> techs = FemtocraftTechnologies.defaultTechnologies();
        for (int i = 0; i < techs.size(); ++i) {
            techs.set(i, loadTechnology(techs.get(i), nodes));
        }

        Femtocraft.log(Level.INFO, "Finished loading default technologies from XML.");
        return techs;
    }


    private NodeList getTechnologies() {
        return xml.getDocumentElement().getElementsByTagName(technologyTag);
    }

    public boolean valid() {
        return xml != null;
    }

    public boolean isChanged() {
        return changed;
    }

    public boolean save() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xml);
            StreamResult result = new StreamResult(techFile);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");

            transformer.transform(source, result);

            changed = false;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (TransformerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
