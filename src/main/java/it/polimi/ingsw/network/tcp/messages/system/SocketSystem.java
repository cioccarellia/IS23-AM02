package it.polimi.ingsw.network.tcp.messages.system;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.utils.json.Parsers;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

public class SocketSystem {

    /**
     * Serializes the given message into JSON using a {@link RuntimeTypeAdapterFactory}
     * and sends it onto the socket stream immediately
     * */
    public static <I extends Message> void sendAsync(@NotNull PrintWriter socketOutput, I request, Class<I> serializationClass) {
        // serializes to JSON the message content
        String serializedJsonRequest = Parsers.marshaledGson().toJson(request, serializationClass);

        // sends the message bytes on TCP
        socketOutput.println(serializedJsonRequest);
        socketOutput.flush();
    }
}