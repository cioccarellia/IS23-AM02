package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;

/**
 * Thread-safe utility class used to serialize and deserialize object to and from json
 */
public class JsonParser {
    private static final Gson gson = new Gson();
    private static final String DFU = "";

    public static <T> T from(String json, Class<T> kclass) {
        return gson.fromJson(json, kclass);
    }

    public static <T> String to(T object) {
        if (object == null) {
            return DFU;
        }

        return gson.toJson(object);
    }
}