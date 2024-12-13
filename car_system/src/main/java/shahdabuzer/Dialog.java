package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Dialog {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public Dialog(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public void updateTableValues(String tableName, String column, double value, boolean isPercentage, TableView<Map<String, String>> tableView) {
        String sql;
        if (isPercentage) {
            sql = "UPDATE " + tableName + " SET " + column + " = " + column + " + (" + column + " * ? / 100)";
        } else {
            sql = "UPDATE " + tableName + " SET " + column + " = " + column + " + ?";
        }
    
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, value); 
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(rowsUpdated + " row(s) updated.");
            
            loadTable(tableName, tableView);
            tableView.refresh();
        } catch (SQLException e) {
            System.err.println("Error updating table values: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public void loadTable(String tableName, TableView<Map<String, String>> tableView) {
        String query = "SELECT * FROM " + tableName;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            tableView.getItems().clear();
            tableView.getColumns().clear();

            java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                TableColumn<Map<String, String>, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(columnName)));
                tableView.getColumns().add(column);
            }

            ObservableList<Map<String, String>> rows = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = resultSet.getString(i);
                    row.put(columnName, value);
                }
                rows.add(row);
            }

            tableView.setItems(rows);
            System.out.println("Table " + tableName + " loaded successfully.");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error loading table: " + e.getMessage());
            alert.showAndWait();
        }
        tableView.refresh();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}