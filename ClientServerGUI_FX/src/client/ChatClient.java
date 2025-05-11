package client;

import ocsf.client.*;
import common.ChatIF;

import java.io.*;
import java.util.function.Consumer;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class ChatClient extends AbstractClient {
    ChatIF clientUI;
    private Consumer<Object> onMessageReceived;

    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port);
        this.clientUI = clientUI;
    }

    public ChatClient(String host, int port, ChatIF clientUI, Consumer<Object> onMessageReceived) throws IOException {
        super(host, port);
        this.clientUI = clientUI;
        this.onMessageReceived = onMessageReceived;
        openConnection(); // ✅ חובה כדי לפתוח את ה־Socket
    }


    @Override
    public void handleMessageFromServer(Object msg) {
        System.out.println("--> handleMessageFromServer");

        // הצג בקונסול (אם זה String)
        if (msg instanceof String) {
            clientUI.display("> " + msg);
        }

        // העבר ל־GUI אם יש מאזין
        if (onMessageReceived != null) {
            onMessageReceived.accept(msg);
        }
    }

    public void handleMessageFromClientUI(String message) {
        try {
            openConnection(); // מאפשר שליחה של כמה הודעות
            sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
            clientUI.display("Could not send message to server: Terminating client." + e);
            quit();
        }
    }

    public void sendToServer(Object obj) {
        try {
            super.sendToServer(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {}
        System.exit(0);
    }
}
