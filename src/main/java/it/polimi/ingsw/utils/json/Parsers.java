package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.network.tcp.messages.replies.*;
import it.polimi.ingsw.network.tcp.messages.request.*;
import it.polimi.ingsw.network.tcp.messages.response.*;

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


    /**
     * Creates the tree hierarchy for the accepted (and deserializable) messages
     * */
    private final static String META_CLASSNAME_JSON_FIELD = "CLASS_META_FIELD";
    private final static RuntimeTypeAdapterFactory<Message> runtimeTypeAdapterFactory =
            RuntimeTypeAdapterFactory
                    .of(Message.class, META_CLASSNAME_JSON_FIELD)
                    .registerSubtype(ServerStatusRequest.class, ServerStatusRequest.class.getName())
                    .registerSubtype(ConnectionAcceptanceEvent.class, ConnectionAcceptanceEvent.class.getName())
                    .registerSubtype(ServerStatusRequestReply.class, ServerStatusRequestReply.class.getName())
                    .registerSubtype(GameCreationRequest.class, GameCreationRequest.class.getName())
                    .registerSubtype(GameCreationRequestReply.class, GameCreationRequestReply.class.getName())
                    .registerSubtype(GameConnectionRequest.class, GameConnectionRequest.class.getName())
                    .registerSubtype(GameConnectionRequestReply.class, GameConnectionRequestReply.class.getName())
                    .registerSubtype(KeepAlive.class, KeepAlive.class.getName())
                    .registerSubtype(KeepAliveReply.class, KeepAliveReply.class.getName())
                    .registerSubtype(GameStartedEvent.class, GameStartedEvent.class.getName())
                    .registerSubtype(ModelUpdateEvent.class, ModelUpdateEvent.class.getName())
                    .registerSubtype(GameInsertionTurnRequest.class, GameInsertionTurnRequest.class.getName())
                    .registerSubtype(GameInsertionTurnRequestReply.class, GameInsertionTurnRequestReply.class.getName())
                    .registerSubtype(GameInsertionTurnResponse.class, GameInsertionTurnResponse.class.getName())
                    .registerSubtype(GameSelectionTurnRequest.class, GameSelectionTurnRequest.class.getName())
                    .registerSubtype(GameSelectionTurnRequestReply.class, GameSelectionTurnRequestReply.class.getName())
                    .registerSubtype(GameSelectionTurnResponse.class, GameSelectionTurnResponse.class.getName());


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
