package shahdabuzer;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public class Sign {
    public VBox createSignUpForm(Stage primaryStage) {
        VBox signUpForm = new VBox(15);
        signUpForm.setAlignment(Pos.CENTER);

        Text signUpText = new Text("Sign Up");
        signUpText.setFont(Font.font("Gabarito", FontWeight.BOLD, 40));
        signUpText.setFill(Color.WHITE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("User name");
        usernameField.setMaxWidth(300);
        usernameField.setFont(Font.font("Gabarito", 14));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setFont(Font.font("Gabarito", 14));

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;");
        signUpButton.setFont(Font.font("Gabarito", FontWeight.BOLD, 16));
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #6B8E9B; -fx-text-fill: white; -fx-font-size: 14px;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;"));

        Button hasAccountButton = new Button("Already have an account");
        hasAccountButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;");
        hasAccountButton.setFont(Font.font("Gabarito", FontWeight.BOLD, 16));
        hasAccountButton.setOnMouseEntered(e -> hasAccountButton.setStyle("-fx-background-color: #6B8E9B; -fx-text-fill: white; -fx-font-size: 14px;"));
        hasAccountButton.setOnMouseExited(e -> hasAccountButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;"));

        signUpButton.setOnAction(e -> createUserAccount(usernameField.getText(), passwordField.getText(), primaryStage));
        signUpForm.getChildren().addAll(signUpText, usernameField, passwordField, signUpButton, hasAccountButton);
        signUpForm.setStyle("-fx-background-color: rgba(179, 200, 207, 0.5); -fx-padding: 20px;");
        return signUpForm;
    }

    private void createUserAccount(String username, String password, Stage primaryStage) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Username and Password cannot be empty.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/car_shop";
        String dbUsername = "root";
        String dbPassword = "";

        String checkQuery = "SELECT COUNT(*) FROM user_account WHERE username = ?";
        String insertQuery = "INSERT INTO user_account (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert(Alert.AlertType.ERROR, "Username already exists.");
                    return;
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                if (preparedStatement.executeUpdate() > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Account created successfully!");
                    Dashboard dashboard = new Dashboard();
                    dashboard.showDashboard(primaryStage, username);
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
