package client;

import gui.ClientApp;
import javafx.application.Application;
import javafx.stage.Stage;

//make sure that the JavaFX client application is launched and connected to the server
//this class serves as the entry point for the client-side user interface
public class ClientUI extends Application {

	public static ClientController chat; // instance to use throughout the app

	// make sure that the JavaFX runtime launches the client application
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	// make sure that the client connects to the server and opens the user interface
	@Override
	public void start(Stage primaryStage) throws Exception {
		// יצירת מחלקת ChatClient עם טיפול בהודעות
		chat = new ClientController("localhost", 5555, ClientApp::handleServerResponse);

		// פתיחת ממשק המשתמש של הלקוח
		ClientApp clientApp = new ClientApp();
		clientApp.start(primaryStage);
	}
}
