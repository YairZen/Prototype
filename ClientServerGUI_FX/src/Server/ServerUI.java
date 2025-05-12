package Server;

import gui.ServerInfoFrameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerUI extends Application {

	final public static int DEFAULT_PORT = 5555;
	public static EchoServer server;

	// make sure that the JavaFX application is launched correctly
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	// make sure that the server GUI is initialized and shown when the application
	// starts
	@Override
	public void start(Stage primaryStage) throws Exception {
		ServerInfoFrameController controller = new ServerInfoFrameController();
		controller.start(primaryStage);
	}

	// make sure that the server is created and starts listening on the given port
	public static void runServer(String p) {
		int port = 0;

		try {
			port = Integer.parseInt(p);
		} catch (Throwable t) {
			System.out.println("ERROR - Invalid port number!");
			return;
		}

		server = new EchoServer(port);

		try {
			server.listen();
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
