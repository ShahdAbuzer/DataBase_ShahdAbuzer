package shahdabuzer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Dashboard {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_shop";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    TableView<Map<String, String>> tableView = new TableView<>();
    private TableView<ObservableList<String>> table = new TableView<>();
    public static final String usedColor="#89A8B2";
    public static final String redColor="#e60808";

    private static final String buttonHoverStyle = "-fx-background-color: #6D8A96; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
    private static final String sidebarTextStyle = "-fx-font-size: 16px; -fx-fill: #555555; -fx-cursor: hand; -fx-padding: 5px 0;";
    private static final String sidebarHoverStyle = "-fx-font-size: 18px; -fx-fill: #000000; -fx-background-color: #DDDDDD; -fx-cursor: hand; -fx-padding: 5px 0;";
    private static final String buttonStyle = "-fx-background-color: #89A8B2; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
    private static final String textFieldStyle = "-fx-background-color: #F1F1F1; -fx-border-color: #CCCCCC; -fx-border-radius: 5px; -fx-padding: 5px;";
    private static final String comboBoxStyle = "-fx-background-color: #F1F1F1; -fx-border-color: #CCCCCC; -fx-border-radius: 5px; -fx-padding: 5px;";

    private TableInserter tableInserter;
    private ReportGenerator reportGenerator;
    static int initialCarSize;
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
        Text abouText = new Text("About me");
        Text logoutText = new Text("Logout");

        carsText.setStyle(sidebarTextStyle);
        carsText.setOnMouseEntered(_ -> carsText.setStyle(sidebarHoverStyle));
        carsText.setOnMouseExited(_ -> carsText.setStyle(sidebarTextStyle));

        customersText.setStyle(sidebarTextStyle);
        customersText.setOnMouseEntered(_ -> customersText.setStyle(sidebarHoverStyle));
        customersText.setOnMouseExited(_ -> customersText.setStyle(sidebarTextStyle));

        employeesText.setStyle(sidebarTextStyle);
        employeesText.setOnMouseEntered(_ -> employeesText.setStyle(sidebarHoverStyle));
        employeesText.setOnMouseExited(_ -> employeesText.setStyle(sidebarTextStyle));

        reportsText.setStyle(sidebarTextStyle);
        reportsText.setOnMouseEntered(_ -> reportsText.setStyle(sidebarHoverStyle));
        reportsText.setOnMouseExited(_ -> reportsText.setStyle(sidebarTextStyle));

        ordersText.setStyle(sidebarTextStyle);
        ordersText.setOnMouseEntered(_ -> ordersText.setStyle(sidebarHoverStyle));
        ordersText.setOnMouseExited(_ -> ordersText.setStyle(sidebarTextStyle));

        paymentsText.setStyle(sidebarTextStyle);
        paymentsText.setOnMouseEntered(_ -> paymentsText.setStyle(sidebarHoverStyle));
        paymentsText.setOnMouseExited(_ -> paymentsText.setStyle(sidebarTextStyle));

        servicesText.setStyle(sidebarTextStyle);
        servicesText.setOnMouseEntered(_ -> servicesText.setStyle(sidebarHoverStyle));
        servicesText.setOnMouseExited(_ -> servicesText.setStyle(sidebarTextStyle));

        abouText.setStyle(sidebarTextStyle);
        abouText.setOnMouseEntered(_ -> abouText.setStyle(sidebarHoverStyle));
        abouText.setOnMouseExited(_ -> abouText.setStyle(sidebarTextStyle));

        logoutText.setStyle(sidebarTextStyle);
        logoutText.setOnMouseEntered(_ -> logoutText.setStyle(sidebarHoverStyle));
        logoutText.setOnMouseExited(_ -> logoutText.setStyle(sidebarTextStyle));

        VBox mainContentPane = new VBox();
        mainContentPane.setStyle(
                "-fx-background-color: #FFFFFF; -fx-padding: 20px; -fx-border-color: #89A8B2; -fx-border-width: 2px;");
        mainContentPane.setMaxHeight(750);

        Text selectTableLabel = new Text("Select a table");
        selectTableLabel.setStyle("-fx-font-size: 18px; -fx-fill: #333333;");

        mainContentPane.getChildren().addAll(selectTableLabel);

        carsText.setOnMouseClicked(_ -> {
            table.setId("cars");
            showTable(mainContentPane, "cars");
        });

        customersText.setOnMouseClicked(_ -> {
            table.setId("customers");
            showTable(mainContentPane, "customers");
        });

        employeesText.setOnMouseClicked(_ -> {
            table.setId("employees");
            showTable(mainContentPane, "employees");
        });

        ordersText.setOnMouseClicked(_ -> {
            table.setId("orders");
            showTable(mainContentPane, "orders");
        });

        paymentsText.setOnMouseClicked(_ -> {
            table.setId("payments");
            showTable(mainContentPane, "payments");
        });

        servicesText.setOnMouseClicked(_ -> {
            table.setId("services");
            showTable(mainContentPane, "services");
        });
        reportsText.setOnMouseClicked(_ -> showReports(mainContentPane));

        logoutText.setOnMouseClicked(_ -> {
            Logout logout = new Logout();
            logout.showLogoutScreen(primaryStage);
        });
        abouText.setOnMouseClicked(event -> {
            Text introductionText = new Text("Project by ");
            introductionText.setStyle("-fx-font-size: 15px; -fx-fill: #555555;");

            Text nameText = new Text("Shahd Abuzer");
            nameText.setStyle("-fx-font-size: 18px; -fx-fill: #1a73e8; -fx-font-weight: bold;");

            Text universityText = new Text(", a Software Engineering student at the ");
            universityText.setStyle("-fx-font-size: 15px; -fx-fill: #555555;");

            Text uniNameText = new Text("University of Bethlehem");
            uniNameText.setStyle("-fx-font-size: 17px; -fx-fill: #ff5722; -fx-font-weight: bold;");

            Text detailsText = new Text(
                    "\nThis project is part of the Database course. I hope you find it insightful and engaging.\n");
            detailsText.setStyle("-fx-font-size: 15px; -fx-fill: #555555;");

            Text idText = new Text("Student ID: ");
            idText.setStyle("-fx-font-size: 15px; -fx-fill: #333333;");

            Text idValueText = new Text("202201332\n");
            idValueText.setStyle("-fx-font-size: 16px; -fx-fill: #e91e63; -fx-font-weight: bold;");

            Text emailText = new Text("Email: ");
            emailText.setStyle("-fx-font-size: 15px; -fx-fill: #333333;");

            Text emailValueText = new Text("202201332@bethlehem.edu\n");
            emailValueText.setStyle("-fx-font-size: 16px; -fx-fill: #4caf50; -fx-font-weight: bold;");

            Text closingText = new Text(
                    "\nFeel free to explore the project, and don't hesitate to provide feedback!");
            closingText.setStyle("-fx-font-size: 15px; -fx-fill: #555555;");

            TextFlow headerTextFlow = new TextFlow(introductionText, nameText, universityText, uniNameText);
            TextFlow idTextFlow = new TextFlow(idText, idValueText);
            TextFlow emailTextFlow = new TextFlow(emailText, emailValueText);

            mainContentPane.getChildren().clear();
            mainContentPane.getChildren().addAll(headerTextFlow, detailsText, idTextFlow, emailTextFlow, closingText);
        });

        mainContentPane.getChildren();

        navigationBar.getChildren().addAll(appName, profileImage, greeting, carsText, customersText, employeesText,
                ordersText, paymentsText, servicesText, reportsText, abouText, logoutText);

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

        table.setPrefHeight(mainContentPane.getHeight());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 0; i < columnCount; i++) {
                final int colIndex = i;
                String columnName = metaData.getColumnName(i + 1);
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);

                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));
                table.getColumns().add(column);

                TextField field = new TextField();
                field.setPromptText(columnName);
                field.setStyle(textFieldStyle);
                searchBar.getChildren().add(field);
            }

            if (columnCount > 0) {
                for (TableColumn<ObservableList<String>, ?> column : table.getColumns()) {
                    column.prefWidthProperty().bind(table.widthProperty().divide(columnCount).subtract(1)); // Subtracting
                                                                                                            // 1 for
                                                                                                            // padding
                }
            }

            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showNotification("Failed to load data from table: " + tableName, mainContentPane ,redColor);
            return;
        }

        table.setItems(data);

        table.widthProperty().addListener((obs, oldVal, newVal) -> {
            int currentColumnCount = table.getColumns().size();
            if (currentColumnCount > 0) {
                for (TableColumn<ObservableList<String>, ?> column : table.getColumns()) {
                    try {
                        column.setPrefWidth((newVal.doubleValue() / currentColumnCount) - 1);
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }
                }
            }
        });
        updateRecord(table, mainContentPane, Color.valueOf(usedColor));
        mainContentPane.getChildren().clear();  
        mainContentPane.getChildren().add(table);  
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
                        String columnName = field.getPromptText();
                        conditions.add(columnName + " LIKE '%" + value.replace("'", "''") + "%'");
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
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getString(i));
                    }
                    data.add(row);
                }
            } catch (SQLException ex) {
                showNotification("An error occurred while searching the table.", mainContentPane , redColor);
                ex.printStackTrace();
            }
        });

        Map<String, String> tableIdMap = new HashMap<>();
        Button insertButton = new Button("Insert");
        insertButton.setStyle(buttonStyle);
        insertButton.setOnMouseEntered(e -> insertButton.setStyle(buttonHoverStyle));
        insertButton.setOnMouseExited(e -> insertButton.setStyle(buttonStyle));
        insertButton.setOnAction(e -> {
            try {
                List<String> values = new ArrayList<>();
                List<String> columnNames = new ArrayList<>();
                String idColumn = tableIdMap.getOrDefault(tableName.toLowerCase(), "ID");
                for (Node node : searchBar.getChildren()) {
                    if (node instanceof TextField) {
                        TextField textField = (TextField) node;
                        String columnName = textField.getPromptText();
                        if (columnName.equalsIgnoreCase(idColumn)) {
                            continue;
                        }
                        String value = textField.getText().trim();
                        if (value.isEmpty()) {
                            showNotification("All fields must be filled.", mainContentPane ,redColor);
                            return;
                        }
                        columnNames.add(columnName);
                        values.add(value.replace("'", "''"));
                    }
                }
                StringBuilder columnsBuilder = new StringBuilder();
                StringBuilder placeholdersBuilder = new StringBuilder();
                for (int i = 0; i < columnNames.size(); i++) {
                    if (i > 0) {
                        columnsBuilder.append(", ");
                        placeholdersBuilder.append(", ");
                    }
                    columnsBuilder.append(columnNames.get(i));
                    placeholdersBuilder.append("?");
                }
                String insertSQL = "INSERT INTO " + tableName + " (" + columnsBuilder.toString() + ") VALUES ("
                        + placeholdersBuilder.toString() + ")";
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                PreparedStatement.RETURN_GENERATED_KEYS)) {
                    for (int i = 0; i < values.size(); i++) {
                        preparedStatement.setString(i + 1, values.get(i));
                    }
                    System.out.println(preparedStatement);
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            showNotification("Record inserted successfully with ID: " + generatedId, mainContentPane ,usedColor);
                            List<String> newRow = new ArrayList<>();
                            newRow.add(String.valueOf(generatedId));
                            newRow.addAll(values);
                            data.add(FXCollections.observableArrayList(newRow));
                            for (Node node : searchBar.getChildren()) {
                                if (node instanceof TextField) {
                                    ((TextField) node).clear();
                                }
                            }
                        } else {
                            showNotification("Record inserted successfully !!!!",
                                    mainContentPane,usedColor);
                                    showTable(mainContentPane, tableName); 

                        }
                    } else {
                        
                        showNotification("Failed to insert the record.", mainContentPane , redColor);
                    }
                }
            } catch (SQLException e2) {
                handleSQLException(e2, mainContentPane, tableName);
            }
        });

        Button updateCarPriceButton = new Button("Update Car Price");
        updateCarPriceButton.setStyle(buttonStyle);
        updateCarPriceButton.setOnMouseEntered(e -> updateCarPriceButton.setStyle(buttonHoverStyle));
        updateCarPriceButton.setOnMouseExited(e -> updateCarPriceButton.setStyle(buttonStyle));

        updateCarPriceButton.setOnAction(e -> {
            HBox optionsBox = new HBox(10);
            optionsBox.setAlignment(Pos.CENTER);

            Button numberButton = new Button("Number");
            numberButton.setStyle(buttonStyle);
            Button percentageButton = new Button("Percentage");
            percentageButton.setStyle(buttonStyle);

            optionsBox.getChildren().addAll(numberButton, percentageButton);
            tableContainer.getChildren().add(optionsBox);

            numberButton.setOnAction(e1 -> {
                TextField valueField = new TextField();
                valueField.setPromptText("Enter the value to add to price");

                Button submitButton = new Button("Submit");
                submitButton.setStyle(buttonStyle);

                HBox inputBox = new HBox(10);
                inputBox.setAlignment(Pos.CENTER);
                inputBox.getChildren().addAll(valueField, submitButton);
                tableContainer.getChildren().add(inputBox);

                submitButton.setOnAction(e2 -> {
                    try {
                        String valueText = valueField.getText().trim();

                        if (valueText.isEmpty()) {
                            showNotification("Fields must not be empty.", mainContentPane , redColor);
                            return;
                        }

                        int valueToAdd;
                        try {
                            valueToAdd = Integer.parseInt(valueText);
                        } catch (NumberFormatException ex) {
                            showNotification("Value must be a valid number.", mainContentPane , redColor);
                            return;
                        }

                        String updateSQL = "UPDATE cars SET Price = Price + ?";
                        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

                            preparedStatement.setInt(1, valueToAdd);
                            int rowsUpdated = preparedStatement.executeUpdate();

                            if (rowsUpdated > 0) {
                                showNotification("Car price successfully added.", mainContentPane ,usedColor);
                                showTable(mainContentPane, "cars"); 
                            } else {
                                showNotification("No records were updated.", mainContentPane , redColor);
                            }
                        }

                        tableContainer.getChildren().remove(inputBox);
                        tableContainer.getChildren().remove(optionsBox);

                    } catch (SQLException ex) {
                        showNotification("An unexpected error occurred: " + ex.getMessage(), mainContentPane ,redColor);
                        ex.printStackTrace();
                    }
                });
            });

            percentageButton.setOnAction(e1 -> {
                TextField valueField = new TextField();
                valueField.setPromptText("Enter the percentage to adjust price");

                Button submitButton = new Button("Submit");
                submitButton.setStyle(buttonStyle);

                HBox inputBox = new HBox(10);
                inputBox.setAlignment(Pos.CENTER);
                inputBox.getChildren().addAll(valueField, submitButton);
                tableContainer.getChildren().add(inputBox);

                submitButton.setOnAction(e2 -> {
                    try {
                        String valueText = valueField.getText().trim();

                        if (valueText.isEmpty()) {
                            showNotification("Field must not be empty.", mainContentPane ,redColor);
                            return;
                        }

                        double percentageToAdd;
                        try {
                            percentageToAdd = Double.parseDouble(valueText);
                        } catch (NumberFormatException ex) {
                            showNotification("Value must be a valid number.", mainContentPane ,redColor);
                            return;
                        }

                        String updateSQL = "UPDATE cars SET Price = Price * (1 + ? / 100)";
                        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

                            preparedStatement.setDouble(1, percentageToAdd);
                            int rowsUpdated = preparedStatement.executeUpdate();

                            if (rowsUpdated > 0) {
                                showNotification("Car price updated successfully.", mainContentPane ,usedColor);
                                showTable(mainContentPane, "cars"); 
                            } else {
                                showNotification("No records were updated.", mainContentPane,redColor);
                            }
                        }

                        tableContainer.getChildren().remove(inputBox);
                        tableContainer.getChildren().remove(optionsBox);

                    } catch (SQLException ex) {
                        showNotification("An unexpected error occurred: " + ex.getMessage(), mainContentPane,redColor);
                        ex.printStackTrace();
                    }
                });
            });
        });

        Button updateEmployeeSalaryButton = new Button("Update Employee Salary");
        updateEmployeeSalaryButton.setStyle(buttonStyle);
        updateEmployeeSalaryButton.setOnMouseEntered(e -> updateEmployeeSalaryButton.setStyle(buttonHoverStyle));
        updateEmployeeSalaryButton.setOnMouseExited(e -> updateEmployeeSalaryButton.setStyle(buttonStyle));

        updateEmployeeSalaryButton.setOnAction(e -> {
            HBox optionsBox = new HBox(10);
            optionsBox.setAlignment(Pos.CENTER);

            Button numberButton = new Button("Number");
            numberButton.setStyle(buttonStyle);
            Button percentageButton = new Button("Percentage");
            percentageButton.setStyle(buttonStyle);

            optionsBox.getChildren().addAll(numberButton, percentageButton);
            tableContainer.getChildren().add(optionsBox);

            numberButton.setOnAction(e1 -> {
                TextField valueField = new TextField();
                valueField.setPromptText("Enter the value to add to salary");

                Button submitButton = new Button("Submit");
                submitButton.setStyle(buttonStyle);

                HBox inputBox = new HBox(10);
                inputBox.setAlignment(Pos.CENTER);
                inputBox.getChildren().addAll(valueField, submitButton);
                tableContainer.getChildren().add(inputBox);

                submitButton.setOnAction(e2 -> {
                    try {
                        String valueText = valueField.getText().trim();

                        if (valueText.isEmpty()) {
                            showNotification("Field must not be empty.", mainContentPane ,redColor);
                            return;
                        }

                        int valueToAdd;
                        try {
                            valueToAdd = Integer.parseInt(valueText);
                        } catch (NumberFormatException ex) {
                            showNotification("Value must be a valid number.", mainContentPane ,redColor);
                            return;
                        }

                        String updateSQL = "UPDATE employees SET Salary = Salary + ?";
                        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

                            preparedStatement.setInt(1, valueToAdd);
                            int rowsUpdated = preparedStatement.executeUpdate();

                            if (rowsUpdated > 0) {
                                showNotification("Employee salary updated successfully.", mainContentPane ,usedColor);
                                showTable(mainContentPane, "employees"); 
                            } else {
                                showNotification("No records were updated.", mainContentPane ,redColor);
                            }
                        }

                        tableContainer.getChildren().remove(inputBox);
                        tableContainer.getChildren().remove(optionsBox);

                    } catch (SQLException ex) {
                        showNotification("An unexpected error occurred: " + ex.getMessage(), mainContentPane , redColor);
                        ex.printStackTrace();
                    }
                });
            });

            percentageButton.setOnAction(e1 -> {
                TextField valueField = new TextField();
                valueField.setPromptText("Enter the percentage to adjust salary");

                Button submitButton = new Button("Submit");
                submitButton.setStyle(buttonStyle);

                HBox inputBox = new HBox(10);
                inputBox.setAlignment(Pos.CENTER);
                inputBox.getChildren().addAll(valueField, submitButton);
                tableContainer.getChildren().add(inputBox);

                submitButton.setOnAction(e2 -> {
                    try {
                        String valueText = valueField.getText().trim();

                        if (valueText.isEmpty()) {
                            showNotification("Field must not be empty.", mainContentPane , redColor);
                            return;
                        }

                        double percentageToAdd;
                        try {
                            percentageToAdd = Double.parseDouble(valueText);
                        } catch (NumberFormatException ex) {
                            showNotification("Value must be a valid number.", mainContentPane ,redColor);
                            return;
                        }

                        String updateSQL = "UPDATE employees SET Salary = Salary * (1 + ? / 100)";
                        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

                            preparedStatement.setDouble(1, percentageToAdd);
                            int rowsUpdated = preparedStatement.executeUpdate();

                            if (rowsUpdated > 0) {
                                showNotification("Employee Salary Updated Successfully!", mainContentPane ,usedColor);
                                showTable(mainContentPane, "employees"); 
                            } else {
                                showNotification("No records were updated.", mainContentPane , redColor);
                            }
                        }

                        tableContainer.getChildren().remove(inputBox);
                        tableContainer.getChildren().remove(optionsBox);

                    } catch (SQLException ex) {
                        showNotification("An unexpected error occurred: " + ex.getMessage(), mainContentPane , usedColor);
                        ex.printStackTrace();
                    }
                });
            });
        });

        if (tableName.equals("cars")) {
            tableContainer.getChildren().addAll(updateCarPriceButton);
        } else if (tableName.equals("employees")) {
            tableContainer.getChildren().addAll(updateEmployeeSalaryButton);
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
        reportTypeChoice.setStyle(comboBoxStyle);
        reportTypeChoice.getItems().addAll(
                "Services for Car or Customer",
                "Sales by Employee",
                "Payments by Customer",
                "Revenue per Service Type",
                "Frequency of Services",
                "Service History by Vehicle");
        reportTypeChoice.setValue("Services for Car or Customer");

        TextField carIdField = new TextField();
        carIdField.setPromptText("Car ID optional");
        carIdField.setStyle(textFieldStyle);

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID optional");
        customerIdField.setStyle(textFieldStyle);

        TextField employeeIdField = new TextField();
        employeeIdField.setPromptText("Employee ID optional");
        employeeIdField.setStyle(textFieldStyle);

        TextField startDateField = new TextField();
        startDateField.setPromptText("Start Date (YYYY-MM-DD) optional");
        startDateField.setStyle(textFieldStyle);

        TextField endDateField = new TextField();
        endDateField.setPromptText("End Date (YYYY-MM-DD) optional");
        endDateField.setStyle(textFieldStyle);

        carIdField.setVisible(false);
        customerIdField.setVisible(false);
        employeeIdField.setVisible(false);
        startDateField.setVisible(false);
        endDateField.setVisible(false);

        reportTypeChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            carIdField.setVisible(false);
            customerIdField.setVisible(false);
            employeeIdField.setVisible(false);
            startDateField.setVisible(false);
            endDateField.setVisible(false);

            switch (newValue) {
                case "Services for Car or Customer":
                    carIdField.setVisible(true);
                    customerIdField.setVisible(true);
                    break;
                case "Sales by Employee":
                    employeeIdField.setVisible(true);
                    break;
                case "Payments by Customer":
                    customerIdField.setVisible(true);
                    break;
                case "Revenue per Service Type":
                case "Frequency of Services":
                    startDateField.setVisible(true);
                    endDateField.setVisible(true);
                    break;
                case "Service History by Vehicle":
                    carIdField.setVisible(true);
                    break;
            }
        });

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

            if (!startDateField.getText().isEmpty()) {
                params.put("start_date", startDateField.getText());
            }

            if (!endDateField.getText().isEmpty()) {
                params.put("end_date", endDateField.getText());
            }

            String report = reportGenerator.generateReport(selectedReportType, params);
            populateReportTable(report, reportTable);
        });

        reportContainer.getChildren().addAll(
                reportTitle,
                reportTypeChoice,
                carIdField,
                customerIdField,
                employeeIdField,
                startDateField,
                endDateField,
                generateReportButton,
                reportTable);
        mainContentPane.getChildren().setAll(reportContainer);
    }

    public void showNotification(String message, VBox mainContentPane, String colorHex) {
        StackPane notificationPane = new StackPane();
        notificationPane.setStyle("-fx-background-color: " + colorHex + "; -fx-background-radius: 15px;");
    
        Text notificationText = new Text(message);
        notificationText.setFont(Font.font("Gabarito", FontWeight.BOLD, 16));
        notificationText.setFill(Color.WHITE);
    
        notificationPane.setMaxWidth(400);
        notificationPane.setMaxHeight(50);
        notificationPane.getChildren().addAll(notificationText);
        StackPane.setAlignment(notificationPane, javafx.geometry.Pos.BOTTOM_CENTER);
    
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), notificationPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
    
        TranslateTransition slideUp = new TranslateTransition(Duration.seconds(0.5), notificationPane);
        slideUp.setFromY(300);
        slideUp.setToY(-50);
    
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), notificationPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(3));
        fadeOut.setOnFinished(
                event -> ((StackPane) notificationPane.getParent()).getChildren().remove(notificationPane));
    
        javafx.scene.Scene currentScene = mainContentPane.getScene();
        if (currentScene == null) {
            throw new IllegalStateException("mainContentPane must be part of a Scene.");
        }
    
        Parent root = currentScene.getRoot();
        if (root instanceof StackPane stackPane) {
            stackPane.getChildren().add(notificationPane);
        } else {
            StackPane wrapper = new StackPane(root);
            currentScene.setRoot(wrapper);
            wrapper.getChildren().add(notificationPane);
        }
    
        fadeIn.play();
        slideUp.play();
        fadeOut.play();
    }
    public void updateRecord(TableView<ObservableList<String>> table, VBox mainContentPane, Color notificationColor) {
        Permissions permissions = new Permissions();
    
        if (permissions.checkPermission("shahd")) {
            table.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    int index = table.getSelectionModel().getSelectedIndex();
                    if (index >= 0) {
                        ObservableList<String> row = table.getItems().get(index);
                        if (row == null) {
                            showNotification("Row data is missing.", mainContentPane, redColor);
                            return;
                        }
    
                        String tableName = table.getId();
                        if (tableName == null || tableName.isEmpty()) {
                            showNotification("Table name (id) is not set.", mainContentPane, redColor);
                            return;
                        }
    
                        openEditScene(row, tableName, mainContentPane, notificationColor);
                    }
                }
            });
        } else {
            showNotification("You do not have permission to edit records.", mainContentPane, redColor);
        }
    }
    
    
    public void openEditScene(ObservableList<String> row, String tableName, VBox mainContentPane, Color notificationColor) {
        Stage stage = new Stage();
        stage.setTitle("Edit Record");
        
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setStyle("-fx-background-color:white;");
    
        String[] columnNames = getColumnNamesForTable(tableName);
        
        if (columnNames == null || columnNames.length == 0) {
            showNotification("No columns found for table " + tableName, mainContentPane, redColor);
            return;
        }
    
        if (row.size() != columnNames.length) {
            showNotification("Row data mismatch with column count. Cannot edit record.", mainContentPane, redColor);
            return;
        }
    
        TextField[] textFields = new TextField[columnNames.length - 1];
    
        int textFieldIndex = 0;  
    
        for (int i = 0; i < columnNames.length; i++) {
            Label label = new Label(columnNames[i] + ":");
    
            if (i == 0) {
                continue;
            }
    
            TextField textField = new TextField(row.get(i));  
            textFields[textFieldIndex] = textField;
            textFieldIndex++;  
    
            gridPane.add(label, 0, textFieldIndex);
            gridPane.add(textField, 1, textFieldIndex);
        }
    
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-padding: 10px;");
        saveButton.setOnMouseEntered(e -> saveButton.setStyle(buttonHoverStyle));
        saveButton.setOnMouseExited(e -> saveButton.setStyle(buttonStyle));
        
        saveButton.setOnAction(event -> {
            try {
                updateDatabase(row.get(0), columnNames, textFields, tableName); 
                for (int i = 0; i < textFields.length; i++) {
                    row.set(i + 1, textFields[i].getText()); 
                }
                table.refresh();
                showNotification("Record updated successfully.", mainContentPane, usedColor);
                stage.close();
            } catch (SQLException e) {
                showNotification("Error updating record: " + e.getMessage(), mainContentPane, redColor);
                e.printStackTrace();
            }
        });
    
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-padding: 10px;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(buttonHoverStyle));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(buttonStyle));
        cancelButton.setOnAction(event -> stage.close());
    
        gridPane.add(saveButton, 0, columnNames.length);
        gridPane.add(cancelButton, 1, columnNames.length);
    
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }
    
    
    private void updateDatabase(String primaryKey, String[] columnNames, TextField[] textFields, String tableName) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        
        for (int i = 1; i < columnNames.length; i++) {
            queryBuilder.append(columnNames[i]).append(" = ?");
            if (i < columnNames.length - 1) queryBuilder.append(", ");
        }
    
        queryBuilder.append(" WHERE ").append(columnNames[0]).append(" = ?"); 
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
            
            int index = 1;  
            for (int i = 1; i < columnNames.length; i++) {
                preparedStatement.setString(index++, textFields[i - 1].getText()); 
            }
                preparedStatement.setString(index, primaryKey);
    
            preparedStatement.executeUpdate();
        }
    }
    
    private String[] getColumnNamesForTable(String tableName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " LIMIT 1")) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }

            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
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

    private void handleSQLException(SQLException e, VBox mainContentPane, String tableName) {
        String message = e.getMessage().toLowerCase();
        if (message.contains("duplicate entry")) {
            int nextId = getNextId(tableName);
            showNotification("Duplicate entry detected. Please use a unique of : "+nextId, mainContentPane, redColor);


        } else if (message.contains("foreign key constraint fails")) {
            showNotification("Foreign key constraint fails. Please ensure related records exist.", mainContentPane, redColor);
        } else if (message.contains("cannot be null")) {
            showNotification("A required field is missing. Please fill in all required fields.", mainContentPane, redColor);
        } else if (message.contains("data too long")) {
            showNotification("Data too long for one of the fields. Please shorten your input.", mainContentPane, redColor);
        } else {
            showNotification("An unexpected error occurred: " + e.getMessage(), mainContentPane, redColor);
        }
    }
    public int getNextId(String tableName) {
      int max =table.getItems().size();
      return max + 1;
    }
}