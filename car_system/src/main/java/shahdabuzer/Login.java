package shahdabuzer;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
     boolean isPasswordVisible = false;

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

        TextField passwordFieldVisible = new TextField();
        passwordFieldVisible.textProperty().bindBidirectional(passwordField.textProperty());
        passwordFieldVisible.setVisible(false);
        passwordFieldVisible.setMaxWidth(300);
        passwordFieldVisible.setFont(Font.font("Gabarito", 14));

        StackPane passwordStack = new StackPane();
        passwordStack.getChildren().addAll(passwordField, passwordFieldVisible);

        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setFont(Font.font("Gabarito", FontWeight.BOLD, 16));
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #6B8E9B; -fx-text-fill: white; -fx-font-size: 14px;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;"));

        loginButton.setOnAction(e -> authenticateUser(usernameField.getText(), passwordField.getText(), primaryStage, loginForm));

          CheckBox showPasswordCheckBox = new CheckBox("Show Password");
        showPasswordCheckBox.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-background-color: transparent;");
        
        showPasswordCheckBox.setOnAction(_ -> {
            isPasswordVisible = showPasswordCheckBox.isSelected();
                passwordField.setVisible(!isPasswordVisible);
                passwordFieldVisible.setVisible(isPasswordVisible);
                    });
    

        loginForm.getChildren().addAll(loginText, usernameField, passwordStack, loginButton , showPasswordCheckBox);
        loginForm.setStyle("-fx-background-color: rgba(179, 200, 207, 0.5); -fx-padding: 20px;");
          

        VBox container = new VBox(20, title, loginForm);
        container.setAlignment(Pos.CENTER);
        root.getChildren().addAll(background, container);
        Scene loginScene = new Scene(root, 800, 600);
        primaryStage.setScene(loginScene);
        primaryStage.setFullScreen(true);

    }

    private void authenticateUser(String username, String password, Stage primaryStage, VBox loginForm) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            showNotification("Please enter a valid username and password.", loginForm);
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
                    showNotification("Login successful!", loginForm);
                    Dashboard dashboard = new Dashboard();
                    dashboard.showDashboard(primaryStage, username);
                } else {
                    showNotification("Invalid username or password.", loginForm);
                }
            }
        } catch (SQLException e) {
            showNotification("Database error: " + e.getMessage(), loginForm);
        }
    }

 
      public void showNotification(String message, VBox mainContentPane) {
        StackPane notificationPane = new StackPane();
        notificationPane.setStyle("-fx-background-color: #89A8B2; -fx-background-radius: 15px;");

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

}
