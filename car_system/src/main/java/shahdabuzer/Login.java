package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login {
    public void showLoginScreen(Stage primaryStage) {
        
       
        
        StackPane root = new StackPane();

    

        
        VBox content = new VBox(10);
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: transparent; -fx-padding: 30px;");

        
        Text title = new Text("Car System");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setFill(Color.web("#89A8B2"));

        
        VBox loginForm = new VBox(10);
        loginForm.setAlignment(Pos.TOP_CENTER);
        loginForm.setStyle("-fx-background-color: transparent; -fx-padding: 30px;");

        Text loginText = new Text("Login");
        loginText.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        loginText.setFill(Color.web("#B3C8CF"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("User name");
        usernameField.setMaxWidth(400);

        
        usernameField.setOnMouseEntered(e -> usernameField.setStyle("-fx-background-color: #E0E0E0;"));
        usernameField.setOnMouseExited(e -> usernameField.setStyle("-fx-background-color: white;"));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(400);

        
        passwordField.setOnMouseEntered(e -> passwordField.setStyle("-fx-background-color: #E0E0E0;"));
        passwordField.setOnMouseExited(e -> passwordField.setStyle("-fx-background-color: white;"));

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color:B6A28E; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-font-size: 14px;");

        
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #8B7D6B; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-font-size: 14px;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color:B6A28E; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-font-size: 14px;"));

        
        loginButton.setOnAction(e -> verifyUserAccount(usernameField.getText(), passwordField.getText(), primaryStage));

        
        loginForm.getChildren().addAll(loginText, usernameField, passwordField, loginButton);

        
        content.getChildren().addAll(title, loginForm);

        
        root.getChildren().add(content);

        
        Scene loginScene = new Scene(root, 800, 600);
        primaryStage.setScene(loginScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void verifyUserAccount(String username, String password, Stage primaryStage) {
        String url = "jdbc:mysql://localhost:3306/car_shop";
        String dbUsername = "root";
        String dbPassword = "";

        String selectQuery = "SELECT * FROM user_account WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                showAlert(AlertType.INFORMATION, "Login successful!");
                Dashboard dashboard = new Dashboard();
                dashboard.showDashboard(primaryStage, username);
            } else {
                showAlert(AlertType.ERROR, "Invalid username or password.");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
