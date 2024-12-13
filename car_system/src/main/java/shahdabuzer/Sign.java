package shahdabuzer;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
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

public class Sign {
    boolean isPasswordVisible = false;
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
        TextField passwordFieldVisible = new TextField();
        passwordFieldVisible.textProperty().bindBidirectional(passwordField.textProperty());
        StackPane passwordStack = new StackPane();
        passwordStack.getChildren().addAll(passwordField, passwordFieldVisible);
        passwordFieldVisible.setVisible(false);
        passwordFieldVisible.setMaxWidth(300);
        passwordFieldVisible.setFont(Font.font("Gabarito", 14));
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setFont(Font.font("Gabarito", 14));

        PasswordField confirmPasswordField = new PasswordField();
        TextField confirmFieldVisible = new TextField();
        StackPane confirmStack = new StackPane();
        confirmStack.getChildren().addAll(confirmPasswordField, confirmFieldVisible);
        confirmFieldVisible.textProperty().bindBidirectional(confirmPasswordField.textProperty());
        confirmFieldVisible.setVisible(false);
        confirmFieldVisible.setMaxWidth(300);
        confirmFieldVisible.setFont(Font.font("Gabarito", 14));
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setFont(Font.font("Gabarito", 14));

        Text errorText = new Text();
        errorText.setFont(Font.font("Gabarito", 14));
        errorText.setFill(Color.RED);
        errorText.setVisible(false);

        CheckBox showPasswordCheckBox = new CheckBox("Show Password");
        showPasswordCheckBox.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-background-color: transparent;");
        
        showPasswordCheckBox.setOnAction(_ -> {
            isPasswordVisible = showPasswordCheckBox.isSelected();
                passwordField.setVisible(!isPasswordVisible);
                confirmPasswordField.setVisible(!isPasswordVisible);
                passwordFieldVisible.setVisible(isPasswordVisible);
                confirmFieldVisible.setVisible(isPasswordVisible);
        });
    

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;");
        signUpButton.setFont(Font.font("Gabarito", FontWeight.BOLD, 16));
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #6B8E9B; -fx-text-fill: white; -fx-font-size: 14px;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;"));
        signUpButton.setOnAction(e -> {

            if (usernameField.getText().trim().isEmpty()) {
                showNotification("Username cannot be empty!", signUpForm);
            } else if (passwordField.getText().trim().isEmpty()) {
                showNotification("Password cannot be empty!", signUpForm);
            } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                showNotification("Passwords do not match!", signUpForm);
            } else {
                createUserAccount(usernameField.getText(), passwordField.getText(), primaryStage, errorText, signUpForm);
                showNotification("Account created successfully!", signUpForm);
            }
        });

        Button hasAccountButton = new Button("Already have an account");
        hasAccountButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;");
        hasAccountButton.setFont(Font.font("Gabarito", FontWeight.BOLD, 16));
        hasAccountButton.setOnMouseEntered(e -> hasAccountButton.setStyle("-fx-background-color: #6B8E9B; -fx-text-fill: white; -fx-font-size: 14px;"));
        hasAccountButton.setOnMouseExited(e -> hasAccountButton.setStyle("-fx-background-color: #89A8B2; -fx-text-fill: white; -fx-font-size: 14px;"));
        hasAccountButton.setOnAction(e-> {
            Login login = new Login();
            login.showLoginScreen(primaryStage);
        });

        VBox passwordBox = new VBox(15);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.getChildren().addAll(passwordStack, confirmStack);
        signUpForm.getChildren().clear();
        signUpForm.getChildren().addAll(signUpText, usernameField, passwordBox, errorText, showPasswordCheckBox, signUpButton, hasAccountButton);
        signUpForm.setStyle("-fx-background-color: rgba(179, 200, 207, 0.5); -fx-padding: 20px;");
        return signUpForm;
    }

    private void createUserAccount(String username, String password, Stage primaryStage, Text errorText, VBox signUpForm) {
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
                    showNotification("Username already exists!", signUpForm);
                    return;
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                if (preparedStatement.executeUpdate() > 0) {
                    showNotification("Account created successfully!",signUpForm);
                    Dashboard dashboard = new Dashboard();
                    dashboard.showDashboard(primaryStage, username);
                }
            }
        } catch (SQLException e) {
           showNotification("Cannot create account! " + e.getMessage(), signUpForm);
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

    

    

