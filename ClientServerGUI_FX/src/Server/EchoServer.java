package Server;

import java.net.InetAddress;
import java.sql.Date;
import java.util.List;

import common.Message;
import common.MessageType;
import entities.Order;
import logic.OrderHandler;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 */
public class EchoServer extends AbstractServer {

    public EchoServer(int port) {
        super(port);
        DBcontroler.connectToDB(); // חיבור למסד נתונים
    }

    @Override
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    @Override
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }

    @Override
    public void clientConnected(ConnectionToClient client) {
        try {
            InetAddress addr = InetAddress.getByName(client.getInetAddress().getHostAddress());
            System.out.println("Client connected: " + addr.getHostAddress() + " (" + addr.getHostName() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);

        if (msg instanceof Message) {
            Message message = (Message) msg;

            switch (message.getType()) {
                case GET_ALL_ORDERS:
                    List<Order> orders = OrderHandler.getAllOrders();
                    sendToClient(client, new Message(MessageType.RESPONSE_ORDERS, orders));
                    break;

                case UPDATE_ORDER:
                    Object[] data = (Object[]) message.getData();
                    int orderNumber = (Integer) data[0];
                    int newParkingSpace = (Integer) data[1];
                    Date newOrderDate = (Date) data[2];
                    boolean success = OrderHandler.updateOrder(orderNumber, newParkingSpace, newOrderDate);
                    MessageType responseType = success ? MessageType.RESPONSE_SUCCESS : MessageType.RESPONSE_ERROR;
                    sendToClient(client, new Message(responseType, null));
                    break;

                default:
                    System.out.println("Unknown message type received.");
                    break;
            }
        } else {
            System.out.println("Invalid message type received.");
        }
    }

    private void sendToClient(ConnectionToClient client, Message message) {
        try {
            client.sendToClient(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
