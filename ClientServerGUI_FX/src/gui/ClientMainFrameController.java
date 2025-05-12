package gui;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import client.ClientUI;
import common.Message;
import entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ClientMainFrameController {

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TextField orderNumberField;
    @FXML
    private TextField parkingSpaceField;
    @FXML
    private TextField orderDateField;
    @FXML
    private Button loadBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Label statusLabel;

    public static void handleServerResponse(Object obj) {
        if (obj instanceof Message message) {
            switch (message.getType()) {
                case RESPONSE_ORDERS -> {
                    List<Order> orders = (List<Order>) message.getData();
                    ObservableList<Order> list = FXCollections.observableArrayList(orders);
                    javafx.application.Platform.runLater(() -> {
                        ((ClientMainFrameController) loader.getController()).ordersTable.setItems(list);
                        ((ClientMainFrameController) loader.getController()).statusLabel.setText("Orders loaded.");
                    });
                }
                case RESPONSE_SUCCESS -> showStatus("Order updated successfully.", "green");
                case RESPONSE_ERROR -> showStatus("Update failed. Check order number.", "red");
            }
        }
    }

    private static void showStatus(String message, String color) {
        javafx.application.Platform.runLater(() -> {
            ((ClientMainFrameController) loader.getController()).statusLabel.setText(message);
            ((ClientMainFrameController) loader.getController()).statusLabel.setStyle("-fx-text-fill: " + color + ";");
        });
    }

    public void loadOrders() throws IOException {
        ClientUI.chat.requestAllOrders();
    }

    public void updateOrder() {
        try {
            int orderNumber = Integer.parseInt(orderNumberField.getText().trim());
            int parkingSpace = Integer.parseInt(parkingSpaceField.getText().trim());

            LocalDate localDate = LocalDate.parse(orderDateField.getText().trim()).plusDays(1); // הוספת יום אחד
            Date orderDate = Date.valueOf(localDate);


            ClientUI.chat.updateOrder(orderNumber, parkingSpace, orderDate);
        } catch (Exception e) {
            statusLabel.setText("Invalid input.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public void exitApp() {
        System.exit(0);
    }

    private static FXMLLoader loader;

    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("/gui/ClientMainFrame.fxml"));
        Parent root = loader.load();

        ClientMainFrameController controller = loader.getController();
        controller.initializeTableColumns();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/ClientMainFrame.css").toExternalForm());

        primaryStage.setTitle("Client - Order Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeTableColumns() {
        TableColumn<Order, Integer> orderNumberCol = new TableColumn<>("Order Number");
        orderNumberCol.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));

        TableColumn<Order, Integer> parkingSpaceCol = new TableColumn<>("Parking Space");
        parkingSpaceCol.setCellValueFactory(new PropertyValueFactory<>("parkingSpace"));

        TableColumn<Order, Date> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, Integer> confirmationCodeCol = new TableColumn<>("Confirmation Code");
        confirmationCodeCol.setCellValueFactory(new PropertyValueFactory<>("confirmationCode"));

        TableColumn<Order, Integer> subscriberIdCol = new TableColumn<>("Subscriber ID");
        subscriberIdCol.setCellValueFactory(new PropertyValueFactory<>("subscriberId"));

        TableColumn<Order, Date> placingDateCol = new TableColumn<>("Date of Placing Order");
        placingDateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfPlacingAnOrder"));

        ordersTable.getColumns().addAll(orderNumberCol, parkingSpaceCol, orderDateCol,
                confirmationCodeCol, subscriberIdCol, placingDateCol);
    }
}
