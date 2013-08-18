package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Type;
import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    public AnimationMetadataSection func_110493_a(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
    {
        ArrayList arraylist = Lists.newArrayList();
        JsonObject jsonobject = (JsonObject)par1JsonElement;
        int i = this.func_110485_a(jsonobject.get("frametime"), "frametime", Integer.valueOf(1), 1, Integer.MAX_VALUE);
        int j;

        if (jsonobject.has("frames"))
        {
            try
            {
                JsonArray jsonarray = jsonobject.getAsJsonArray("frames");

                for (j = 0; j < jsonarray.size(); ++j)
                {
                    JsonElement jsonelement1 = jsonarray.get(j);
                    AnimationFrame animationframe = this.func_110492_a(j, jsonelement1);

                    if (animationframe != null)
                    {
                        arraylist.add(animationframe);
                    }
                }
            }
            catch (ClassCastException classcastexception)
            {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonobject.get("frames"), classcastexception);
            }
        }

        int k = this.func_110485_a(jsonobject.get("width"), "width", Integer.valueOf(-1), 1, Integer.MAX_VALUE);
        j = this.func_110485_a(jsonobject.get("height"), "height", Integer.valueOf(-1), 1, Integer.MAX_VALUE);
        return new AnimationMetadataSection(arraylist, k, j, i);
    }

    private AnimationFrame func_110492_a(int par1, JsonElement par2JsonElement)
    {
        if (par2JsonElement.isJsonPrimitive())
        {
            try
            {
                return new AnimationFrame(par2JsonElement.getAsInt());
            }
            catch (NumberFormatException numberformatexception)
            {
                throw new JsonParseException("Invalid animation->frames->" + par1 + ": expected number, was " + par2JsonElement, numberformatexception);
            }
        }
        else if (par2JsonElement.isJsonObject())
        {
            JsonObject jsonobject = par2JsonElement.getAsJsonObject();
            int j = this.func_110485_a(jsonobject.get("time"), "frames->" + par1 + "->time", Integer.valueOf(-1), 1, Integer.MAX_VALUE);
            int k = this.func_110485_a(jsonobject.get("index"), "frames->" + par1 + "->index", (Integer)null, 0, Integer.MAX_VALUE);
            return new AnimationFrame(k, j);
        }
        else
        {
            return null;
        }
    }

    public JsonElement func_110491_a(AnimationMetadataSection par1AnimationMetadataSection, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("frametime", Integer.valueOf(par1AnimationMetadataSection.func_110469_d()));

        if (par1AnimationMetadataSection.func_110474_b() != -1)
        {
            jsonobject.addProperty("width", Integer.valueOf(par1AnimationMetadataSection.func_110474_b()));
        }

        if (par1AnimationMetadataSection.func_110471_a() != -1)
        {
            jsonobject.addProperty("height", Integer.valueOf(par1AnimationMetadataSection.func_110471_a()));
        }

        if (par1AnimationMetadataSection.func_110473_c() > 0)
        {
            JsonArray jsonarray = new JsonArray();

            for (int i = 0; i < par1AnimationMetadataSection.func_110473_c(); ++i)
            {
                if (par1AnimationMetadataSection.func_110470_b(i))
                {
                    JsonObject jsonobject1 = new JsonObject();
                    jsonobject1.addProperty("index", Integer.valueOf(par1AnimationMetadataSection.func_110468_c(i)));
                    jsonobject1.addProperty("time", Integer.valueOf(par1AnimationMetadataSection.func_110472_a(i)));
                    jsonarray.add(jsonobject1);
                }
                else
                {
                    jsonarray.add(new JsonPrimitive(Integer.valueOf(par1AnimationMetadataSection.func_110468_c(i))));
                }
            }

            jsonobject.add("frames", jsonarray);
        }

        return jsonobject;
    }

    public String func_110483_a()
    {
        return "animation";
    }

    public Object deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
    {
        return this.func_110493_a(par1JsonElement, par2Type, par3JsonDeserializationContext);
    }

    public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        return this.func_110491_a((AnimationMetadataSection)par1Obj, par2Type, par3JsonSerializationContext);
    }
}
