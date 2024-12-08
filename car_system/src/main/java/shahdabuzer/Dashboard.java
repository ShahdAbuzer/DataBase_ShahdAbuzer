package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Dashboard {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_shop";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static final String buttonHoverStyle = "-fx-background-color: #6D8A96; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
    private static final String sidebarTextStyle = "-fx-font-size: 16px; -fx-fill: #555555; -fx-cursor: hand; -fx-padding: 5px 0;";
    private static final String sidebarHoverStyle = "-fx-font-size: 18px; -fx-fill: #000000; -fx-background-color: #DDDDDD; -fx-cursor: hand; -fx-padding: 5px 0;";
    private static final String buttonStyle = "-fx-background-color: #89A8B2; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";

    private TableInserter tableInserter;
    private ReportGenerator reportGenerator;

    public Dashboard() {
        tableInserter = new TableInserter(DB_URL, DB_USERNAME, DB_PASSWORD);
        reportGenerator = new ReportGenerator(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public Dashboard(TableInserter tableInserter) {
        this.tableInserter = tableInserter;
        this.reportGenerator = new ReportGenerator(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public void showDashboard(Stage primaryStage, String userName) {
        HBox root = new HBox(20);
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #F1F0E8; -fx-padding: 30px;");

        VBox navigationBar = new VBox(20);
        navigationBar.setAlignment(Pos.TOP_LEFT);
        navigationBar.setStyle(
                "-fx-background-color: #E5E1DA; -fx-padding: 20px; -fx-border-color: #CCCCCC; -fx-border-width: 1px;");

        Text appName = new Text("Car Shop System");
        appName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #333333;");

        Text greeting = new Text("Hi " + userName + "!");
        greeting.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #333333;");

        ImageView profileImage = new ImageView(new Image("file:C:/Users/توب نت/Desktop/extra/dataBase/sedan.png"));
        profileImage.setFitWidth(100);
        profileImage.setFitHeight(100);
        profileImage.setStyle(
                "-fx-border-radius: 50%; -fx-background-radius: 50%; -fx-border-color: #89A8B2; -fx-border-width: 2px;");

        Text carsText = new Text("Cars");
        Text customersText = new Text("Customers");
        Text employeesText = new Text("Employees");
        Text reportsText = new Text("Reports");
        Text ordersText = new Text("Orders");
        Text paymentsText = new Text("Payments");
        Text servicesText = new Text("Services");

        carsText.setStyle(sidebarTextStyle);
        carsText.setOnMouseEntered(e -> carsText.setStyle(sidebarHoverStyle));
        carsText.setOnMouseExited(e -> carsText.setStyle(sidebarTextStyle));

        customersText.setStyle(sidebarTextStyle);
        customersText.setOnMouseEntered(e -> customersText.setStyle(sidebarHoverStyle));
        customersText.setOnMouseExited(e -> customersText.setStyle(sidebarTextStyle));

        employeesText.setStyle(sidebarTextStyle);
        employeesText.setOnMouseEntered(e -> employeesText.setStyle(sidebarHoverStyle));
        employeesText.setOnMouseExited(e -> employeesText.setStyle(sidebarTextStyle));

        reportsText.setStyle(sidebarTextStyle);
        reportsText.setOnMouseEntered(e -> reportsText.setStyle(sidebarHoverStyle));
        reportsText.setOnMouseExited(e -> reportsText.setStyle(sidebarTextStyle));

        ordersText.setStyle(sidebarTextStyle);
        ordersText.setOnMouseEntered(e -> ordersText.setStyle(sidebarHoverStyle));
        ordersText.setOnMouseExited(e -> ordersText.setStyle(sidebarTextStyle));

        paymentsText.setStyle(sidebarTextStyle);
        paymentsText.setOnMouseEntered(e -> paymentsText.setStyle(sidebarHoverStyle));
        paymentsText.setOnMouseExited(e -> paymentsText.setStyle(sidebarTextStyle));

        servicesText.setStyle(sidebarTextStyle);
        servicesText.setOnMouseEntered(e -> servicesText.setStyle(sidebarHoverStyle));
        servicesText.setOnMouseExited(e -> servicesText.setStyle(sidebarTextStyle));

        VBox mainContentPane = new VBox();
        mainContentPane.setStyle(
                "-fx-background-color: #FFFFFF; -fx-padding: 20px; -fx-border-color: #89A8B2; -fx-border-width: 2px;");
        mainContentPane.setMaxHeight(750);

        Text selectTableLabel = new Text("Select a table");
        selectTableLabel.setStyle("-fx-font-size: 18px; -fx-fill: #333333;");
        mainContentPane.getChildren().add(selectTableLabel);

        carsText.setOnMouseClicked(e -> showTable(mainContentPane, "cars"));
        customersText.setOnMouseClicked(e -> showTable(mainContentPane, "customers"));
        employeesText.setOnMouseClicked(e -> showTable(mainContentPane, "employees"));
        reportsText.setOnMouseClicked(e -> showReports(mainContentPane));
        ordersText.setOnMouseClicked(e -> showTable(mainContentPane, "orders"));
        paymentsText.setOnMouseClicked(e -> showTable(mainContentPane, "payments"));
        servicesText.setOnMouseClicked(e -> showTable(mainContentPane, "services"));

        mainContentPane.getChildren();

        navigationBar.getChildren().addAll(appName, profileImage, greeting, carsText, customersText, employeesText,
                ordersText, paymentsText, servicesText, reportsText);

        root.getChildren().addAll(navigationBar, mainContentPane);

        Scene dashboardScene = new Scene(root, 800, 600);
        primaryStage.setScene(dashboardScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void showTable(VBox mainContentPane, String tableName) {
        VBox tableContainer = new VBox(10);
        tableContainer.setStyle(
                "-fx-background-color: #FFFFFF; -fx-padding: 20px; -fx-border-color: #8B4513; -fx-border-width: 1px;");
    
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
    
        TableView<ObservableList<String>> table = new TableView<>();
        table.setPrefHeight(mainContentPane.getHeight());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {
    
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));
                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                table.getColumns().add(column);
    
                TextField field = new TextField();
                field.setPromptText(resultSet.getMetaData().getColumnName(i + 1));
                searchBar.getChildren().add(field);
            }
    
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

        Button searchButton = new Button("Search");
        searchButton.setStyle(buttonStyle);
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(buttonHoverStyle));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(buttonStyle));
        searchButton.setOnAction(e -> {
            StringBuilder query = new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE ");
            List<String> conditions = new ArrayList<>();
        
            for (int i = 0; i < searchBar.getChildren().size(); i++) {
                if (searchBar.getChildren().get(i) instanceof TextField) {
                    TextField field = (TextField) searchBar.getChildren().get(i);
                    String value = field.getText().trim();
                    if (!value.isEmpty()) {
                        String columnName = field.getPromptText(); // The column name
                        conditions.add(columnName + " LIKE '%" + value + "%'");
                    }
                }
            }
        
            if (!conditions.isEmpty()) {
                query.append(String.join(" AND ", conditions));
            } else {
                query = new StringBuilder("SELECT * FROM ").append(tableName); 
            }
        
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query.toString())) {
        
                data.clear();
                while (resultSet.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row.add(resultSet.getString(i));
                    }
                    data.add(row);
                }
            } catch (SQLException ex) {
                showAlert("Database Error", "Error executing search query: " + ex.getMessage(), Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        });
        
        Button insertButton = new Button("Insert");
        insertButton.setStyle(buttonStyle);
        insertButton.setOnMouseEntered(e -> insertButton.setStyle(buttonHoverStyle));
        insertButton.setOnMouseExited(e -> insertButton.setStyle(buttonStyle));
        insertButton.setOnAction(e -> {
            try {
                List<String> values = new ArrayList<>();
                for (Node node : searchBar.getChildren()) {
                    if (node instanceof TextField) {
                        TextField textField = (TextField) node;
                        String value = textField.getText().trim();
                        if (value.isEmpty()) {
                            showAlert("Input Error", "All fields must be filled before inserting.", Alert.AlertType.ERROR);
                            return;
                        }
                        values.add(value.replace("'", "''")); 
                    }
                }
        
                String concatenatedValues = String.join(",", values);
                TableInserter inserter = new TableInserter(DB_URL, DB_USERNAME, DB_PASSWORD);
                String tableName2 = tableName;
                inserter.insertIntoTable(tableName2, concatenatedValues);
                showAlert("Success", "Record inserted successfully.", Alert.AlertType.INFORMATION);
                ObservableList<String> newRow = FXCollections.observableArrayList(values);
                data.add(newRow);

            } catch (Exception e2) {
                e2.printStackTrace();
                showAlert("Error", "An error occurred: " + e2.getMessage(), Alert.AlertType.ERROR);
            }
        }); 
        
        Button updateCarButton = new Button("Update Car Price");
        updateCarButton.setStyle(buttonStyle);
        updateCarButton.setOnMouseEntered(e -> updateCarButton.setStyle(buttonHoverStyle));
        updateCarButton.setOnMouseExited(e -> updateCarButton.setStyle(buttonStyle));

        updateCarButton.setOnAction(e -> {
            TextField valueField = new TextField();
            valueField.setPromptText("Enter the value to add to price");

            HBox inputBox = new HBox(10);
            inputBox.setAlignment(Pos.CENTER);
            Button submitButton = new Button("Submit");
            submitButton.setStyle(buttonStyle);

            inputBox.getChildren().addAll(valueField, submitButton);
            tableContainer.getChildren().add(inputBox);

            submitButton.setOnAction(e2 -> {
                try {
                    String valueText = valueField.getText().trim();

                    if (valueText.isEmpty()) {
                        showAlert("Input Error", "Field must not be empty.", Alert.AlertType.ERROR);
                        return;
                    }

                    int valueToAdd;
                    try {
                        valueToAdd = Integer.parseInt(valueText);
                    } catch (NumberFormatException ex) {
                        showAlert("Input Error", "Value must be an integer.", Alert.AlertType.ERROR);
                        return;
                    }

                    Dialog dialog = new Dialog(DB_URL, DB_USERNAME, DB_PASSWORD);
                    dialog.updateTableValues("car", "price", valueToAdd);

                    showAlert("Success", "Car price updated successfully.", Alert.AlertType.INFORMATION);
                    tableContainer.getChildren().remove(inputBox);
                } catch (Exception ex) {
                    showAlert("Error", "An unexpected error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
                    ex.printStackTrace();
                }
            });
        });

        Button updateEmployeeButton = new Button("Update Employee Salary");
        updateEmployeeButton.setStyle(buttonStyle);
        updateEmployeeButton.setOnMouseEntered(e -> updateEmployeeButton.setStyle(buttonHoverStyle));
        updateEmployeeButton.setOnMouseExited(e -> updateEmployeeButton.setStyle(buttonStyle));

        updateEmployeeButton.setOnAction(e -> {
            TextField valueField = new TextField();
            valueField.setPromptText("Enter the value to add to salary");

            HBox inputBox = new HBox(10);
            inputBox.setAlignment(Pos.CENTER);
            Button submitButton = new Button("Submit");
            submitButton.setStyle(buttonStyle);

            inputBox.getChildren().addAll(valueField, submitButton);
            tableContainer.getChildren().add(inputBox);

            submitButton.setOnAction(e1 -> {
                try {
                    String valueText = valueField.getText().trim();

                    if (valueText.isEmpty()) {
                        showAlert("Input Error", "Field must not be empty.", Alert.AlertType.ERROR);
                        return;
                    }
                
                    int valueToAdd;
                    try {
                        valueToAdd = Integer.parseInt(valueText);
                    } catch (NumberFormatException ex) {
                        showAlert("Input Error", "Value must be an integer.", Alert.AlertType.ERROR);
                        return;
                    }
                
                    Dialog dialog = new Dialog(DB_URL, DB_USERNAME, DB_PASSWORD);
                    dialog.updateTableValues("employee", "salary", valueToAdd);

                    showAlert("Success", "Employee salary updated successfully.", Alert.AlertType.INFORMATION);
                    tableContainer.getChildren().remove(inputBox);
                } catch (Exception ex) {
                    showAlert("Error", "An unexpected error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
                    ex.printStackTrace();
                }
            });
        });
    
        System.out.println(tableName);
        if (tableName == "cars") {
            tableContainer.getChildren().addAll(updateCarButton);
        } else if (tableName == "employees") {
            tableContainer.getChildren().addAll(updateEmployeeButton);
        }
        
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);
        buttonBar.getChildren().addAll(searchButton, insertButton);

        tableContainer.getChildren().addAll(searchBar, buttonBar, table);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), tableContainer);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        

        mainContentPane.getChildren().setAll(tableContainer);
    }

    void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        TableInserter tableInserter = new TableInserter("jdbc:mysql://localhost:3306/car_system", "username",
                "password");
        new Dashboard(tableInserter);
    }

    private void showReports(VBox mainContentPane) {
        VBox reportContainer = new VBox(10);
        reportContainer.setStyle(
                "-fx-background-color: #FFFFFF; -fx-padding: 20px; -fx-border-color: #8B4513; -fx-border-width: 1px;");
    
        Text reportTitle = new Text("Reports");
        reportTitle.setStyle("-fx-font-size: 18px; -fx-fill: #333333;");
    
        ChoiceBox<String> reportTypeChoice = new ChoiceBox<>();
        reportTypeChoice.getItems().addAll(
                "Full Report - All Data",
                "Services for Car or Customer",
                "Sales by Employee",
                "Payments by Customer",
                "Revenue per Service Type",
                "Frequency of Services",
                "Service History by Vehicle"
        );
        reportTypeChoice.setValue("Full Report - All Data");
    
        TextField carIdField = new TextField();
        carIdField.setPromptText("Car ID (optional)");
    
        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID (optional)");
    
        TextField employeeIdField = new TextField();
        employeeIdField.setPromptText("Employee ID (optional)");
    
        Button generateReportButton = new Button("Generate Report");
        generateReportButton.setStyle(buttonStyle);
        generateReportButton.setOnMouseEntered(e -> generateReportButton.setStyle(buttonHoverStyle));
        generateReportButton.setOnMouseExited(e -> generateReportButton.setStyle(buttonStyle));
    
        TableView<Map<String, String>> reportTable = new TableView<>();
    
        generateReportButton.setOnAction(e -> {
            Map<String, String> params = new HashMap<>();
            String selectedReportType = reportTypeChoice.getValue();
    
            if (!carIdField.getText().isEmpty()) {
                params.put("car_id", carIdField.getText());
            }
            
            if (!customerIdField.getText().isEmpty()) {
                params.put("customer_id", customerIdField.getText());
            }
            if (!employeeIdField.getText().isEmpty()) {
                params.put("employee_id", employeeIdField.getText());
            }
    
            String report = reportGenerator.generateReport(selectedReportType, params);
            populateReportTable(report, reportTable);
        });
    
        reportContainer.getChildren().addAll(reportTitle, reportTypeChoice, carIdField, customerIdField, employeeIdField, generateReportButton, reportTable);
        mainContentPane.getChildren().setAll(reportContainer);
    }
    
    private void populateReportTable(String report, TableView<Map<String, String>> reportTable) {
        reportTable.getColumns().clear();
        reportTable.getItems().clear();
    
        String[] rows = report.split("\n-------------------------------\n");
        if (rows.length == 0 || report.isEmpty()) {
            return;
        }
    
        String[] keys = rows[0].split("\n");
        List<TableColumn<Map<String, String>, String>> columns = new ArrayList<>();
    
        for (String key : keys) {
            String columnName = key.split(":")[0].trim();
            TableColumn<Map<String, String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(columnName)));
            columns.add(column);
        }
    
        reportTable.getColumns().addAll(columns);
    
        for (String row : rows) {
            Map<String, String> dataRow = new HashMap<>();
            String[] fields = row.split("\n");
            for (String field : fields) {
                String[] parts = field.split(":");
                if (parts.length == 2) {
                    dataRow.put(parts[0].trim(), parts[1].trim());
                }
            }
            reportTable.getItems().add(dataRow);
        }
    }
}