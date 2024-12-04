package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class Sign {
    public VBox createSignUpForm(Stage primaryStage) {
        VBox signUpForm = new VBox(10);
        signUpForm.setAlignment(Pos.TOP_CENTER);

        Text signUpText = new Text("sign up");
        signUpText.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        signUpText.setFill(Color.web("#B3C8CF"));

        // TranslateTransition textTransition = new TranslateTransition(Duration.seconds(1), signUpText);
        // textTransition.setFromY(50);
        // textTransition.setToY(0);
        // textTransition.play();

        // ImageView carImage = new ImageView(new Image(getClass().getResource("/sedan.png").toExternalForm()));
        // TranslateTransition carTransition = new TranslateTransition(Duration.seconds(1), carImage);
        // carTransition.setFromY(50);
        // carTransition.setToY(0);
        // carTransition.play();

        TextField usernameField = new TextField();
        usernameField.setPromptText("User name");
        usernameField.setMaxWidth(400);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(400);

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color:B6A28E; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-font-size: 14px;");
        signUpButton.setOnAction(e -> createUserAccount(usernameField.getText(), passwordField.getText(), primaryStage));
        

        Button hasButton = new Button("Already have an account");
        hasButton.setStyle("-fx-background-color:B6A28E; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-font-size: 14px;");

        signUpForm.getChildren().addAll(signUpText, usernameField, passwordField, signUpButton, hasButton);

        return signUpForm;
    }

    private void createUserAccount(String username, String password, Stage primaryStage) {
        String url = "jdbc:mysql://localhost:3306/car_shop";
        String dbUsername = "root";
        String dbPassword = "";

        String insertQuery = "INSERT INTO user_account (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Account created successfully!");
                Dashboard dashboard = new Dashboard();
                dashboard.showDashboard(primaryStage, username);
            } else {
                showAlert(AlertType.ERROR, "Failed to create account.");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
