package shahdabuzer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public void showLoginScreen(Stage primaryStage) {
        StackPane root = new StackPane();
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        
        ImageView background = new ImageView(new Image("file:C:/Users/توب نت/Desktop/extra/dataBase/img.png"));
        background.fitWidthProperty().bind(primaryStage.widthProperty());
        background.fitHeightProperty().bind(primaryStage.heightProperty());
        background.setPreserveRatio(false);

        Text title = new Text("Car System");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setFill(Color.WHITE);

    

        Text loginText = new Text("Log In");
        loginText.setFont(Font.font("Gabarito", FontWeight.BOLD, 40));
        loginText.setFill(Color.WHITE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("User name");
        usernameField.setMaxWidth(300);
        usernameField.setFont(Font.font("Gabarito", 14));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setFont(Font.font("Gabarito", 14));

        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setFont(Font.font("Gabarito", FontWeight.BOLD, 16));
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #6B8E9B; -fx-text-fill: white; -fx-font-size: 14px;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;"));

        loginButton.setOnAction(e -> authenticateUser(usernameField.getText(), passwordField.getText(), primaryStage));

        loginForm.getChildren().addAll(loginText, usernameField, passwordField, loginButton);
        loginForm.setStyle("-fx-background-color: rgba(179, 200, 207, 0.5); -fx-padding: 20px;");


        VBox container = new VBox(20, title, loginForm);
        container.setAlignment(Pos.CENTER);
        root.getChildren().addAll(background, container);

        Scene loginScene = new Scene(root, 800, 600);
        primaryStage.setScene(loginScene);
        primaryStage.setFullScreen(true);

    }

    private void authenticateUser(String username, String password, Stage primaryStage) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Username and Password cannot be empty.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/car_shop";
        String dbUsername = "root";
        String dbPassword = "";

        String query = "SELECT * FROM user_account WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    showAlert(Alert.AlertType.INFORMATION, "Login successful!");
                    Dashboard dashboard = new Dashboard();
                    dashboard.showDashboard(primaryStage, username);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Invalid username or password.");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Log In");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
