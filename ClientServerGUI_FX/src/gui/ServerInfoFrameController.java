package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Server.ServerUI;

public class ServerInfoFrameController {

    @FXML
    private TextArea clientInfoArea;

    @FXML
    public Label statusLabel;

    public void exitApp() {
        System.exit(0);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ServerInfoFrame.fxml"));
        Parent root = loader.load();

        // לאחר הטעינה, קבל את ה־Controller שנוצר מה־FXML
        ServerInfoFrameController controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/ServerInfoFrame.css").toExternalForm());
        primaryStage.setTitle("Server - Connected Clients");
        primaryStage.setScene(scene);
        primaryStage.show();

        // הפעלת השרת
        ServerUI.runServer("5555");

        // עדכון תצוגה
        controller.statusLabel.setText("Server running on port 5555");
        controller.statusLabel.setStyle("-fx-text-fill: green;");
        controller.clientInfoArea.setText("Client: 127.0.0.1\nClient: 192.168.1.10\n...");
    }
}
