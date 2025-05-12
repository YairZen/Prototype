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
public class ClientController implements ChatIF {
  
  public static int DEFAULT_PORT;
  private ChatClient client;

  /**
   * Constructs an instance of the ClientController.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   * @param onMessageReceived Callback to handle server responses in GUI.
   */
  public ClientController(String host, int port, Consumer<Object> onMessageReceived) {
    try {
      client = new ChatClient(host, port, this, onMessageReceived);
    } catch (IOException exception) {
      System.out.println("Error: Can't setup connection! Terminating client.");
      System.exit(1);
    }
  }

  public void accept(String str) {
    client.handleMessageFromClientUI(str);
  }

  public void display(String message) {
    System.out.println("> " + message);
  }

  // 🔽 הוספה חדשה: בקשת כל ההזמנות
  public void requestAllOrders() throws IOException {
    Message msg = new Message(MessageType.GET_ALL_ORDERS, null);
    client.sendToServer(msg);
  }

  // 🔽 הוספה חדשה: עדכון הזמנה
  public void updateOrder(int orderNumber, int parkingSpace, Date orderDate) throws IOException {
    Object[] data = { orderNumber, parkingSpace, orderDate };
    Message msg = new Message(MessageType.UPDATE_ORDER, data);
    client.sendToServer(msg);
  }

  public void closeConnection() {
    try {
      client.closeConnection();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
