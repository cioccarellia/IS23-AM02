package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.net.tcp.messages.Message;
import it.polimi.ingsw.net.tcp.messages.request.*;
import it.polimi.ingsw.net.tcp.messages.request.replies.*;

public class Parsers {

    private final static Gson defaultGson = new Gson();

    public static Gson defaultGson() {
        return defaultGson;
    }


    private final static String META_CLASSNAME_JSON_FIELD = "type";

    private final static RuntimeTypeAdapterFactory<Message> runtimeTypeAdapterFactory =
            RuntimeTypeAdapterFactory
                    .of(Message.class, META_CLASSNAME_JSON_FIELD) // typeFieldName
                    .registerSubtype(ServerStatusRequest.class, ServerStatusRequest.class.getName())
                    .registerSubtype(ServerStatusRequestReply.class, ServerStatusRequestReply.class.getName())
                    .registerSubtype(GameStartRequest.class, GameStartRequest.class.getName())
                    .registerSubtype(GameStartRequestReply.class, GameStartRequestReply.class.getName())
                    .registerSubtype(GameConnectionRequest.class, GameConnectionRequest.class.getName())
                    .registerSubtype(GameConnectionRequestReply.class, GameConnectionRequestReply.class.getName())
                    .registerSubtype(KeepAlive.class, KeepAliveReply.class.getName())
                    .registerSubtype(GameInsertionTurnRequest.class, GameInsertionTurnRequest.class.getName())
                    .registerSubtype(GameInsertionTurnRequestReply.class, GameInsertionTurnRequestReply.class.getName())
                    .registerSubtype(GameSelectionTurnRequest.class, GameSelectionTurnRequest.class.getName())
                    .registerSubtype(GameSelectionTurnRequestReply.class, GameSelectionTurnRequestReply.class.getName());

    private static final Gson marshaledGson = new GsonBuilder()
            .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
            .create();

    public static Gson marshaledGson() {
        return marshaledGson;
    }

}
