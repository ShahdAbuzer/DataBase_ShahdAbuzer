package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dialog {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public Dialog(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public void updateTableValues(String tableName, String column, int valueToAdd) {
    String sql = "UPDATE " + tableName + " SET " + column + " = " + column + " + ?";
    try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, valueToAdd);
        int rowsUpdated = preparedStatement.executeUpdate();
        System.out.println(rowsUpdated + " row(s) updated.");
    } catch (SQLException e) {
        System.err.println("Error updating table values: " + e.getMessage());
        e.printStackTrace();
    }
}

}