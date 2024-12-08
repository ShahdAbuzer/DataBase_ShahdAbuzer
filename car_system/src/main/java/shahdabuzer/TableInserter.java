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
    public boolean insertIntoTable(String tableName, String values) {
        String query = "INSERT INTO " + tableName + " VALUES (" + values + ")";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

    /**
     * Fetches all car IDs from the car table.
     *
     * @return ResultSet containing all car IDs.
     */
    public ResultSet fetchCarIds() {
        String sql = "SELECT CarID FROM cars";
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
        String sql = "SELECT CustomerID FROM customers";
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
        String sql = "SELECT EmployeeID FROM employees";
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
        String fetchPriceSql = "SELECT Price FROM cars WHERE CarID = " + carId;
        String insertSql = "INSERT INTO orders (CarID, CustomerID, EmployeeID, OrderDate, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

            ResultSet resultSet = statement.executeQuery(fetchPriceSql);
            if (resultSet.next()) {
                double price = resultSet.getDouble("TotalPrice") * quantity;
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
