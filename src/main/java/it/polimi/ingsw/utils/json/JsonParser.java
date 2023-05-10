package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;

/**
 * Thread-safe utility class used to serialize and deserialize object to and from json
 */
public class JsonParser {

    private final Gson gson;

    protected JsonParser(Gson gson) {
        this.gson = gson;
    }

    public <T> T fromJson(String json, Class<T> kclass) {
        return gson.fromJson(json, kclass);
    }

    public <T> String toJson(T object) {
        return gson.toJson(object);
    }
}