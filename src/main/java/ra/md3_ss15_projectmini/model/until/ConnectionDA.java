package ra.md3_ss15_projectmini.model.until;

import java.sql.*;

public class ConnectionDA {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/Category_Book";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;

    }
    public static void closeConnection(Connection connection, CallableStatement callableStatement) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
