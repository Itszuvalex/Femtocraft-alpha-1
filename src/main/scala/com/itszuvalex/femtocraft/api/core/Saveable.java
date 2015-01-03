package com.itszuvalex.femtocraft.api.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * world True by default. Field will be saved/loaded when the world saves/loads. desc  False by default. If true, Field
 * will be sent to client when description packets are sent, and loaded on client. item  False by default. If true,
 * Field will be saved to dropped ItemStack 's NBTTagCompound.
 *
 * @author Itszuvalex
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Saveable {
    public String tag() default "";

    public boolean world() default true;

    public boolean desc() default false;

    public boolean item() default false;
}
