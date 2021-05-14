package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import model.Board;
import model.Tile;
import view.*;

import java.io.IOException;

public class Controller {
    private Board field;
    private View view;
    @FXML
    TextField columns;
    @FXML
    TextField rows;
    @FXML
    TextField bombs;
    @FXML
    Label wrong;


    public Controller(Board field, View view) {
        this.field = field;
        this.view = view;
    }

    public Controller(View view) {
        this.view = view;
    }

    public void playNewGame() {
            try {
                int x = Integer.parseInt(columns.getText());
                int y = Integer.parseInt(rows.getText());
                int bombs = Integer.parseInt(this.bombs.getText());
                if (x <= 0 || y <= 0 || bombs <= 0 || x * y <= bombs || x > 30 || y > 22
                ) {
                    wrong.setVisible(true);
                    return;
                }
                view.setConfigure(y, x, bombs);
            } catch (Exception i) {
                wrong.setVisible(true);
                return;
            }

        if (!view.isConfigured())
            return;

        Minesweeper.primaryStage.setScene(new Scene(view.createGameBoard()));
    }

    public void returnToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        loader.setController(this);

        Parent root = loader.load();

        Minesweeper.primaryStage.setScene(new Scene(root));
    }

    public void reset() {
        Minesweeper.primaryStage.getScene().setRoot(view.createGameBoard());
    }

    public void exit() {
        System.exit(0);
    }

    public void mouseClickLeft(Tile tile) {
        field.openTile(tile);
        view.update();
    }

    public void mouseClickRight(Tile tile) {
        field.flagTile(tile);
        view.update();
    }

}