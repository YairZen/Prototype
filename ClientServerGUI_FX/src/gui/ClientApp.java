package gui;

import javafx.stage.Stage;

public class ClientApp {

    private ClientMainFrameController mainController;

    public void start(Stage primaryStage) throws Exception {
        mainController = new ClientMainFrameController();
        mainController.start(primaryStage);
    }

    public static void handleServerResponse(Object obj) {
        ClientMainFrameController.handleServerResponse(obj);
    }
}
