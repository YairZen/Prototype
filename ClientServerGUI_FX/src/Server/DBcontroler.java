package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBcontroler {

	// make sure that the MySQL driver is loaded and a test connection is
	// established
	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			System.out.println("Driver definition failed");
		}

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dbprototype?serverTimezone=IST",
					"root", "Aa123456");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	// make sure that a valid connection object is returned for executing SQL
	// queries
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost/dbprototype?serverTimezone=IST", "root", "Aa123456");
	}

	// make sure that a new student record is inserted into the 'student' table in
	// the database
	public static void saveUserToDB(ArrayList<String> student) {
		String insertQuery = "INSERT INTO student (id, pname, lname, fc) VALUES ('" + student.get(1) + "', '"
				+ student.get(0) + "', '" + student.get(2) + "', '" + student.get(3) + "');";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/dbprototype?serverTimezone=IST", "root",
					"Aa123456");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("row effected: " + rowsAffected);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
