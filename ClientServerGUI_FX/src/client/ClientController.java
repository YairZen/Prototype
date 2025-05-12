package client;

import java.io.*;
import java.sql.Date;
import java.util.function.Consumer;

import common.ChatIF;
import common.Message;
import common.MessageType;

/**
 * This class constructs the UI for a chat client.
 */

//make sure that the client connects to the server and handles user interaction logic
//this class serves as the main controller for client-side communication and message handling
public class ClientController implements ChatIF {

	public static int DEFAULT_PORT;
	private ChatClient client;

	/**
	 * Constructs an instance of the ClientController.
	 *
	 * @param host              The host to connect to.
	 * @param port              The port to connect on.
	 * @param onMessageReceived Callback to handle server responses in GUI.
	 */

//make sure that the client is initialized and connected to the server
	public ClientController(String host, int port, Consumer<Object> onMessageReceived) {
		try {
			client = new ChatClient(host, port, this, onMessageReceived);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection! Terminating client.");
			System.exit(1);
		}
	}

//make sure that a string command is passed to the client handler
	public void accept(String str) {
		client.handleMessageFromClientUI(str);
	}

//make sure that messages from the server are printed to the console
	public void display(String message) {
		System.out.println("> " + message);
	}

//make sure that a request is sent to the server to fetch all orders
	public void requestAllOrders() throws IOException {
		Message msg = new Message(MessageType.GET_ALL_ORDERS, null);
		client.sendToServer(msg);
	}

//make sure that an update request is sent to the server with order details
	public void updateOrder(int orderNumber, int parkingSpace, Date orderDate) throws IOException {
		Object[] data = { orderNumber, parkingSpace, orderDate };
		Message msg = new Message(MessageType.UPDATE_ORDER, data);
		client.sendToServer(msg);
	}

//make sure that the client connection is properly closed

	public void closeConnection() {
		try {
			client.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
