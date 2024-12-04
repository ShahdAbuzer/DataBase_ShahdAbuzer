package shahdabuzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaException;

public class App extends Application 
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        StackPane stackPane = new StackPane();
        stackPane.getChildren();

        Text title = new Text("Car System");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setFill(Color.web("#89A8B2"));

        Sign sign = new Sign();
        VBox signUpForm = sign.createSignUpForm(primaryStage);

        root.getChildren().addAll(title, signUpForm);
        stackPane.getChildren().add(root);

        Scene scene = new Scene(stackPane, 800,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car System");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        connectToDatabase();

        Button loginButton = (Button) signUpForm.getChildren().get(4); // Directly reference the button
        loginButton.setOnAction(e -> {
            Login login = new Login();
            login.showLoginScreen(primaryStage);
        });
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/car_shop";
        String username = "root"; 
        String password = ""; 
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Cannot connect the database! " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Database Connection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}