package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.ChatModel;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;
import it.polimi.ingsw.model.game.GameMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ChatTests {

    @Test
    @DisplayName("Chat Test: positive addiction of a message made of a single word, sent directly one to one")
    public void addMessageTestPositivelySingleWordCase() {
        MessageRecipient recipient = new MessageRecipient.Direct("Marco");

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient, "Hi");

        assertEquals(1, chatModel.getMessagesFor("Marco").size());
        assertEquals("Hi", chatModel.getMessagesFor("Marco").get(0).text());
    }


    @Test
    @DisplayName("Chat Test: positive addiction of some messages made of a single word, sent directly one to one")
    public void addMessageTestPositivelySingleWordMoreMessagesCase() {
        MessageRecipient recipient = new MessageRecipient.Direct("Marco");

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient, "Hi");
        chatModel.addMessage("Alberto", recipient, "Bella");


        assertEquals(2, chatModel.getMessagesFor("Marco").size());
        assertEquals("Bella", chatModel.getMessagesFor("Marco").get(1).text());
    }

    @Test
    @DisplayName("Chat Test: negative addiction of some messages made of a single word, sent directly one to one")
    public void addMessageTestNegativelySingleWordMoreMessagesCase() {
        MessageRecipient recipient = new MessageRecipient.Direct("Marco");

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient, "Bella");

        assertNotEquals(2, chatModel.getMessagesFor("Marco").size());
        assertNotEquals("Balla", chatModel.getMessagesFor("Marco").get(0).text());
    }

    @Test
    @DisplayName("Chat Test: positive addiction of some messages composed by phrases, sent directly one to one")
    public void addMessageTestPositivelyComposedMessagesMoreMessagesCase() {
        MessageRecipient recipient = new MessageRecipient.Direct("Marco");

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient, "Alberto is making fun of marco");
        chatModel.addMessage("Alberto", recipient, "Marco is trying to select the wrong tiles from the board");

        assertEquals(2, chatModel.getMessagesFor("Marco").size());
        assertEquals("Marco is trying to select the wrong tiles from the board", chatModel.getMessagesFor("Marco").get(1).text());
    }

    @Test
    @DisplayName("Chat Test: peer to peer chat message from two player with two messages made of different words")
    public void addMessageTestPositivelyPeerToPeer() {
        MessageRecipient recipient1 = new MessageRecipient.Direct("Marco");
        MessageRecipient recipient2 = new MessageRecipient.Direct("Alberto");

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient1, "Hi");
        chatModel.addMessage("Marco", recipient2, "Hi");

        assertEquals(2, chatModel.getMessagesFor("Marco").size());
        assertEquals(2, chatModel.getMessagesFor("Alberto").size());
        assertEquals("Hi", chatModel.getMessagesFor("Marco").get(0).text());

        chatModel.addMessage("Alberto", recipient1, "Come stai?");
        chatModel.addMessage("Marco", recipient2, "Tutto apposto");

        assertEquals(4, chatModel.getMessagesFor("Marco").size());
        assertEquals(4, chatModel.getMessagesFor("Alberto").size());
        assertEquals("Come stai?", chatModel.getMessagesFor("Marco").get(2).text());
    }

    @Test
    @DisplayName("Chat Test: positive addiction of a message made of a single word, sent by broadcast")
    public void addMessageTestPositivelyByBroadcastSingleWordCase() {
        MessageRecipient recipient = new MessageRecipient.Broadcast();

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient, "Hi");

        assertEquals(1, chatModel.getMessagesFor("Marco").size());
        assertEquals(1, chatModel.getMessagesFor("Cookie").size());
        assertEquals(1, chatModel.getMessagesFor("Giulia").size());

        assertEquals("Hi", chatModel.getMessagesFor("Marco").get(0).text());
        assertEquals("Hi", chatModel.getMessagesFor("Cookie").get(0).text());
        assertEquals("Hi", chatModel.getMessagesFor("Giulia").get(0).text());

    }

    @Test
    @DisplayName("Chat Test: positive addiction of a message made of a multiple words, sent by broadcast")
    public void addMessageTestPositivelyByBroadcastMultipleWordCase() {
        MessageRecipient recipient = new MessageRecipient.Broadcast();

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient, "Alberto is trying to test this chat");

        assertEquals(1, chatModel.getMessagesFor("Marco").size());
        assertEquals(1, chatModel.getMessagesFor("Cookie").size());
        assertEquals(1, chatModel.getMessagesFor("Cookie").size());

        assertEquals("Alberto is trying to test this chat", chatModel.getMessagesFor("Marco").get(0).text());
        assertEquals("Alberto is trying to test this chat", chatModel.getMessagesFor("Cookie").get(0).text());
        assertEquals("Alberto is trying to test this chat", chatModel.getMessagesFor("Giulia").get(0).text());

    }

    @Test
    @DisplayName("Chat Test: positive addiction of a message made of a multiple words, sent by broadcast from different players")
    public void addMessageTestPositivelyByBroadcastMultipleWordsFromDifferentPlayersCase() {
        MessageRecipient recipient = new MessageRecipient.Broadcast();

        ChatModel chatModel = new ChatModel();

        chatModel.addMessage("Alberto", recipient, "Alberto is trying to test this chat");
        chatModel.addMessage("Marco", recipient, "Marco is trying to test this chat");
        chatModel.addMessage("Cookie", recipient, "Cookie is trying to test this chat");

        assertEquals(3, chatModel.getMessagesFor("Marco").size());
        assertEquals(3, chatModel.getMessagesFor("Cookie").size());
        assertEquals(3, chatModel.getMessagesFor("Cookie").size());

        assertEquals("Alberto is trying to test this chat", chatModel.getMessagesFor("Marco").get(0).text());
        assertEquals("Alberto is trying to test this chat", chatModel.getMessagesFor("Cookie").get(0).text());
        assertEquals("Alberto is trying to test this chat", chatModel.getMessagesFor("Giulia").get(0).text());

        assertEquals("Marco is trying to test this chat", chatModel.getMessagesFor("Alberto").get(1).text());
        assertEquals("Marco is trying to test this chat", chatModel.getMessagesFor("Cookie").get(1).text());
        assertEquals("Marco is trying to test this chat", chatModel.getMessagesFor("Giulia").get(1).text());

        assertEquals("Cookie is trying to test this chat", chatModel.getMessagesFor("Marco").get(2).text());
        assertEquals("Cookie is trying to test this chat", chatModel.getMessagesFor("Alberto").get(2).text());
        assertEquals("Cookie is trying to test this chat", chatModel.getMessagesFor("Giulia").get(2).text());
    }
}