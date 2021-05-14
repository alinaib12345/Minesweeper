package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.Controller;

public class Minesweeper extends Application {
    View view = new View();
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        loader.setController(new Controller(view));
        Parent root = loader.load();
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        (Minesweeper.primaryStage = primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}