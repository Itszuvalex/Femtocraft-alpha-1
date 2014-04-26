package femtocraft.managers.research;

import femtocraft.utils.FemtocraftUtils;
import net.minecraft.util.EnumChatFormatting;

public enum EnumTechLevel {
    MACRO("macro", 0), // Vanilla level
    MICRO("micro", 1), // 1st Tier
    NANO("nano", 2), // 2nd Tier
    FEMTO("femto", 3), // 3rd Tier
    TEMPORAL("temporal", 4), // Specialty Tier 1
    DIMENSIONAL("dimensional", 4); // Specialty Tier 2

    public String key;
    public int tier;

    EnumTechLevel(String key, int tier) {
        this.key = key;
        this.tier = tier;
    }

    public static EnumTechLevel getTech(String key) {
        if (key.equals("macro")) {
            return MACRO;
        }
        else if (key.equals("micro")) {
            return MICRO;
        }
        else if (key.equals("nano")) {
            return NANO;
        }
        else if (key.equals("femto")) {
            return FEMTO;
        }
        else if (key.equals("temporal")) {
            return TEMPORAL;
        }
        else if (key.equals("dimensional")) {
            return DIMENSIONAL;
        }
        else {
            return null;
        }
    }

    public EnumChatFormatting getTooltipEnum() {
        switch (this) {
            case MACRO:
                return EnumChatFormatting.RED;
            case MICRO:
                return EnumChatFormatting.BLUE;
            case NANO:
                return EnumChatFormatting.GREEN;
            case FEMTO:
                return EnumChatFormatting.GOLD;
            case TEMPORAL:
                return EnumChatFormatting.WHITE;
            case DIMENSIONAL:
                return EnumChatFormatting.DARK_PURPLE;
            default:
                return EnumChatFormatting.RESET;
        }
    }

    public int getColor() {
        switch (this) {
            case MACRO:
                return FemtocraftUtils.colorFromARGB(0, 255, 0, 0);
            case MICRO:
                return FemtocraftUtils.blueColor();
            case NANO:
                return FemtocraftUtils.greenColor();
            case FEMTO:
                return FemtocraftUtils.orangeColor();
            case TEMPORAL:
                return FemtocraftUtils.colorFromARGB(0, 255, 255, 255);
            case DIMENSIONAL:
                return FemtocraftUtils.colorFromARGB(0, 148, 0, 211);
            default:
                return FemtocraftUtils.colorFromARGB(0, 255, 255, 255);
        }
    }
}
