package it.polimi.ingsw.utils.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.net.tcp.messages.Message;

import java.io.IOException;

public class MessageTypeAdapterFactory implements TypeAdapterFactory {

    private static final String CLASSNAME = "className";
    private static final String INSTANCE = "instance";

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<?> rawType = typeToken.getRawType();
        if (Message.class.isAssignableFrom(rawType)) {
            return new MessageTypeAdapter<>(gson, typeToken);
        }
        return null;
    }

    private static class MessageTypeAdapter<T> extends TypeAdapter<T> {

        private final Gson gson;
        private final TypeToken<T> typeToken;

        public MessageTypeAdapter(Gson gson, TypeToken<T> typeToken) {
            this.gson = gson;
            this.typeToken = typeToken;
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(CLASSNAME, value.getClass().getName());
            jsonObject.add(INSTANCE, gson.toJsonTree(value, typeToken.getType()));
            gson.toJson(jsonObject, out);
        }

        @Override
        public T read(JsonReader in) throws IOException {
            JsonObject jsonObject = gson.fromJson(in, JsonObject.class);
            JsonPrimitive classNameJson = jsonObject.getAsJsonPrimitive(CLASSNAME);
            String className = classNameJson.getAsString();
            try {
                Class<?> clazz = Class.forName(className);
                JsonElement instanceJson = jsonObject.get(INSTANCE);
                return (T) gson.fromJson(instanceJson, clazz);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown element type: " + className, e);
            }
        }
    }
}
