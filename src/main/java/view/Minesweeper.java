package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.Controller;

public class Minesweeper extends Application {
    public Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("Minesweeper");
        View view = new View(mainStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        loader.setController(new Controller(view, mainStage));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}