package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class MessageComponentSerializer implements JsonDeserializer, JsonSerializer
{
    public ChatMessageComponent func_111056_a(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
    {
        ChatMessageComponent chatmessagecomponent = new ChatMessageComponent();
        JsonObject jsonobject = (JsonObject)par1JsonElement;
        JsonElement jsonelement1 = jsonobject.get("text");
        JsonElement jsonelement2 = jsonobject.get("translate");
        JsonElement jsonelement3 = jsonobject.get("color");
        JsonElement jsonelement4 = jsonobject.get("bold");
        JsonElement jsonelement5 = jsonobject.get("italic");
        JsonElement jsonelement6 = jsonobject.get("underlined");
        JsonElement jsonelement7 = jsonobject.get("obfuscated");

        if (jsonelement3 != null && jsonelement3.isJsonPrimitive())
        {
            EnumChatFormatting enumchatformatting = EnumChatFormatting.func_96300_b(jsonelement3.getAsString());

            if (enumchatformatting == null || !enumchatformatting.func_96302_c())
            {
                throw new JsonParseException("Given color (" + jsonelement3.getAsString() + ") is not a valid selection");
            }

            chatmessagecomponent.func_111059_a(enumchatformatting);
        }

        if (jsonelement4 != null && jsonelement4.isJsonPrimitive())
        {
            chatmessagecomponent.func_111071_a(Boolean.valueOf(jsonelement4.getAsBoolean()));
        }

        if (jsonelement5 != null && jsonelement5.isJsonPrimitive())
        {
            chatmessagecomponent.func_111063_b(Boolean.valueOf(jsonelement5.getAsBoolean()));
        }

        if (jsonelement6 != null && jsonelement6.isJsonPrimitive())
        {
            chatmessagecomponent.func_111081_c(Boolean.valueOf(jsonelement6.getAsBoolean()));
        }

        if (jsonelement7 != null && jsonelement7.isJsonPrimitive())
        {
            chatmessagecomponent.func_111061_d(Boolean.valueOf(jsonelement7.getAsBoolean()));
        }

        if (jsonelement1 != null)
        {
            if (jsonelement1.isJsonArray())
            {
                JsonArray jsonarray = jsonelement1.getAsJsonArray();
                Iterator iterator = jsonarray.iterator();

                while (iterator.hasNext())
                {
                    JsonElement jsonelement8 = (JsonElement)iterator.next();

                    if (jsonelement8.isJsonPrimitive())
                    {
                        chatmessagecomponent.func_111079_a(jsonelement8.getAsString());
                    }
                    else if (jsonelement8.isJsonObject())
                    {
                        chatmessagecomponent.func_111073_a(this.func_111056_a(jsonelement8, par2Type, par3JsonDeserializationContext));
                    }
                }
            }
            else if (jsonelement1.isJsonPrimitive())
            {
                chatmessagecomponent.func_111079_a(jsonelement1.getAsString());
            }
        }
        else if (jsonelement2 != null && jsonelement2.isJsonPrimitive())
        {
            JsonElement jsonelement9 = jsonobject.get("using");

            if (jsonelement9 != null)
            {
                if (jsonelement9.isJsonArray())
                {
                    ArrayList arraylist = Lists.newArrayList();
                    Iterator iterator1 = jsonelement9.getAsJsonArray().iterator();

                    while (iterator1.hasNext())
                    {
                        JsonElement jsonelement10 = (JsonElement)iterator1.next();

                        if (jsonelement10.isJsonPrimitive())
                        {
                            arraylist.add(jsonelement10.getAsString());
                        }
                        else if (jsonelement10.isJsonObject())
                        {
                            arraylist.add(this.func_111056_a(jsonelement10, par2Type, par3JsonDeserializationContext));
                        }
                    }

                    chatmessagecomponent.func_111080_a(jsonelement2.getAsString(), arraylist.toArray());
                }
                else if (jsonelement9.isJsonPrimitive())
                {
                    chatmessagecomponent.func_111080_a(jsonelement2.getAsString(), new Object[] {jsonelement9.getAsString()});
                }
            }
            else
            {
                chatmessagecomponent.func_111072_b(jsonelement2.getAsString());
            }
        }

        return chatmessagecomponent;
    }

    public JsonElement func_111055_a(ChatMessageComponent par1ChatMessageComponent, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        JsonObject jsonobject = new JsonObject();

        if (par1ChatMessageComponent.func_111065_a() != null)
        {
            jsonobject.addProperty("color", par1ChatMessageComponent.func_111065_a().func_96297_d());
        }

        if (par1ChatMessageComponent.func_111058_b() != null)
        {
            jsonobject.addProperty("bold", par1ChatMessageComponent.func_111058_b());
        }

        if (par1ChatMessageComponent.func_111064_c() != null)
        {
            jsonobject.addProperty("italic", par1ChatMessageComponent.func_111064_c());
        }

        if (par1ChatMessageComponent.func_111067_d() != null)
        {
            jsonobject.addProperty("underlined", par1ChatMessageComponent.func_111067_d());
        }

        if (par1ChatMessageComponent.func_111076_e() != null)
        {
            jsonobject.addProperty("obfuscated", par1ChatMessageComponent.func_111076_e());
        }

        if (par1ChatMessageComponent.func_111075_f() != null)
        {
            jsonobject.addProperty("text", par1ChatMessageComponent.func_111075_f());
        }
        else if (par1ChatMessageComponent.func_111074_g() != null)
        {
            jsonobject.addProperty("translate", par1ChatMessageComponent.func_111074_g());

            if (par1ChatMessageComponent.func_111069_h() != null && !par1ChatMessageComponent.func_111069_h().isEmpty())
            {
                jsonobject.add("using", this.func_111057_b(par1ChatMessageComponent, par2Type, par3JsonSerializationContext));
            }
        }
        else if (par1ChatMessageComponent.func_111069_h() != null && !par1ChatMessageComponent.func_111069_h().isEmpty())
        {
            jsonobject.add("text", this.func_111057_b(par1ChatMessageComponent, par2Type, par3JsonSerializationContext));
        }

        return jsonobject;
    }

    private JsonArray func_111057_b(ChatMessageComponent par1ChatMessageComponent, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        JsonArray jsonarray = new JsonArray();
        Iterator iterator = par1ChatMessageComponent.func_111069_h().iterator();

        while (iterator.hasNext())
        {
            ChatMessageComponent chatmessagecomponent1 = (ChatMessageComponent)iterator.next();

            if (chatmessagecomponent1.func_111075_f() != null)
            {
                jsonarray.add(new JsonPrimitive(chatmessagecomponent1.func_111075_f()));
            }
            else
            {
                jsonarray.add(this.func_111055_a(chatmessagecomponent1, par2Type, par3JsonSerializationContext));
            }
        }

        return jsonarray;
    }

    public Object deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
    {
        return this.func_111056_a(par1JsonElement, par2Type, par3JsonDeserializationContext);
    }

    public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        return this.func_111055_a((ChatMessageComponent)par1Obj, par2Type, par3JsonSerializationContext);
    }
}
