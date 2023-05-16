package it.polimi.ingsw.utils.json;

import com.google.gson.*;
import it.polimi.ingsw.net.tcp.messages.Message;

import java.lang.reflect.Type;

public class PropertyBasedInterfaceMarshal implements JsonSerializer<Message>, JsonDeserializer<Message> {

    private static final String CLASS_META_KEY = "CLASS_META_KEY";

    @Override
    public Message deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();

        String className = obj.get(CLASS_META_KEY).getAsString();
        try {
            Class<?> clazz = Class.forName(className);
            return context.deserialize(element, clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Message object, Type type, JsonSerializationContext context) {
        JsonElement element = context.serialize(object, object.getClass());
        element.getAsJsonObject().addProperty(CLASS_META_KEY, object.getClass().getCanonicalName());
        return element;
    }

}