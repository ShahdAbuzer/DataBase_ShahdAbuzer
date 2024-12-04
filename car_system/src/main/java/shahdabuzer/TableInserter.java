package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class TableInserter {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public TableInserter(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    /**
     * Inserts values into a table.
     *
     * @param tableName 
     * @param values  
     */
    public void insertIntoTable(String tableName, String values) {
        String sql = "INSERT INTO " + tableName + " VALUES (" + values + ")";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error inserting into table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fetches all car IDs from the car table.
     *
     * @return ResultSet containing all car IDs.
     */
    public ResultSet fetchCarIds() {
        String sql = "SELECT carID FROM car";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {

            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error fetching car IDs: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetches all customer IDs from the customer table.
     *
     * @return ResultSet containing all customer IDs.
     */
    public ResultSet fetchCustomerIds() {
        String sql = "SELECT customerID FROM customer";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {

            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error fetching customer IDs: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetches all employee IDs from the employee table.
     *
     * @return ResultSet containing all employee IDs.
     */
    public ResultSet fetchEmployeeIds() {
        String sql = "SELECT employeeID FROM employee";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {

            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error fetching employee IDs: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inserts values into the order table with default values for orderDate and orderId.
     *
     * @param carId
     * @param customerId
     * @param employeeId
     * @param quantity
     */
    public void insertOrder(int carId, int customerId, int employeeId, int quantity) {
        String fetchPriceSql = "SELECT price FROM car WHERE carID = " + carId;
        String insertSql = "INSERT INTO order (carID, customerID, employeeID, orderDate, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

            ResultSet resultSet = statement.executeQuery(fetchPriceSql);
            if (resultSet.next()) {
                double price = resultSet.getDouble("price") * quantity;
                LocalDate orderDate = LocalDate.now();

                preparedStatement.setInt(1, carId);
                preparedStatement.setInt(2, customerId);
                preparedStatement.setInt(3, employeeId);
                preparedStatement.setDate(4, java.sql.Date.valueOf(orderDate));
                preparedStatement.setInt(5, quantity);
                preparedStatement.setDouble(6, price);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error inserting order: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
