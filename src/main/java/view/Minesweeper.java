package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.Controller;

public class Minesweeper extends Application {
    View view = new View();
    public static Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        loader.setController(new Controller(view));
        Parent root = loader.load();
        mainStage = primaryStage;
        mainStage.setTitle("view.Minesweeper");
        mainStage.setScene(new Scene(root));
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}