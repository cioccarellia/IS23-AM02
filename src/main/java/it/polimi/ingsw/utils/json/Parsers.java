package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.net.tcp.messages.Message;

public class Parsers {

    private final static Gson defaultGson = new Gson();

    private static final Gson marshaledGson = new GsonBuilder()
            .registerTypeAdapter(Message.class, new PropertyBasedInterfaceMarshal())
            //.registerTypeAdapterFactory(new MessageTypeAdapterFactory())
            .create();


    public static Gson defaultGson() {
        return defaultGson;
    }

    public static Gson marshaledGson() {
        return marshaledGson;
    }

}
