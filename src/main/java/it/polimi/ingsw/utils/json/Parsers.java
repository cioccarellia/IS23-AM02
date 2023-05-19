package it.polimi.ingsw.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.*;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.network.tcp.messages.request.*;
import it.polimi.ingsw.network.tcp.messages.request.replies.*;

public class Parsers {

    private final static Gson defaultGson = new Gson();
    private final static String META_CLASSNAME_JSON_FIELD = "CLASS_META_FIELD";
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


    private final static String META_CLASSNAME_SEALED1_JSON_FIELD = "CLASS_SEALED1_META_FIELD";

    private final static RuntimeTypeAdapterFactory<SingleResult> sealedAdapter =
            RuntimeTypeAdapterFactory
                    .of(SingleResult.class, META_CLASSNAME_SEALED1_JSON_FIELD) // typeFieldName
                    .registerSubtype(SingleResult.Failure.class, "success")
                    .registerSubtype(SingleResult.Success.class, "failure");


    private final static String META_CLASSNAME_SEALED2_JSON_FIELD = "CLASS_SEALED2_META_FIELD";

    private final static RuntimeTypeAdapterFactory<RequestError> requestErrorAdapter =
            RuntimeTypeAdapterFactory
                    .of(RequestError.class, META_CLASSNAME_SEALED2_JSON_FIELD) // typeFieldName
                    .registerSubtype(BookshelfInsertionFailure.class, BookshelfInsertionFailure.class.getName())
                    .registerSubtype(GameConnectionError.class, GameConnectionError.class.getName())
                    .registerSubtype(GameStartError.class, GameStartError.class.getName())
                    .registerSubtype(StatusError.class, StatusError.class.getName())
                    .registerSubtype(TileSelectionFailures.class, TileSelectionFailures.class.getName());


    private static final Gson marshaledGson = new GsonBuilder()
            .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
            .registerTypeAdapterFactory(sealedAdapter)
            .registerTypeAdapterFactory(requestErrorAdapter)
            .create();

    public static Gson defaultGson() {
        return defaultGson;
    }

    public static Gson marshaledGson() {
        return marshaledGson;
    }

}
