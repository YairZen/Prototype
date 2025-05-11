package client;

import gui.ClientApp;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {

    public static ClientController chat; // instance to use throughout the app

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // יצירת מחלקת ChatClient עם טיפול בהודעות
        chat = new ClientController("localhost", 5555, ClientApp::handleServerResponse);

        // פתיחת ממשק המשתמש של הלקוח
        ClientApp clientApp = new ClientApp();
        clientApp.start(primaryStage);
    }
}
