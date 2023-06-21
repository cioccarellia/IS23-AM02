package it.polimi.ingsw.chat;

import it.polimi.ingsw.model.ChatModel;
import it.polimi.ingsw.model.chat.MessageRecipient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatTests {

    // users
    String user1 = "Marco";
    String user2 = "Alberto";
    String user3 = "Cookie";
    String user4 = "Giulia";

    // recipients
    MessageRecipient recipient1 = new MessageRecipient.Direct(user1);
    MessageRecipient recipient2 = new MessageRecipient.Direct(user2);
    MessageRecipient recipient3 = new MessageRecipient.Direct(user3);
    MessageRecipient recipient4 = new MessageRecipient.Direct(user4);
    MessageRecipient recipientBroadcast = new MessageRecipient.Broadcast();


    // tests for addMessage direct
    @Test
    @DisplayName("Tests the correct addition of a direct message - 1")
    public void addDirectMessage_test1() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user2, recipient1, "Hi");

        // the receiver has the message
        assertEquals(1, chatModel.getMessagesFor(user1).size());
        assertEquals("Hi", chatModel.getMessagesFor(user1).get(0).text());

        // the sender has the message
        assertEquals(1, chatModel.getMessagesFor(user2).size());
        assertEquals("Hi", chatModel.getMessagesFor(user2).get(0).text());
    }

    @Test
    @DisplayName("Tests the correct addition of a direct message - 2")
    public void addDirectMessage_test2() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user2, recipient1, "Hi Marco");
        chatModel.addMessage(user2, recipient1, "How are you?");

        // the receiver has the messages
        assertEquals(2, chatModel.getMessagesFor(user1).size());
        assertEquals("How are you?", chatModel.getMessagesFor(user1).get(1).text());
        assertNotEquals("How are you?", chatModel.getMessagesFor(user1).get(0).text());


        // the sender has the messages
        assertEquals(2, chatModel.getMessagesFor(user2).size());
        assertEquals("Hi Marco", chatModel.getMessagesFor(user2).get(0).text());
    }

    @Test
    @DisplayName("Tests the correct addition of a direct message - 3")
    public void addDirectMessage_test3() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user2, recipient1, "Hi Marco");

        // the receiver has the messages
        assertEquals(1, chatModel.getMessagesFor(user1).size());
        assertNotEquals("Hi Alberto", chatModel.getMessagesFor(user1).get(0).text());

        // the sender has the messages
        assertEquals(1, chatModel.getMessagesFor(user2).size());
        assertNotEquals("Hi Alberto", chatModel.getMessagesFor(user2).get(0).text());
    }

    @Test
    @DisplayName("Tests the correct addition of a direct message - 4")
    public void addDirectMessage_test4() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user2, recipient1, "Hi Marco");
        chatModel.addMessage(user1, recipient2, "Hi Alberto");

        // the receiver has the messages
        assertEquals(2, chatModel.getMessagesFor(user1).size());
        assertEquals("Hi Marco", chatModel.getMessagesFor("Marco").get(0).text());

        // the sender has the messages
        assertEquals(2, chatModel.getMessagesFor(user2).size());
        assertEquals("Hi Alberto", chatModel.getMessagesFor("Alberto").get(1).text());

        chatModel.addMessage(user2, recipient1, "How are you?");
        chatModel.addMessage(user1, recipient2, "I'm fine, thank you");

        // both sender and receiver have two new messages
        assertEquals(4, chatModel.getMessagesFor(user1).size());
        assertEquals(4, chatModel.getMessagesFor(user2).size());
        assertEquals("How are you?", chatModel.getMessagesFor(user1).get(2).text());
        assertEquals("I'm fine, thank you", chatModel.getMessagesFor(user2).get(3).text());
    }


    // tests for addMessage broadcast
    @Test
    @DisplayName("Tests the correct addition of a broadcast message - 1")
    public void addBroadcastMessage_test1() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user2, recipientBroadcast, "Hi");

        assertEquals(1, chatModel.getMessagesFor(user1).size());
        assertEquals(1, chatModel.getMessagesFor(user2).size());
        assertEquals(1, chatModel.getMessagesFor(user3).size());
        assertEquals(1, chatModel.getMessagesFor(user4).size());

        assertEquals("Hi", chatModel.getMessagesFor(user1).get(0).text());
        assertEquals("Hi", chatModel.getMessagesFor(user2).get(0).text());
        assertEquals("Hi", chatModel.getMessagesFor(user3).get(0).text());
        assertEquals("Hi", chatModel.getMessagesFor(user4).get(0).text());
    }

    @Test
    @DisplayName("Tests the correct addition of a broadcast message - 2")
    public void addBroadcastMessage_test2() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user2, recipientBroadcast, "I'm trying to test the chat, can you see this?");

        assertEquals(1, chatModel.getMessagesFor(user1).size());
        assertEquals(1, chatModel.getMessagesFor(user2).size());
        assertEquals(1, chatModel.getMessagesFor(user3).size());
        assertEquals(1, chatModel.getMessagesFor(user4).size());

        assertEquals("I'm trying to test the chat, can you see this?", chatModel.getMessagesFor(user1).get(0).text());
        assertEquals("I'm trying to test the chat, can you see this?", chatModel.getMessagesFor(user2).get(0).text());
        assertEquals("I'm trying to test the chat, can you see this?", chatModel.getMessagesFor(user3).get(0).text());
        assertEquals("I'm trying to test the chat, can you see this?", chatModel.getMessagesFor(user4).get(0).text());
    }

    @Test
    @DisplayName("Tests the correct addition of a broadcast message - 3")
    public void addBroadcastMessage_test3() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user1, recipientBroadcast, "Hello guys");
        chatModel.addMessage(user2, recipientBroadcast, "Hello everyone!");
        chatModel.addMessage(user3, recipientBroadcast, "Hello hello");


        assertEquals(3, chatModel.getMessagesFor(user1).size());
        assertEquals(3, chatModel.getMessagesFor(user2).size());
        assertEquals(3, chatModel.getMessagesFor(user3).size());
        assertEquals(3, chatModel.getMessagesFor(user4).size());

        assertEquals("Hello guys", chatModel.getMessagesFor(user1).get(0).text());
        assertEquals("Hello guys", chatModel.getMessagesFor(user2).get(0).text());
        assertEquals("Hello guys", chatModel.getMessagesFor(user3).get(0).text());
        assertEquals("Hello guys", chatModel.getMessagesFor(user4).get(0).text());

        assertEquals("Hello everyone!", chatModel.getMessagesFor(user1).get(1).text());
        assertEquals("Hello everyone!", chatModel.getMessagesFor(user2).get(1).text());
        assertEquals("Hello everyone!", chatModel.getMessagesFor(user3).get(1).text());
        assertEquals("Hello everyone!", chatModel.getMessagesFor(user4).get(1).text());

        assertEquals("Hello hello", chatModel.getMessagesFor(user1).get(2).text());
        assertEquals("Hello hello", chatModel.getMessagesFor(user2).get(2).text());
        assertEquals("Hello hello", chatModel.getMessagesFor(user3).get(2).text());
        assertEquals("Hello hello", chatModel.getMessagesFor(user4).get(2).text());
    }

    // tests for addMessage both broadcast and direct
    @Test
    @DisplayName("Tests the correct addition of both broadcast and direct messages - 1")
    public void addBroadcastAndDirectMessages_test1() {
        ChatModel chatModel = new ChatModel();

        chatModel.addMessage(user2, recipientBroadcast, "Hello everyone!");
        chatModel.addMessage(user1, recipient3, "Hey Cookie");
        chatModel.addMessage(user3, recipient4, "Hello Giulia");

        assertEquals(2, chatModel.getMessagesFor(user1).size());
        assertEquals(1, chatModel.getMessagesFor(user2).size());
        assertEquals(3, chatModel.getMessagesFor(user3).size());
        assertEquals(2, chatModel.getMessagesFor(user4).size());

        // user1 messages - two messages
        assertEquals("Hello everyone!", chatModel.getMessagesFor(user1).get(0).text());
        assertEquals("Hey Cookie", chatModel.getMessagesFor(user1).get(1).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user1).get(2).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user1).get(3).text());

        // user2 messages - one message
        assertEquals("Hello everyone!", chatModel.getMessagesFor(user2).get(0).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user2).get(1).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user2).get(2).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user2).get(3).text());

        // user3 messages - three messages
        assertEquals("Hello everyone!", chatModel.getMessagesFor(user3).get(0).text());
        assertEquals("Hey Cookie", chatModel.getMessagesFor(user3).get(1).text());
        assertEquals("Hello Giulia", chatModel.getMessagesFor(user3).get(2).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user3).get(3).text());

        // user4 messages - two messages
        assertEquals("Hello everyone!", chatModel.getMessagesFor(user4).get(0).text());
        assertEquals("Hello Giulia", chatModel.getMessagesFor(user4).get(1).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user4).get(2).text());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> chatModel.getMessagesFor(user4).get(3).text());
    }
}