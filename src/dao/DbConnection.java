package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection conn = null;

    public static void main(String[] args) {
        Connection connection = DbConnection.getDbC();
        if (connection != null) {
            System.out.println("Connection OK!");
            DbConnection.closeConnection();
        } else {
            System.out.println("Connection Failed!");
        }
    }

    public static Connection getDbC() {
        String url = "jdbc:mysql://localhost:3306/POS";
        String user = "root";
        String password = "1234";
        String mysqlForName = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(mysqlForName);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("No driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("No connection");
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed.");
                conn = null; // Reset the connection to null after closing
            } catch (SQLException e) {
                System.out.println("Failed to close connection");
                e.printStackTrace();
            }
        }
    }
}
