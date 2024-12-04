package shahdabuzer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import java.sql.SQLException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        ImageView background = new ImageView(new Image("file:C:/Users/توب نت/Desktop/extra/dataBase/img.png"));
        background.fitWidthProperty().bind(primaryStage.widthProperty());
        background.fitHeightProperty().bind(primaryStage.heightProperty());
        background.setPreserveRatio(false);

        Text title = new Text("Car System");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setFill(Color.WHITE);

        Sign sign = new Sign();
        VBox signUpForm = sign.createSignUpForm(primaryStage);

        VBox container = new VBox(20, title, signUpForm);
        container.setAlignment(Pos.CENTER);

        root.getChildren().addAll(background, container);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car System");
        primaryStage.setFullScreen(true);
        primaryStage.show();

        connectToDatabase();

        signUpForm.getChildren().get(4).setOnMouseClicked(e -> {
            Login login = new Login();
            login.showLoginScreen(primaryStage);
        });
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/car_shop";
        String username = "root";
        String password = "";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Cannot connect to the database! " + e.getMessage());
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
