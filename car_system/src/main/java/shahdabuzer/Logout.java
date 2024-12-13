package shahdabuzer;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Logout {

    private static final String buttonHoverStyle = "-fx-background-color: #6D8A96; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
    private static final String buttonStyle = "-fx-background-color: #89A8B2; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";

    public void showLogoutScreen(Stage primaryStage) {
        Scene currentScene = primaryStage.getScene();
        Parent root = currentScene.getRoot();
        StackPane stackRoot;
        if (root instanceof StackPane) {
            stackRoot = (StackPane) root;
        } else {
            stackRoot = new StackPane(root);
            currentScene.setRoot(stackRoot);
        }

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.rgb(0, 0, 0, 0.5));
        overlay.widthProperty().bind(stackRoot.widthProperty());
        overlay.heightProperty().bind(stackRoot.heightProperty());

        VBox confirmationBox = new VBox(20);
        confirmationBox.setAlignment(Pos.CENTER);
        confirmationBox.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-padding: 20px; " +
                "-fx-border-radius: 10px; " +
                "-fx-background-radius: 10px;"
        );

        Text confirmationText = new Text("Are you sure you want to log out?");
        confirmationText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        confirmationText.setFill(Color.BLACK);

        Button yesButton = new Button("Yes");
        yesButton.setStyle(buttonStyle);
        yesButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        yesButton.setPrefWidth(80);
        yesButton.setOnMouseEntered(e -> yesButton.setStyle(buttonHoverStyle));
        yesButton.setOnMouseExited(e -> yesButton.setStyle(buttonStyle));
        yesButton.setOnAction(e -> {
            new Login().showLoginScreen(primaryStage);
        });

        Button noButton = new Button("No");
        noButton.setStyle(buttonStyle);
        noButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        noButton.setPrefWidth(80);
        noButton.setOnMouseEntered(e -> noButton.setStyle(buttonHoverStyle));
        noButton.setOnMouseExited(e -> noButton.setStyle(buttonStyle));
        noButton.setOnAction(e -> {
            stackRoot.getChildren().removeAll(overlay, confirmationBox);
        });

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(yesButton, noButton);

        confirmationBox.getChildren().addAll(confirmationText, buttonsBox);
        stackRoot.getChildren().addAll(overlay, confirmationBox);
    }
}
