package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableInserter {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public TableInserter(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public long insertIntoTable(String tableName, String[] columnNames, String[] values) {
        if (columnNames.length != values.length) {
            throw new IllegalArgumentException("Number of columns and values must match.");
        }

        StringBuilder columnsBuilder = new StringBuilder();
        StringBuilder placeholdersBuilder = new StringBuilder();
        for (int i = 0; i < columnNames.length; i++) {
            if (i > 0) {
                columnsBuilder.append(", ");
                placeholdersBuilder.append(", ");
            }
            columnsBuilder.append(columnNames[i]);
            placeholdersBuilder.append("?");
        }
        String insertSQL = "INSERT INTO " + tableName + " (" + columnsBuilder.toString() + ") VALUES (" + placeholdersBuilder.toString() + ")";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ResultSet fetchCarIds() {
        String sql = "SELECT CarID FROM cars";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet fetchCustomerIds() {
        String sql = "SELECT CustomerID FROM customers";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet fetchEmployeeIds() {
        String sql = "SELECT EmployeeID FROM employees";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet fetchPaymentIds() {
        String sql = "SELECT PaymentID FROM payments";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet fetchServiceIds() {
        String sql = "SELECT ServiceID FROM services";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet fetchUserAccountIds() {
        String sql = "SELECT UserID FROM user_account";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertOrder(int carId, int customerId, int employeeId, int quantity) {
        String fetchPriceSql = "SELECT Price FROM cars WHERE CarID = " + carId;
        String insertSql = "INSERT INTO orders (CarID, CustomerID, EmployeeID, OrderDate, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

            ResultSet resultSet = connection.createStatement().executeQuery(fetchPriceSql);
            if (resultSet.next()) {
                double price = resultSet.getDouble("Price") * quantity;
                java.sql.Date orderDate = java.sql.Date.valueOf(java.time.LocalDate.now());

                preparedStatement.setInt(1, carId);
                preparedStatement.setInt(2, customerId);
                preparedStatement.setInt(3, employeeId);
                preparedStatement.setDate(4, orderDate);
                preparedStatement.setInt(5, quantity);
                preparedStatement.setDouble(6, price);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
