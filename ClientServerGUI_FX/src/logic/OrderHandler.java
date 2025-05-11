package logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Server.DBcontroler;
import entities.Order;

public class OrderHandler {

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `Order`";

        try (Connection conn = DBcontroler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("parking_space"),
                        rs.getInt("order_number"),
                        rs.getDate("order_date"),
                        rs.getInt("confirmation_code"),
                        rs.getInt("subscriber_id"),
                        rs.getDate("date_of_placing_an_order")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static boolean updateOrder(int orderNumber, int newParkingSpace, Date newOrderDate) {
        String query = "UPDATE `Order` SET parking_space = ?, order_date = ? WHERE order_number = ?";

        try (Connection conn = DBcontroler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newParkingSpace);
            stmt.setDate(2, newOrderDate);
            stmt.setInt(3, orderNumber);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
