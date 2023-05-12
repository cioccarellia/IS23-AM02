package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.net.tcp.messages.Message;

public class Parsers {

    private final static Gson defaultGson = new Gson();
    private final static JsonParser defaultJsonParser = new JsonParser(defaultGson);

    private static final Gson marshaledGson = new GsonBuilder()
            .registerTypeAdapter(Message.class, new PropertyBasedInterfaceMarshal())
            .create();

    private final static JsonParser marshaledJsonParser = new JsonParser(marshaledGson);


    public static JsonParser defaultJson() {
        return defaultJsonParser;
    }

    public static JsonParser tcpMarshaledJson() {
        return marshaledJsonParser;
    }

}
