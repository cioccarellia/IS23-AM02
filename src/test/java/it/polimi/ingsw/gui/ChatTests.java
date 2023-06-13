package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.game.gui.GuiGameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatTests {
    GameModel game = new GameModel(GameMode.GAME_MODE_2_PLAYERS);

    @Test
    @DisplayName("Test the correct insertion of message sent directly inside an observable list of ChatTextMessage objects")
    public void chatTestObservableListInsertionDirectPositively() {
        game.addPlayer("Giulia");
        game.addPlayer("Alberto");
        game.onGameStarted();

        Timestamp time = new Timestamp(14);
        MessageRecipient giuliaMessageRecipient = new MessageRecipient.Direct("Alberto");
        ChatTextMessage giuliaToAlberto = new ChatTextMessage("Giulia", giuliaMessageRecipient, "Ciao", time);
        List<ChatTextMessage> messages = new ArrayList<>();
        messages.add(giuliaToAlberto);

        ObservableList<String> observableMessage = FXCollections.observableArrayList(messages.stream().map(ChatTextMessage::toString).toList());
        String viewMessage = observableMessage.get(0);

        assertEquals(giuliaToAlberto.toString(), viewMessage);
    }

    @Test
    @DisplayName("Test the correct insertion of message sent by broadcast inside an observable list of ChatTextMessage objects")
    public void chatTestObservableListInsertionBroadcastPositively() {
        game.addPlayer("Cookie");
        game.addPlayer("Giulia");
        game.onGameStarted();

        Timestamp time = new Timestamp(14);
        MessageRecipient broadcastMessageRecipient = new MessageRecipient.Broadcast();
        ChatTextMessage cookieToAll = new ChatTextMessage("Cookie", broadcastMessageRecipient, "Ciao", time);
        List<ChatTextMessage> messages = new ArrayList<>();
        messages.add(cookieToAll);

        ObservableList<String> observableMessage = FXCollections.observableArrayList(messages.stream().map(ChatTextMessage::toString).toList());
        String viewMessage = observableMessage.get(0);

        assertEquals(cookieToAll.toString(), viewMessage);
    }

    @Test
    @DisplayName("Test the correct insertion of multiple messages sent by broadcast and directly inside an observable list of ChatTextMessage objects")
    public void chatTestObservableListInsertionBroadcastAndDirectlyPositively() {
        game.addPlayer("Marco");
        game.addPlayer("Giulia");
        game.onGameStarted();

        Timestamp time = new Timestamp(14);
        MessageRecipient broadcastMessageRecipient = new MessageRecipient.Broadcast();
        MessageRecipient marcoMessageRecipient = new MessageRecipient.Direct("Marco");
        ChatTextMessage marcoToAll = new ChatTextMessage("Marco", broadcastMessageRecipient, "Ciao", time);
        ChatTextMessage giuliaToMarco= new ChatTextMessage("Marco", marcoMessageRecipient, "Ciao", time);
        List<ChatTextMessage> messages = new ArrayList<>();

        messages.add(marcoToAll);
        messages.add(giuliaToMarco);

        ObservableList<String> observableMessage = FXCollections.observableArrayList(messages.stream().map(ChatTextMessage::toString).toList());
        String insertedBroadCastMessage = observableMessage.get(0);
        String insertedDirectMessage = observableMessage.get(1);


        assertEquals(marcoToAll.toString(), insertedBroadCastMessage);
        assertEquals(giuliaToMarco.toString(), insertedDirectMessage);
    }

    @Test
    @DisplayName("Test the correct update of multiple messages sent directly inside an observable list of ChatTextMessage objects")
    public void chatTestObservableListUpdateDirectlyPositively() {
        game.addPlayer("Cookie");
        game.addPlayer("Alberto");
        game.onGameStarted();

        Timestamp time = new Timestamp(14);
        MessageRecipient cookieMessageRecipient = new MessageRecipient.Direct("Alberto");
        MessageRecipient albertoMessageRecipient = new MessageRecipient.Direct("Cookie");

        ChatTextMessage cookieToAlberto = new ChatTextMessage("Cookie", albertoMessageRecipient, "Ciao", time);
        ChatTextMessage albertoToCookie = new ChatTextMessage("Alberto", cookieMessageRecipient, "Ciao a te", time);

        List<ChatTextMessage> messages = new ArrayList<>();
        GuiGameController controller = new GuiGameController();

        messages.add(cookieToAlberto);
        messages.add(albertoToCookie);


        controller.chatModelUpdateTest(messages);

        ChatTextMessage expectedMessage = controller.getMessages().get(0) ;
        ChatTextMessage expectedMessage1 = controller.getMessages().get(1) ;


        assertEquals(cookieToAlberto,expectedMessage);
        assertEquals(albertoToCookie,expectedMessage1);

    }

    @Test
    @DisplayName("Test the correct update of multiple messages sent by broadcast inside an observable list of ChatTextMessage objects")
    public void chatTestObservableListUpdateBroadcastPositively() {
        game.addPlayer("Marco");
        game.addPlayer("Giulia");
        game.onGameStarted();

        Timestamp time = new Timestamp(14);
        MessageRecipient broadcastRecipient = new MessageRecipient.Broadcast();

        ChatTextMessage marcoToAll = new ChatTextMessage("Marco", broadcastRecipient, "Ciao", time);
        ChatTextMessage giuliaToAll = new ChatTextMessage("Giulia", broadcastRecipient, "Ciao a te", time);

        List<ChatTextMessage> messages = new ArrayList<>();
        GuiGameController controller = new GuiGameController();

        messages.add(marcoToAll);
        messages.add(giuliaToAll);


        controller.chatModelUpdateTest(messages);

        ChatTextMessage expectedMessage = controller.getMessages().get(0) ;
        ChatTextMessage expectedMessage1 = controller.getMessages().get(1) ;


        assertEquals(marcoToAll,expectedMessage);
        assertEquals(giuliaToAll,expectedMessage1);

    }

    @Test
    @DisplayName("Test the correct update of multiple messages sent by broadcast and directly inside an observable list of ChatTextMessage objects")
    public void chatTestObservableListUpdateBroadcastAndDirectlyPositively() {
        game.addPlayer("Marco");
        game.addPlayer("Alberto");
        game.onGameStarted();

        Timestamp time = new Timestamp(14);
        MessageRecipient albertoMessageRecipient = new MessageRecipient.Direct("Marco");
        MessageRecipient broadCastRecipient = new MessageRecipient.Broadcast();

        ChatTextMessage marcoToAll = new ChatTextMessage("Marco", broadCastRecipient, "Ciao", time);
        ChatTextMessage albertoToMarco = new ChatTextMessage("Alberto", albertoMessageRecipient, "Ciao a te", time);

        List<ChatTextMessage> messages = new ArrayList<>();
        GuiGameController controller = new GuiGameController();

        messages.add(marcoToAll);
        messages.add(albertoToMarco);


        controller.chatModelUpdateTest(messages);

        ChatTextMessage expectedMessage = controller.getMessages().get(0) ;
        ChatTextMessage expectedMessage1 = controller.getMessages().get(1) ;


        assertEquals(marcoToAll,expectedMessage);
        assertEquals(albertoToMarco,expectedMessage1);

    }

}