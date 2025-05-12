package client;

import ocsf.client.*;
import common.ChatIF;

import java.io.*;
import java.util.function.Consumer;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 */

//make sure that the client handles the connection to the server and processes incoming/outgoing messages
//this class extends AbstractClient and manages the network communication layer
public class ChatClient extends AbstractClient {
	ChatIF clientUI;
	private Consumer<Object> onMessageReceived;

	// make sure that a basic client is created without a custom message handler

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port);
		this.clientUI = clientUI;
	}

	// make sure that the client is created and connected, with a GUI handler for
	// messages

	public ChatClient(String host, int port, ChatIF clientUI, Consumer<Object> onMessageReceived) throws IOException {
		super(host, port);
		this.clientUI = clientUI;
		this.onMessageReceived = onMessageReceived;
		openConnection(); // ✅ חובה כדי לפתוח את ה־Socket
	}

	// make sure that messages received from the server are processed and routed to
	// UI or callback
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

	// make sure that a message from the UI is sent to the server after opening
	// connection

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

	// make sure that any object can be safely sent to the server
	public void sendObject(Object obj) {
		try {
			sendToServer(obj); // קורא למתודה המקורית (final)
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// make sure that the client connection is closed and the application exits
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
