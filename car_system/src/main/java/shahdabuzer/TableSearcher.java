package shahdabuzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableSearcher {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public TableSearcher(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public void searchTable(String tableName, String query, TableView<ObservableList<String>> table) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        String sql = "SELECT * FROM " + tableName + " WHERE " + query;

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(data);
    }
}
