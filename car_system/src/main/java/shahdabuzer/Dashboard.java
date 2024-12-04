package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    private ComboBox<Integer> carIdComboBox;
    private ComboBox<Integer> customerIdComboBox;
    private ComboBox<Integer> employeeIdComboBox;
    private TextField quantityField;
    private TableInserter tableInserter;

    public Dashboard() {
        tableInserter = new TableInserter(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public Dashboard(TableInserter tableInserter) {
        this.tableInserter = tableInserter;
    }

    public void showDashboard(Stage primaryStage, String userName) {
        HBox root = new HBox(20);
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: #F1F0E8; -fx-padding: 30px;");

        VBox navigationBar = new VBox(20);
        navigationBar.setAlignment(Pos.TOP_LEFT);
        navigationBar.setStyle("-fx-background-color: #E5E1DA; -fx-padding: 20px; -fx-border-color: #CCCCCC; -fx-border-width: 1px;");

        Text appName = new Text("Car Shop System");
        appName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #333333;");

        Text greeting = new Text("Hi " + userName + "!");
        greeting.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #333333;");

        ImageView profileImage = new ImageView(new Image("file:C:/Users/توب نت/Desktop/extra/dataBase/sedan.png"));
        profileImage.setFitWidth(100);
        profileImage.setFitHeight(100);
        profileImage.setStyle("-fx-border-radius: 50%; -fx-background-radius: 50%; -fx-border-color: #89A8B2; -fx-border-width: 2px;");

        Text carsText = new Text("Cars");
        Text customersText = new Text("Customers");
        Text employeesText = new Text("Employees");
        Text reportsText = new Text("Reports");
        Text ordersText = new Text("Orders");
        Text paymentsText = new Text("Payments");
        Text servicesText = new Text("Services");

        String sidebarTextStyle = "-fx-font-size: 16px; -fx-fill: #555555; -fx-cursor: hand; -fx-padding: 5px 0;";
        String sidebarHoverStyle = "-fx-font-size: 18px; -fx-fill: #000000; -fx-background-color: #DDDDDD; -fx-cursor: hand; -fx-padding: 5px 0;";
        String buttonStyle = "-fx-background-color: #89A8B2; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";

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
        mainContentPane.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 20px; -fx-border-color: #89A8B2; -fx-border-width: 2px;");
        mainContentPane.setMaxHeight(750);

        Text selectTableLabel = new Text("Select a table");
        selectTableLabel.setStyle("-fx-font-size: 18px; -fx-fill: #333333;");
        mainContentPane.getChildren().add(selectTableLabel);

        carsText.setOnMouseClicked(e -> showTable(mainContentPane, "cars"));
        customersText.setOnMouseClicked(e -> showTable(mainContentPane, "customers"));
        employeesText.setOnMouseClicked(e -> showTable(mainContentPane, "employees"));
        reportsText.setOnMouseClicked(e -> showReports(mainContentPane));
        ordersText.setOnMouseClicked(e -> showTable(mainContentPane, "orders"));
        Button manageOrdersButton = new Button("Manage Orders");
        manageOrdersButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        manageOrdersButton.setOnAction(e -> {
            OrderManager orderManager = new OrderManager(DB_URL, DB_USERNAME, DB_PASSWORD);
            orderManager.showOrderManagementScreen(primaryStage);


        });
        mainContentPane.getChildren().add(manageOrdersButton);
        paymentsText.setOnMouseClicked(e -> showTable(mainContentPane, "payments"));
        servicesText.setOnMouseClicked(e -> showTable(mainContentPane, "services"));

       

        Button submitButton = new Button("Submit Order");
        submitButton.setStyle(buttonStyle);
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(buttonHoverStyle));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(buttonStyle));


        mainContentPane.getChildren();

        navigationBar.getChildren().addAll(appName, profileImage, greeting, carsText, customersText, employeesText, ordersText, paymentsText, servicesText, reportsText);

        root.getChildren().addAll(navigationBar, mainContentPane);

        Scene dashboardScene = new Scene(root, 800, 600);
        primaryStage.setScene(dashboardScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void showTable(VBox mainContentPane, String tableName) {
        String buttonStyle = "-fx-background-color: #89A8B2; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        String buttonHoverStyle = "-fx-background-color: #6D8A96; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        
        VBox tableContainer = new VBox(10);
        tableContainer.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 20px; -fx-border-color: #8B4513; -fx-border-width: 1px;");
    
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
    
        TableView<ObservableList<String>> table = new TableView<>();
        table.setPrefHeight(mainContentPane.getHeight());
    
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #8B4513; -fx-border-width: 1px; -fx-table-cell-border-color: #8B4513;");
    
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        ObservableList<ComboBox<Integer>> comboBoxes = FXCollections.observableArrayList(); 
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {
    
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));
                column.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().get(j)));
                column.setStyle("-fx-alignment: CENTER; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #F9F9F9; -fx-border-color: #8B4513; -fx-border-width: 0 0 1px 0;");
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
            StringBuilder query = new StringBuilder();
            for (ComboBox<Integer> comboBox : comboBoxes) {
                if (comboBox.getValue() != null) {
                    query.append(comboBox.getPromptText()).append(" = ").append(comboBox.getValue()).append(" AND ");
                }
            }
            if (query.length() > 0) {
                query.setLength(query.length() - 5); 
                TableSearcher searcher = new TableSearcher(DB_URL, DB_USERNAME, DB_PASSWORD);
                searcher.searchTable(tableName, query.toString(), table);
            }
        });
    
        Button insertButton = new Button("Insert");
        insertButton.setStyle(buttonStyle);
        insertButton.setOnMouseEntered(e -> insertButton.setStyle(buttonHoverStyle));
        insertButton.setOnMouseExited(e -> insertButton.setStyle(buttonStyle));
        insertButton.setOnAction(e -> {
            StringBuilder values = new StringBuilder();
            for (ComboBox<Integer> comboBox : comboBoxes) {
                values.append(comboBox.getValue()).append(",");
            }
            values.deleteCharAt(values.length() - 1); 
            TableInserter inserter = new TableInserter(DB_URL, DB_USERNAME, DB_PASSWORD);
            inserter.insertIntoTable(tableName, values.toString());
            showTable(mainContentPane, tableName); 
        });
    
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
        TableInserter tableInserter = new TableInserter("jdbc:mysql://localhost:3306/car_system", "username", "password");
        new Dashboard(tableInserter);
    }

    private void showReports(VBox mainContentPane) {
        mainContentPane.getChildren().setAll(new Text("Reports will be shown here."));
    }
}
