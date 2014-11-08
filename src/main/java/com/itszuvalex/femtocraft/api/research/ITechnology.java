package com.itszuvalex.femtocraft.api.research;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import net.minecraft.item.ItemStack;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 11/1/14.
 * <p/>
 * Interface for handling technology references.  This consists mainly of getter functions, since, beyond the initial
 * loading/adding of techs, no modification should ever be done to them.
 */
public interface ITechnology {
    /**
     * This is what will be used to indirectly reference this tech in locations such as other tech's prereqruisites,
     * etc.  As such, this should be considered unique.
     *
     * @return The name of the technology itself.
     */
    String getName();

    /**
     * The short description is a string that will be shown in the research tree when this tech is hovered over.
     *
     * @return A short description string.
     */
    String getShortDescription();

    /**
     * This is used to render different color prerequisite lines in the research console.
     *
     * @return The tech level of this technology.
     */
    EnumTechLevel getLevel();

    /**
     * @return An collection of Strings, where an element is the {@link #getName()} of an {@link
     * com.itszuvalex.femtocraft.api.research.ITechnology}.  Return null or a collection of length 0, if there are no
     * prerequisites.  If there are no prerequisites, this technology will be both discovered and researched by
     * default.
     */
    Collection<String> getPrerequisites();

    /**
     * @return An itemstack to use to render a representation of this technology in the research console.
     */
    ItemStack getDisplayItem();

    /**
     * @return True if we want the special spiky background when rendering in the research console, false for a plain
     * square.
     */
    boolean isKeystone();

    /**
     * @return A collection of item stacks corresponding to a 9x9 crafting grid of materials.  Return null or a
     * collection of length 0 if there are no required materials. If this technology is not researched by default, it
     * requires a unique collection of research materials.
     */
    ItemStack[] getResearchMaterials();

    /**
     * When this technoogy is researched, the technology carrier will be replaced with the item stack returned by this
     * function, with a stack size of 1.
     *
     * @return The itemstack to replace the tech carrier itemstack with. Return null if you don't want an item.
     */
    ItemStack getDiscoverItem();

    /**
     * This string will be parsed by {@link com.itszuvalex.femtocraft.research.gui.technology.TechnologyPageRenderer} to
     * create a technology page with imbedded recipe layouts.  This will only be parsed once the technology GUI is
     * opened, and if the technology is researched.
     *
     * @return A description string with embedded formatting for display when this technology is researched.
     */
    String getResearchedDescription();

    /**
     * This string will be parsed by {@link com.itszuvalex.femtocraft.research.gui.technology.TechnologyPageRenderer} to
     * create a technology page with imbedded recipe layouts.  This will only be parsed once the technology GUI is
     * opened, and if the technology is discovered and not researched.
     *
     * @return A description string with embedded formatting for display when this technology is discovered.
     */
    String getDiscoveredDescription();

    /**
     * @return True if this technology is to be discovered by default, regardless of prerequisites.  If prerequisites
     * are null, this is redundant.
     */
    boolean isDiscoveredByDefault();

    /**
     * @return True if this technology is to be researched by default, regardless of prerequisites.  If prerequisites
     * are null, this is redundant.
     */
    boolean isResearchedByDefault();
}
