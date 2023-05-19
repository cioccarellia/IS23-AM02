package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.network.tcp.messages.request.*;
import it.polimi.ingsw.network.tcp.messages.request.replies.*;
import it.polimi.ingsw.network.tcp.messages.response.internal.ConnectionAcceptanceEvent;

/**
 * Global parser for default and marshaled gson.
 */
public class Parsers {

    private final static Gson defaultGson = new Gson();

    /**
     * Returns the default GSON instance
     */
    public static Gson defaultGson() {
        return defaultGson;
    }


    private final static String META_CLASSNAME_JSON_FIELD = "CLASS_META_FIELD";
    private final static RuntimeTypeAdapterFactory<Message> runtimeTypeAdapterFactory =
            RuntimeTypeAdapterFactory
                    .of(Message.class, META_CLASSNAME_JSON_FIELD) // typeFieldName
                    .registerSubtype(ConnectionAcceptanceEvent.class, ConnectionAcceptanceEvent.class.getName())
                    .registerSubtype(ServerStatusRequestReply.class, ServerStatusRequestReply.class.getName())
                    .registerSubtype(GameCreationRequest.class, GameCreationRequest.class.getName())
                    .registerSubtype(GameCreationRequestReply.class, GameCreationRequestReply.class.getName())
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


    /**
     * Returns the specialized GSON instance for message serialization and deserialization
     */
    public static Gson marshaledGson() {
        return marshaledGson;
    }

}
