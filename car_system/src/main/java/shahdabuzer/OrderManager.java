package shahdabuzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderManager {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public OrderManager(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public void showOrderManagementScreen(Stage primaryStage) {
        // Layout setup
        VBox layout = new VBox(10);

        // ComboBoxes for filtering
        ComboBox<Integer> carIdComboBox = new ComboBox<>();
        ComboBox<Integer> customerIdComboBox = new ComboBox<>();
        ComboBox<Integer> employeeIdComboBox = new ComboBox<>();

        TableView<Order> tableView = new TableView<>();

        // Configure TableView columns
        TableColumn<Order, Integer> carIdColumn = new TableColumn<>("Car ID");
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));

        TableColumn<Order, Integer> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<Order, Integer> employeeIdColumn = new TableColumn<>("Employee ID");
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        TableColumn<Order, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(carIdColumn, customerIdColumn, employeeIdColumn, quantityColumn, priceColumn);

        // Populate ComboBoxes
        populateComboBox("SELECT carID FROM car", carIdComboBox);
        populateComboBox("SELECT customerID FROM customer", customerIdComboBox);
        populateComboBox("SELECT employeeID FROM employee", employeeIdComboBox);

        // Populate TableView with all orders initially
        updateTableView(tableView, null, null, null);

        // Add listeners to ComboBoxes
        carIdComboBox.setOnAction(e -> updateTableView(tableView, carIdComboBox.getValue(), null, null));
        customerIdComboBox.setOnAction(e -> updateTableView(tableView, null, customerIdComboBox.getValue(), null));
        employeeIdComboBox.setOnAction(e -> updateTableView(tableView, null, null, employeeIdComboBox.getValue()));

        // Add components to layout
        layout.getChildren().addAll(carIdComboBox, customerIdComboBox, employeeIdComboBox, tableView);

        // Show scene
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Order Management");
        primaryStage.show();
    }

    private void populateComboBox(String query, ComboBox<Integer> comboBox) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ObservableList<Integer> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(resultSet.getInt(1));
            }
            comboBox.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTableView(TableView<Order> tableView, Integer carId, Integer customerId, Integer employeeId) {
        ObservableList<Order> data = FXCollections.observableArrayList();

        // Base query
        String sql = "SELECT o.carID, o.customerID, o.employeeID, o.quantity, o.price " +
                     "FROM `order` o " +
                     "JOIN car c ON o.carID = c.carID " +
                     "JOIN customer cu ON o.customerID = cu.customerID " +
                     "JOIN employee e ON o.employeeID = e.employeeID ";

        // Apply filters
        boolean hasCondition = false;
        if (carId != null) {
            sql += "WHERE o.carID = " + carId + " ";
            hasCondition = true;
        }
        if (customerId != null) {
            sql += (hasCondition ? "AND " : "WHERE ") + "o.customerID = " + customerId + " ";
            hasCondition = true;
        }
        if (employeeId != null) {
            sql += (hasCondition ? "AND " : "WHERE ") + "o.employeeID = " + employeeId + " ";
        }

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                data.add(new Order(
                        resultSet.getInt("carID"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("employeeID"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(data);
    }

    public static class Order {
        private final Integer carId;
        private final Integer customerId;
        private final Integer employeeId;
        private final Integer quantity;
        private final Double price;

        public Order(Integer carId, Integer customerId, Integer employeeId, Integer quantity, Double price) {
            this.carId = carId;
            this.customerId = customerId;
            this.employeeId = employeeId;
            this.quantity = quantity;
            this.price = price;
        }

        public Integer getCarId() {
            return carId;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public Integer getEmployeeId() {
            return employeeId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Double getPrice() {
            return price;
        }
    }
}
