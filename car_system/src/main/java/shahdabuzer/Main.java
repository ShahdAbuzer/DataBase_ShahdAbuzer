package shahdabuzer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Dashboard dashboard = new Dashboard(null);
        String userName = "John Doe"; 
        dashboard.showDashboard(primaryStage, userName); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}